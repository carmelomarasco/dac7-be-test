package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.*;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiRestTemplateService;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.DPIUtils;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.*;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.ComClass;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.Status;
import it.sogei.generateuuid.GenerateUUID;
import it.sogei.generateuuid.enumeration.Domain;
import it.sogei.generateuuid.enumeration.AggregateType;
import oecd.ties.dpi.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiSaveService;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.DateUtil;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class DpiSaveServiceImpl implements DpiSaveService {
    @Autowired
    @Qualifier("dboardDpiCountryRepositoryNazionale")
    DboardDpiCountryRepository dboardDpiCountryRepository;
    @Autowired
    @Qualifier("communicationCodesRepositoryNazionale")
    CommunicationCodesRepository communicationCodesRepository;
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
    @Qualifier("comClassRepositoryNazionale")
    ComClassRepository comClassRepository;
    @Autowired
    @Qualifier("effectiveDestRepositoryNazionale")
    EffectiveDestRepository effectiveDestRepository;
    @Autowired
    @Qualifier("fiscalYearRepositoryNazionale")
    FiscalYearRepository fiscalYearRepository;
    @Autowired
    @Qualifier("messageTypeIndicRepositoryNazionale")
    MessageTypeIndicRepository messageTypeIndicRepository;
    @Autowired
    @Qualifier("countryRepositoryNazionale")
    CountryRepository countryRepository;
    @Autowired
    DpiRestTemplateService dpiRestTemplateService;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Communication saveAcquiredCommunication(
            DpiCommunicationPayload dpiCommunication,
            String xmlGzipBase64,
            DPIOECD dpioecd,
            List<Country> countriesList,
            AllInfoAcquiredBean allInfoAcquiredBean) throws Exception {

        //*Build Communication*/
        //Communication com = Communication.builder().build();
        Communication comEntity = buildEntity( dpiCommunication, dpioecd, countriesList);
        Communication comEntityPersisted = communicationRepository.saveAndFlush(comEntity);

        CommunicationData comData = CommunicationData.buildEntity(comEntityPersisted.getId().getComUuid(), xmlGzipBase64);
        communicationDataRepository.saveAndFlush(comData);

        // TODO salvare le informazioni CommunicationCodes
        // riempire con IN o TIN presi da PlatformOperator e da OtherPlatform...
        List<CommunicationCodes> communicationCodesList = buildCommunicationCodes( dpioecd);
        communicationCodesList.forEach( cCodes -> {
            cCodes.setComUuid(comEntityPersisted.getId().getComUuid());
            communicationCodesRepository.saveAndFlush(cCodes);
        });

        String messageTypeIndic = comEntityPersisted.getMessageTypeIndicByMessageTypeIndicId().getValue();
        //*Build CommunicationBean to send to nat-view*/
        allInfoAcquiredBean.setCommunicationBean(CommunicationBean.buildDTO(comEntityPersisted, dpiCommunication));
        //Build payload for notify
        allInfoAcquiredBean.setDpiNationalNotifyBean(DpiNationalNotifyBean.buildBeanForReceivedComNotify(comEntityPersisted, messageTypeIndic, dpiCommunication.getStatus(), dpiCommunication.getIntention()));
        //Build history bean
        allInfoAcquiredBean.setHistoryBean(HistoryBean.buildBean(comEntityPersisted, dpiCommunication.getIntention()));
        //Other info
        allInfoAcquiredBean.setReportingPeriod( Date.from(
                comEntityPersisted.getReportingPeriod().withMonth(12).withDayOfMonth(31)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        allInfoAcquiredBean.setFyForCurrentComIsExpired(
                fiscalYearRepository.findByYear((long)comEntityPersisted.getReportingPeriod().getYear())
                        .isExpired()
        );
        allInfoAcquiredBean.setMessageTypeIndic(messageTypeIndic);

        return comEntityPersisted;
    }

    List<CommunicationCodes> buildCommunicationCodes( DPIOECD dpioecd){
        List<CommunicationCodes> list = Lists.newArrayList();
        dpioecd.getDPIBody().stream()
                .flatMap(body -> Stream.of(body.getPlatformOperator()))
                .forEach( platOp -> {
                    platOp.getName().forEach( name -> {
                        list.add(CommunicationCodes.builder()
                                .denominazione(name.getValue())
                                .type("PLATFORM_OPERATOR")
                                .build()
                        );
                    });
                    platOp.getIN().forEach(in -> {
                            list.add(CommunicationCodes.builder()
                                    .type("PLATFORM_OPERATOR")
                                    .in(in.getValue())
                                    .build()
                            );
                    });
                    platOp.getTIN().forEach(tin -> {
                        list.add(CommunicationCodes.builder()
                                .type("PLATFORM_OPERATOR")
                                .tin(tin.getValue())
                                .build()
                        );
                    });
                });
        dpioecd.getDPIBody().stream()
                .flatMap(body -> Stream.of(body.getOtherPlatformOperators()))
                .filter(Objects::nonNull)
                .flatMap( other -> other.getAssumedPlatformOperator().stream())
                .forEach( other -> {
                    list.add(CommunicationCodes.builder()
                            .denominazione(other.getName().getValue())
                            .type("OTHER_PLATFORM_OPERATOR")
                            .build()
                    );
                    other.getTIN().forEach(tin -> {
                        list.add(CommunicationCodes.builder()
                                .type("OTHER_PLATFORM_OPERATOR")
                                .tin(tin.getValue())
                                .build()
                        );
                    });
                });
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveCommunication(Communication comEntity) {
    	log.info("Saving communication with id: " + comEntity.getId().getComUuid());
        communicationRepository.saveAndFlush(comEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFiscalYear(FiscalYear fiscalYear) {
        fiscalYearRepository.saveAndFlush(fiscalYear);
    }



    public Communication buildEntity( DpiCommunicationPayload dpip,
                                      DPIOECD dpioecd,
                                      List<Country> countriesList) throws Exception {
        Communication.CommunicationBuilder builder = Communication.builder();

        MessageSpecType messageSpec = dpioecd.getMessageSpec();
        // ** get fiscal year*/
        LocalDate reportingPeriod = DateUtil.toLocalDate(messageSpec.getReportingPeriod());
        long yearFromDate = (long)reportingPeriod.getYear();
        log.info("yearFromDate: " + yearFromDate);
        FiscalYear fye = fiscalYearRepository.findById(yearFromDate)
                .orElseThrow(()->new RuntimeException("Non trovo il FiscalYear " + yearFromDate));

        // ** get messageTypeInd*/
        MessageTypeIndic mtie = messageTypeIndicRepository
                .findByValue(messageSpec.getMessageTypeIndic().value());

        // inspect receipt to check if communication is CAUTIONED
        String receipt = new String(IOUtils.convertGzippedBase64ToByteArray(dpip.getMetadatiGZipBase64()),
                StandardCharsets.UTF_8);
        // ** get statusType*/
        // caso in cui arrivi una comunicazione di annullamento, questa deve essere
        // salvata in stato ACCEPTED_NON_CONTRIBUTING
        if (dpip.getIntention().equalsIgnoreCase(ComClass.INVALIDATE.getValue())
                && !dpip.getStatus().equals(Status.REJECTED.getValue()))
            dpip.setStatus(Status.ACCEPTED_NON_CONTRIBUTING.getValue());
            // caso in cui arrivi una comunicazione nuovi dati col flag late settato a 1,
            // questa deve essere salvata NUOVI DATI FUORI DAI TERMINI
        else if (dpip.getIntention().equalsIgnoreCase(ComClass.NEW_DATA_ON_TERMS.getValue())
                && fye.isExpired()) {
            // setto l'intention del bean
            dpip.setIntention(ComClass.NEW_DATA_OUT_OF_TERMS.getValue());
        }

        String statusToInsertForThisCom = dpip.getStatus();

        CommunicationStatus cs = communicationStatusRepository
                .findByStatus(statusToInsertForThisCom);

        // **get comClassEntity by intention */
        ComClassEntity cce = comClassRepository.findByComClassDescription(dpip.getIntention());

        // TODO sostituire con Domain.DPI_NATIONAL
        String comUuid = GenerateUUID.generate(Domain.CRS_NATIONAL, AggregateType.COMMUNICATION);
        Date data = new Date();
        CommunicationId comId = new CommunicationId();
        comId.setComUuid(comUuid);
        comId.setData(data.toInstant());

        String platformOperatorName = "";
        if( !dpioecd.getDPIBody().isEmpty()){
            CorrectablePlatformOperatorType po = dpioecd.getDPIBody().get(0).getPlatformOperator();
            if( !po.getName().isEmpty()) {
                platformOperatorName = po.getName().get(0).getValue();
            }
        }

        return Communication.builder()
                .id(comId)
                // .fiscalYear(fye.getYear())
                .protocol(dpip.getProtocol())
                .senderCf(dpip.getSendingFiscalCode())
                .sentDate(DateUtil.toDateWithTime(dpip.getSentDate()).toInstant())
                .receipt(receipt == null || receipt.isEmpty() ? "NO-RECEIPT-AVAILABLE" : receipt)
                .status(cs)
                .sendingEntityIn(dpioecd.getMessageSpec().getSendingEntityIN())
                .transmittingCountry( countryRepository.findByValue(dpioecd.getMessageSpec().getTransmittingCountry().value()))
                .receivingCountry(countryRepository.findByValue(dpioecd.getMessageSpec().getReceivingCountry().value()))
                .messageType(dpioecd.getMessageSpec().getMessageType().value())
                .messageRefId(dpip.getMessageRefId())
                .messageTypeIndic(mtie)
                .comClass(cce)
                .reportingPeriod(reportingPeriod)
                .messageDate(DateUtil.xmlGregorianCalendarToDate(dpioecd.getMessageSpec().getTimestamp()).toInstant())
                .platformOperatorName(platformOperatorName)
                .reportableSellersCount(
                    dpioecd.getDPIBody().stream()
                            .map(DPIBodyType::getReportableSeller)
                            .mapToLong(Collection::size)
                            .sum()
                )
                .potentialDests(
                        countriesList != null ? PotentialDestEntity.buildListEntities(countriesList, comUuid, data)
                                : Collections.emptyList())
                .effectiveDests(countriesList != null
                        ? buildListEffectiveDest(countriesList, comUuid, data,dpioecd)
                        : Collections.emptyList())
                .messageTypeIndicByMessageTypeIndicId(mtie)
                .build();
    }

    List<EffectiveDestEntity> buildListEffectiveDest(List<Country> potentialCountries, String comUuid,
                                                     Date data, DPIOECD dpioecd) {
        Map<String, Country> mapCountryFromIso = countryRepository.findAll().stream()
                .collect(Collectors.toMap(Country::getValue, Function.identity()));

        Map<String, Country> mapEffDestPerCountry = Maps.newHashMap();

        Map<Long, DboardDpiCountry> mapDpiCountryFromId = dboardDpiCountryRepository.findAll().stream()
                .collect(Collectors.toMap(DboardDpiCountry::getCountryDestinationId, Function.identity()));
        // Associo ad ogni Country il Country di riferimento per le comunicazioni
        mapDpiCountryFromId.values().forEach(dpiCountry -> {
            DboardDpiCountry dpiParent = mapDpiCountryFromId.get(dpiCountry.getCountryId());
            Country countryParent = mapCountryFromIso.get(dpiParent.getCountryCode());
            mapEffDestPerCountry.put(dpiCountry.getCountryCode(), countryParent);
        });
        Set<Country> effDestCountry = Sets.newHashSet();
        potentialCountries.forEach( potentialCountry -> {
            Country destCountry = mapEffDestPerCountry.get(potentialCountry.getValue());
            if( destCountry != null ){
                effDestCountry.add(destCountry);
            }
        });
        log.info("Number of dests from communication: " + effDestCountry.size());
        return effDestCountry.stream()
                .map( c -> {
                    // COstruisco elenco figli
                    List<String> countryList = mapEffDestPerCountry.entrySet().stream()
                            .filter(entry -> c.equals(entry.getValue()))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                    Predicate<CorrectableReportableSellerType> repSellerPredicate = DPIUtils.buildPredicateReportableSellerInCountryList(countryList);
                    Predicate<CorrectableReportableSellerType> immPropPredicate = DPIUtils.buildPredicateImmovablePropertyInCountryList(countryList);
                    EffectiveDestEntity effectiveDest = EffectiveDestEntity.builder()
                            .comUuid(comUuid)
                            .data(data)
                            .countryId(c.getId())
                            .reportableSellerCount(
                                    dpioecd.getDPIBody().stream()
                                    .map(DPIBodyType::getReportableSeller)
                                    .flatMap(Collection::stream)
                                    .filter(repSellerPredicate)
                                    .count()
                            )
                            .immovablePropertyCount(
                                    dpioecd.getDPIBody().stream()
                                            .map(DPIBodyType::getReportableSeller)
                                            .flatMap(Collection::stream)
                                            .filter(immPropPredicate)
                                            .count()
                            )
                            .build();
                    // Effettuare in asincrono con eventi kafka
                    //effectiveDest.setContributeForBuild( buildXMLForEffDest(dpioecd));
                    return effectiveDest;
                })
                .collect(Collectors.toList());
    }

}
