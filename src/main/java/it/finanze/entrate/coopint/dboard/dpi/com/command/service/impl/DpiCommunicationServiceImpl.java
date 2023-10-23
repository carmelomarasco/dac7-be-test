package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.*;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.*;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.utils.JsonOperation;
import it.gov.agenziaentrate.specifichetecniche.telent.v1.Messaggio;
import it.sogei.dac7.impl.Dac7Marshaller;
import it.sogei.dac7.impl.Dac7Unmarshaler;
import oecd.ties.dpi.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Optionals;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.finanze.entrate.coopint.dboard.dpi.com.command.kafka.CustomHeader;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.AllInfoAcquiredBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.DpiCommunicationPayload;
import it.finanze.entrate.coopint.dboard.dpi.common.config.TopicProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.ComClass;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.MessageTypeIndic;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.Status;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.xml.bind.JAXBException;

@CommonsLog
@Data
@Service
public class DpiCommunicationServiceImpl implements DpiCommunicationService {
	@Autowired
	@Qualifier("dboardDpiCountryRepositoryNazionale")
	DboardDpiCountryRepository dboardDpiCountryRepository;
	@Autowired
	KafkaProduceService kafkaProduceService;
	@Autowired
	DpiEventSender dpiEventSender;
	@Autowired
	DpiRestTemplateService dpiRestTemplateService;
	@Autowired
	@Qualifier("communicationRepositoryNazionale")
	CommunicationRepository communicationRepository;
	@Autowired
	@Qualifier("communicationDataRepositoryNazionale")
	CommunicationDataRepository communicationDataRepository;
	@Autowired
	@Qualifier("communicationStatusRepositoryNazionale")
	CommunicationStatusRepository communicationStatusRepository;
	@Autowired
	@Qualifier("messageTypeIndicRepositoryNazionale")
	MessageTypeIndicRepository messageTypeIndicRepository;
	@Autowired
	@Qualifier("fiscalYearRepositoryNazionale")
	FiscalYearRepository fiscalYearRepository;
	@Autowired
	@Qualifier("countryRepositoryNazionale")
	CountryRepository countryRepository;
	@Autowired
	@Qualifier("comClassRepositoryNazionale")
	ComClassRepository comClassRepository;
	@Autowired
	@Qualifier("effectiveDestRepositoryNazionale")
	EffectiveDestRepository effectiveDestRepository;
	@Autowired
	DpiRestTemplateService restTemplateService;
	@Autowired
	DpiSaveService dpiSaveService;
	@Autowired
	DPIService dpiService;
	@Autowired
	CountryService countryService;

	Dac7Unmarshaler dac7unmarshaler = new Dac7Unmarshaler();
	Dac7Marshaller dac7marshaller = new Dac7Marshaller();

	@Value("${url-dpi-com-view-service}")
	private String urlDpiComView;
	
	@Value("${url-dpi-com-command-service}")
	private String urlDpiComCommand;

	@Override
	public void processReceivedCommunication(String payloadB64, MessageHeaders messageHeaders) {
		List<Runnable> eventsToSend = processReceivedCommunicationPrivate(payloadB64,messageHeaders);
		try {
			eventsToSend.forEach(Runnable::run);
		} catch( Exception e ){
			String protocol = (String) messageHeaders.get(CustomHeader.PROTOCOL);
			log.info("Errore durante l'invio degli eventi relativi alla comunicazione prot " + protocol,e);
		}
	}

	@Transactional
	List<Runnable> processReceivedCommunicationPrivate(String payloadB64, MessageHeaders messageHeaders) {
		List<Runnable> eventsToSend = Lists.newArrayList();

		log.info("Arrived communication from boundary-service,checking if it already exists");

		// Check if already exist a communication by protocol
		String protocol = (String) messageHeaders.get(CustomHeader.PROTOCOL);
		if (communicationRepository.existsByProtocol(protocol)) {
			log.info("Communication with protocol [" + protocol + "] already exists");
			return eventsToSend;
		}


		log.info("Communication has unic protocol, it can be processed");
		try {
			String payload = new String(Base64.getDecoder().decode(payloadB64));
			String eventName = (String) messageHeaders.get(CustomHeader.EVENT_NAME);
			List<Country> countriesList = null;

			DpiCommunicationPayload dpiCommunication = JsonOperation.jsonToObject(payload,
					DpiCommunicationPayload.class);

			log.info("*************************** COMMUNICATION BEAN INFO");
			log.info(dpiCommunication.toString());
			log.info("*************************** COMMUNICATION BEAN INFO");

			// new behaviour with xml taken from rest service
			String xmlGzipBase64FromBoundary = dpiRestTemplateService
					.retrieveDPICommunicationByProtocol(dpiCommunication.getProtocol());
			log.info(xmlGzipBase64FromBoundary != null && xmlGzipBase64FromBoundary.length() > 50
					? xmlGzipBase64FromBoundary.substring(0, 50)
					: "--");

			String xmlGzippedBase64FileName = "/tmp/dpi-" + System.currentTimeMillis() + ".xml.gz.b64";
			File xmlGzippedBase64File = new File(xmlGzippedBase64FileName);
			FileOutputStream xmlGzippedBase64FileOutputStream = new FileOutputStream(xmlGzippedBase64File);
			StreamUtils.copy(new ByteArrayInputStream(xmlGzipBase64FromBoundary.getBytes(StandardCharsets.UTF_8)),
					xmlGzippedBase64FileOutputStream);
			xmlGzippedBase64FileOutputStream.flush();
			xmlGzippedBase64FileOutputStream.close();

			DPIOECD dpioecd = dac7unmarshaler.unmarshalDPI( new FileReader(xmlGzippedBase64File));
			// if status is Accepted, process communication dests
			// TODO Controllare con Roberto
			if (dpiCommunication.getStatus().equals(Status.ACCEPTED.getValue())
					|| dpiCommunication.getStatus().equals(Status.ACCEPTED_NON_CONTRIBUTING.getValue())) {
				log.info("Communication is ACCEPTED, POTENTIAL and EFFECTIVE dests must pe calculated");
				// *Call to country-service to get list of countries*/
				Map<String, List<String>> countriesOfCom = MessageUtils.findAllDestinationsOfCommunication(dpioecd);
				countriesList = countryRepository.findAllByValueIn(
						countriesOfCom.values().stream().flatMap(Collection::stream).collect(Collectors.toSet()));

				// if class is SUBSTITUTIVE or INVALIDATE, we have to chek if protocolToUpdate
				// is present in DB
				if (ComClass.INVALIDATE.getValue().equalsIgnoreCase(dpiCommunication.getIntention())) {
					log.info("Communication intention: " + dpiCommunication.getIntention());
					if ("".equals(dpiCommunication.getProtocolToUpdate())
							|| null == dpiCommunication.getProtocolToUpdate()) {
						log.error("Protocol to update: " + dpiCommunication.getProtocolToUpdate());
						throw new NullPointerException("Protocol arrived: [" + dpiCommunication.getProtocol()
								+ "] - protocolToUpdate: [" + dpiCommunication.getProtocolToUpdate() + "]");
					} else {
						String[] protocolToUpdate = dpiCommunication.getProtocolToUpdate().split("[|]");
						for (String singleProtocol : protocolToUpdate) {
							if (!communicationRepository.existsByProtocol(singleProtocol)) {
								log.error("Communication with protocol [" + dpiCommunication.getProtocolToUpdate()
										+ "] doesn't exists");
								throw new Exception("Communication with protocol ["
										+ dpiCommunication.getProtocolToUpdate() + "] doesn't exists");
							}
						}
					}
				}
			}

			AllInfoAcquiredBean allInfoAcquiredBean = AllInfoAcquiredBean.builder().build();
			// START transaction to save communication
			Communication comEntityPersisted = dpiSaveService.saveAcquiredCommunication( dpiCommunication,
					xmlGzipBase64FromBoundary, dpioecd, countriesList, allInfoAcquiredBean);
			//////////////////////////////////////
			//SerializationUtils.clone()


			// Sending event for acquired communication
			eventsToSend.add( () ->
				dpiEventSender.sendEventAcquiredCommunication(
						TopicProperties.DPI_NAT_EVENT,
						protocol,
						eventName,
						allInfoAcquiredBean, "")
			);


			// il codice seguente è quello di lettura dell'evento sul servizio Excel
			//	if (EventNameProperties.START_EXCEL_DAC7_NATIONAL.equals(eventReceived)) {
			//		String protocol = new String(Base64.getDecoder().decode(payload));
			//		dac7ExcelService.createExcelNational(protocol);

			// Excel solo se contenuto presente
			if (dpiCommunication.getStatus().equals(Status.ACCEPTED.getValue())
				 && !MessageTypeIndic.DPI403.getValue().equalsIgnoreCase(
					comEntityPersisted.getMessageTypeIndicByMessageTypeIndicId().getValue())
					) {
				eventsToSend.add(() ->
						dpiEventSender.sendEventStartBuildExcel(protocol)
				);
			}

			if (dpiCommunication.getStatus().equals(Status.ACCEPTED_NON_CONTRIBUTING.getValue())
					&& null != dpiCommunication.getProtocolToUpdate()
					&& !dpiCommunication.getProtocolToUpdate().isEmpty()) {
				log.info("Arrived a " + dpiCommunication.getIntention() + ". Protocol to update: "
						+ dpiCommunication.getProtocolToUpdate());
				//String classOfEntityPersisted = comEntityPersisted.getComClassByComClassId().getComClassDescription();
				List<String> protocolToUpdate = Arrays.asList(dpiCommunication.getProtocolToUpdate().split("[|]"));
				protocolToUpdate.forEach(ptu -> {
					String comToUpdate = communicationRepository.findOnlyIdByProtocol(ptu);
					// invio evento solo su ANNULLAMENTO
					if(  ComClass.INVALIDATE.getValue().equalsIgnoreCase(dpiCommunication.getIntention() )) {
						// effettuare cambio di stato sulle entity da annullare
						eventsToSend.add( () ->
							dpiEventSender.sendEventUpdateStatus(ptu, Status.CANCELED.getValue(),
								TopicProperties.DPI_NAT_COMMAND, null, null, null, comToUpdate, true)
						);
					}
				});
			}

			// solo se elenco esiste ossia su accettata
			if( countriesList != null ) {
				eventsToSend.add(() ->
						dpiEventSender.sendCommandAddContributeToEffDest(dpiCommunication.getProtocol())
				);
			}

			/* if a communication is late, it must be changed to FINALIZED */
			if (dpiCommunication.getStatus().equals(Status.ACCEPTED.getValue())
					&& !MessageTypeIndic.DPI403.getValue().equalsIgnoreCase(
							comEntityPersisted.getMessageTypeIndicByMessageTypeIndicId().getValue())
					&& allInfoAcquiredBean.getFyForCurrentComIsExpired()) {
				log.info(
						"*****************************THIS COMMUNICATIONS MUST BE USED FOR CONSTRUCTION NOW****************************************");
				eventsToSend.add( () ->
						dpiEventSender.sendCommandFinalizeCommunication(comEntityPersisted.getProtocol(), false )
				);
				// dopo la finalizzazione di una tardiva inviare START_BUILDING_MESSAGES_SPECIFIC_COM

				// TODO questo evento è a favore di European Communication Service
//				eventsToSend.add( () -> {
//					log.info("send late communication --------> " + EventNameProperties.LATE_COMMUNICATION);
//					dpiEventSender.sendEventLateCommunication((long) (comEntityPersisted.getReportingPeriod().getYear()),
//							dpiCommunication.getXmlGZipBase64(), comEntityPersisted.getSenderCf(),
//							Date.from(comEntityPersisted.getSentDate()).getTime(), comEntityPersisted.getProtocol(), comEntityPersisted.getId().getComUuid(), urlDpiComCommand);
//				});
			}

			log.info("All processes for received communication ended");
		} catch (Exception e) {
			log.info("Error during processReceivedCommunication method", e);
			throw new RuntimeException(e);
		}
		return eventsToSend;
	}

	@Override
	@Transactional
	public void updateStatus(String statusToB64, MessageHeaders messageHeaders) {

		String msgKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
		String idCommunication = (String) messageHeaders.get(CustomHeader.COM_UUID);

		try {
			Boolean comInvalidateBySystem = (Boolean) messageHeaders.get(CustomHeader.INVALIDATE_BY_SYSTEM);
			log.info("Decoding payload...");

			log.info("Start update status for communication with id: " + idCommunication);
			Communication entityToUpdate = communicationRepository.findByComUuid(idCommunication).get();

			String statusTo = new String(it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils
					.convertBase64ToByteArray(statusToB64));
//			FiscalYear fiscalYear = fiscalYearRepository.findByYear((long)entityToUpdate.getReportingPeriod().getYear());
//			if (fiscalYear.isExpired() && Status.ACCEPTED.getValue().equals(statusTo)
//					&& !MessageTypeIndic.DPI403.getValue()
//							.equals(entityToUpdate.getMessageTypeIndicByMessageTypeIndicId().getValue())) {
//				statusTo = Status.FINALIZED.getValue();
//			}

			Communication clone = entityToUpdate.clone();
			clone.setMessageDate(new Date().toInstant());
			clone.setStatus(clone.getStatus());
			dpiSaveService.saveCommunication(clone);
			log.info("Communication with id: " + idCommunication + "changed to " + clone.getStatus().getStatus());
			String previousStatus = entityToUpdate.getStatus().getStatus();
			String msgTypeIndic = entityToUpdate.getMessageTypeIndicByMessageTypeIndicId().getValue();
			// sending event
			Date reportingPeriod = Date.from(
					clone.getReportingPeriod().withMonth(12).withDayOfMonth(31)
							.atStartOfDay(ZoneId.systemDefault()).toInstant());
			//Date reportingPeriod = DateUtil.toDate("31-12-" + clone.getReportingPeriod().getYear());
			
			dpiEventSender.sendEventUpdateStatus(msgKey, clone.getStatus().getStatus(),
					TopicProperties.DPI_NAT_EVENT, reportingPeriod, previousStatus, msgTypeIndic,
					clone.getId().getComUuid(), comInvalidateBySystem);
		} catch (Exception e) {
			log.info("Error during updateStatus method", e);
		}

	}


	// Ogni ora al minuto 18
	// il timer è efficace se l'anno è scaduto ma ancora non è stato inviato StartBuildingMessage
	@Scheduled(cron = "12 18 * * * *")
	public void checkInvioStartBuildingMessage() {
		// TODO ogni 60 minuti check concluse tutte le finalizzazioni e su fiscalYear non c'è costruzione lanciata
		// non devono esistere ACCETTATE non FINALIZZATE
		// tutte le finalizzate devono avere tutti i contributi
		// magari se non si riesce ad effettuare l'invio dello star building message, avvisare via email
		List<FiscalYear> allYear = fiscalYearRepository.findByExpired(true);
		// Esiste un anno scaduto per il quale non sia stata avviata la costruzione dei messaggi NR?
		Optional<FiscalYear> fy = allYear.stream()
				.filter(f -> f.getFlagNrStartMessage() == 0L )
				.findFirst();
		if( !fy.isPresent()){
			return;
		}
		FiscalYear fiscalyear = fy.get();
		log.info( "checkInvioStartBuildingMessage attivo per l'anno " + fiscalyear.getYear());

		Long messageTypeIndic = messageTypeIndicRepository.findByValue(MessageTypeIndic.DPI401.getValue()).getId();
		Long statusId = communicationStatusRepository.findByStatus("ACCEPTED").getId();
		Optional<List<Communication>> comms = communicationRepository.findAllByFiscalYearAndComClassIdAndStatusId(fiscalyear.getYear(), messageTypeIndic, statusId);
		// se non esistono ACCETTATE
		if( !comms.isPresent() || comms.get().isEmpty()) {
			// e tutte le finalizzate hanno un contributo
			if (communicationDataRepository.existsCommunicationDataFinalizedWithoutContributeForYear(fiscalyear.getYear())==0) {
				// disattivo Timer checkInvioStartBuildingMessage
				fiscalyear.setFlagNrStartMessage(1L);
				dpiSaveService.saveFiscalYear(fiscalyear);

				dpiEventSender.sendEventStartBuildingMessages(fiscalyear.getYear());
				log.info("Inviato START_BUILDING_MESSAGE per l'anno " + fiscalyear.getYear());
			} else {
				log.info("Non posso inviare StartBuildingMessage perchè sono presenti comunicazioni senza contributo" );
			}
		} else {
			log.info( "Non posso inviare StartBuildingMessage perchè sono ancora presenti " + comms.get().size() + " comunicazioni ACCEPTED di tipo DPI401 non finalizate");
		}
	}

	@Override
	public void finalizeAllCommunicationsOfYear(long year){
		Long messageTypeIndic = messageTypeIndicRepository.findByValue(MessageTypeIndic.DPI401.getValue())
				.getId();
		Long statusId = communicationStatusRepository.findByStatus("ACCEPTED").getId();

		FiscalYear fiscalYear = fiscalYearRepository.findByYear( year);
		Preconditions.checkNotNull( fiscalYear, "Richiesta la finalizzazione di tutte le comunicazioni del " + year + " ma il FiscalYear non esiste" );
		Preconditions.checkState(fiscalYear.isExpired(), "Richiesta la finalizzazione di tutte le comunicazioni del " + year + " ma l'anno fiscale non risulta scaduto");
		Preconditions.checkState(fiscalYear.getFlagNrStartMessage()>0, "Richiesta la finalizzazione di tutte le comunicazioni del " + year + " ma il comando StartBuildingMessage è già stato inviato");
		Optionals.ifPresentOrElse(
				communicationRepository.findAllByFiscalYearAndComClassIdAndStatusId(year, messageTypeIndic, statusId),
				foundEntities -> {
					log.info("Communications found, start to process them");
					foundEntities.forEach(entity -> {
						// Meglio lanciare l'evento in modo da sincronizzare con altre operazioni ancora non concluse, ancora in coda
						// finalizeCommunication(entity.getId().getComUuid());
						dpiEventSender.sendCommandFinalizeCommunication(entity.getProtocol(), false );
					});
				}, () -> log.info("No Communications expired found, end process"));
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void finalizeCommunication( String protocol ) {
		Communication comByProt = communicationRepository.findByProtocol(protocol);
		Preconditions.checkNotNull(comByProt,"Richiesta finalizzazione di una comunicazione inesistente %", protocol);
		String comUuid = comByProt.getId().getComUuid();
		Communication entityToUpdate = communicationRepository.findByComUuid(comUuid).get();
		// TODO mi sembra strano che il findByProtocol possa individuare univocamente una Comunicazione


		CommunicationStatus finalizedStatusE = communicationStatusRepository.findByStatus("FINALIZED"); // TODO verificare
		CommunicationStatus previousStatus = entityToUpdate.getStatus();
		Preconditions.checkState("ACCEPTED".equals(previousStatus.getStatus()));

		Communication clone;
		try {
			clone = entityToUpdate.clone();
		} catch( CloneNotSupportedException ce ){
			throw new RuntimeException("Non riesco ad effettuare il clone della comunicazione " + comUuid);
		}
		clone.setMessageDate(new Date().toInstant());
		clone.setStatus(finalizedStatusE);
		dpiSaveService.saveCommunication(clone);
		log.info("Communication with id: " + comUuid + "changed was " + finalizedStatusE.getStatus());

		Dac7Unmarshaler dac7Unmarshaler = new Dac7Unmarshaler();
		Optional<CommunicationData> communicationData = communicationDataRepository.findByComUuid(comUuid);
		if( communicationData.isPresent()){
			try {
				Messaggio m = dac7Unmarshaler.unmarshal(new InputStreamReader(new ByteArrayInputStream(communicationData.get().getCommunicationBlob())));
				DPIOECD dpioecd = m.getContenuto().getFornitura().getValue().getDPIOECD();
				dpiService.saveMessage(dpioecd, ""); // TODO createdBy
			} catch (Exception e){
				log.info("Salvataggio JPA della comunicazione " + comUuid + " non riuscito");
				throw new RuntimeException("Salvataggio JPA non riuscito", e);
			}

		}

		// sending event
//			dpiEventSender.sendEventFinalizeCommunication(year, TopicProperties.DPI_NAT_EVENT,
//					entityToUpdate.getId().getComUuid(), isFinished, entityToUpdate.getSenderCf(), Date.from(entityToUpdate.getSentDate()).getTime(), urlDpiComCommand,
//					entityToUpdate.getProtocol(), reportingPeriod, previousStatus);

	}

	@Override
	@Transactional
	public void finalizeCommunication(String idCommunicationB64, MessageHeaders messageHeaders) {
		//Long year = Long.valueOf((String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY));
		String idCommunication = new String(it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils
				.convertBase64ToByteArray(idCommunicationB64));
		finalizeCommunication(idCommunication);
	}

	@Override
	@Transactional
	public void addContributeToEffectiveDest(String commUuid) {
		Optional<Communication> communicationOptional = communicationRepository.findByComUuid(commUuid);
		if(!communicationOptional.isPresent()){
			return;
		}
		Communication communication = communicationOptional.get();

		Optional<CommunicationData> commDataOptional = communicationDataRepository.findByComUuid(commUuid);
		if(!commDataOptional.isPresent()){
			throw new RuntimeException("Manca CommunicationData relativa con uuid=" + commUuid);
		}
		CommunicationData commData = commDataOptional.get();

		List<EffectiveDestEntity> effectiveDests = communication.getEffectiveDests();
		effectiveDests.forEach(effectiveDest -> {
			try {
				DPIOECD dpioecd = dac7unmarshaler.unmarshalDPI(commData.getCommunicationBlob());
				Country country = effectiveDest.getCountryByCountryId();
				effectiveDest.setContributeForBuild(
						buildXMLForEffDest(
								dpioecd,
								country,
								countryService.listCountryOfEffDest(country.getValue())
						));
			} catch( Exception e ){
				throw new RuntimeException("Estrazione Contributo per EffDest non riuscito: " + commUuid);
			}
		});
		dpiSaveService.saveCommunication(communication);
	}


	private byte[] buildXMLForEffDest( DPIOECD dpi, Country countryDestEff, List<String> countryList ){
		try {
			Predicate<CorrectableOtherRPOType> checkOther = DPIUtils.buildPredicateOtherRPOinCountryList(countryList);
			// Elimino tutti gli OtherPlatformOperator relativi ad altri Country
			dpi.getDPIBody().stream()
					.map(DPIBodyType::getOtherPlatformOperators)
					.forEach(otherPlat -> {
                        otherPlat.getAssumedPlatformOperator().removeIf(otherRpo -> !checkOther.test(otherRpo));
					});

			Predicate<CorrectableReportableSellerType> checkSeller =
					DPIUtils.buildPredicateReportableSellerInCountryList(countryList)
							.or(DPIUtils.buildPredicateImmovablePropertyInCountryList(countryList));

			// Elimino tutti i ReportableSeller relativi ad altri Country
			dpi.getDPIBody().forEach( body ->
				body.getReportableSeller().removeIf( seller -> !checkSeller.test(seller))
			);

			// Teoricamente ci potrebbero essere più Body, se così fosse bisognerebbe eliminare eventuali body vuoti

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			dac7marshaller.marshalDPI(dpi, new OutputStreamWriter(bos));
			return bos.toByteArray();
		} catch(JAXBException je){
			throw new RuntimeException("Impossibile effettuare il marshalling", je);
		}
	}

}
