package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import it.finanze.entrate.coopint.dboard.dpi.com.command.excel.InterrogationResultExcelCom;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationCodes;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationData;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CommunicationCodesRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CommunicationDataRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CommunicationRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationCodesDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationSearchDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CountryCoupleDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CountryDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
import it.finanze.security.enumeration.Role;
import it.finanze.security.util.UserUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpHeaders;

/**
 * Insieme di endpoint per l'accesso alle comunicazioni.
 * 
 * @author mastr
 */
@RestController
@RequestMapping(value = "/national/communication")
@CommonsLog
public class CommunicationRest {

	public static final SimpleDateFormat onlyDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	//@Autowired
	//NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("communicationRepositoryNazionale")
	CommunicationRepository communicationRepository;

	@Autowired
	@Qualifier("communicationDataRepositoryNazionale")
	CommunicationDataRepository communicationDataRepository;

    @Autowired
	InterrogationResultExcelCom interrogationResultExcel;

	@Autowired
    HttpServletRequest request;

	@Autowired
	@Qualifier("communicationCodesRepositoryNazionale")
	CommunicationCodesRepository communicationCodesRepository;
	/*
	 * Search communication Nell'url vengono passati: comUuid dataRicezioneDa,
	 * dataRicezioneA // la messageDate identificativoComunicazione // il
	 * messageRefId protocollo tipologiaMes // "NUOVO MESSAGGIO", "ANNULLAMENTO" -->
	 * messageType tipologiaCom // "DPI401", "DPI402", "DPI403" --> messageTypeIndic
	 * esitoRicezione // ELIMINARE statoComunicazione destinazione // SCELGO TRA GLI
	 * STATI MA SOLO EUROPEI, FILTRO SULLE effectiveDest e sulle potentialDest della
	 * comunicazione
	 * 
	 * cercare tutte le comunicazioni che corrispondono a questi valori di ricerca.
	 * Delle comunicazioni così trovate, restituire solo i campi nella lista sopra
	 */

	/**
	 * Servizio per la ricerca di comunicazioni rispondenti a determinati filtri. Il
	 * parametro <code>searchParameters</code> di tipo
	 * <code>CommunicationSearchDto</code> specifica i filtri possibili, che saranno
	 * considerati in AND logico tra loro ma limitatamente a quelli effettivamente
	 * specificati, cioè che non hanno un valore vuoto o nullo.
	 * 
	 * @param searchParameters i filtri di ricerca da applicare
	 * 
	 * @return una lista/array di oggetti di tipo <code>CommunicationDto</code>
	 */
	@PostMapping(value = "/search")
	public ResponseEntity<?> search(@RequestBody CommunicationSearchDto searchParameters) {
		try {
			log.info("Ricerca di comunicazioni mediante filtri...");
			UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());
			if (searchParameters == null) {
				String msg = "Impossibile ricercare comunicazioni da parametri di ricerca nulli";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			List<CommunicationDto> retList = new LinkedList<>();
			List<Communication> ents = this.communicationRepository.findAll();
			if (!CollectionUtils.isEmpty(ents)) {
				for (Communication ent : ents) {
					CommunicationData cdata = this.communicationDataRepository.findByComUuid(StringUtils.trimToEmpty(ent.getId().getComUuid())).get();
					CommunicationDto dto = CommunicationDto.getDto(ent, cdata);
					boolean addToResults = true;
					if (addToResults && !StringUtils.isBlank(searchParameters.getMessageDateFrom())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getMessageDateFrom());
						try {
							Date passedDate = CommunicationRest.onlyDateFormatter.parse(passedPar);
							if (Date.from(ent.getMessageDate()).compareTo(passedDate) < 0) {
								addToResults = false;
							}
						} catch (Exception exc) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getMessageDateTo())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getMessageDateTo());
						try {
							Date passedDate = CommunicationRest.onlyDateFormatter.parse(passedPar);
							if (Date.from(ent.getMessageDate()).compareTo(passedDate) > 0) {
								addToResults = false;
							}
						} catch (Exception exc) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getMessageRefId())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getMessageRefId());
						if (!dto.getMessageRefId().equals(passedPar.toUpperCase())) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getProtocol())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getProtocol());
						if (!dto.getProtocol().equals(passedPar)) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getMessageType())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getMessageType());
						if (!dto.getMessageTypeIndic().getDescription().equals(passedPar)) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getMessageTypeIndic())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getMessageTypeIndic());
						if (!dto.getComClass().getDescription().equals(passedPar)) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getStatoComunicazione())) {
						if (!String.valueOf(dto.getStatus().getDescription()).equals(searchParameters.getStatoComunicazione())) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getDestinationCountryId())) {
						boolean found = false;
						for (CountryDto cdto : dto.getEffectiveDestsCountries()) {
							if (cdto.getValue().equals(String.valueOf(searchParameters.getDestinationCountryId()))) {
								found = true;
								break;
							}
						}
						if (!found) {
							for (CountryDto cdto : dto.getPotentialDestsCountries()) {
								if (cdto.getValue().equals(String.valueOf(searchParameters.getDestinationCountryId()))) {
									found = true;
									break;
								}
							}
						}
						if (!found) {
							addToResults = false;
						}
					}
					// senderCf oppure TIN/IN della communication_codes
					if (addToResults && !StringUtils.isBlank(searchParameters.getCfUser())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getCfUser());
						if (!dto.getSenderCf().equals(passedPar.toUpperCase())) {
							addToResults = false;
						}
						List<CommunicationCodes> codes = communicationCodesRepository.findByComUuidAndCf(cdata.getComUuid(), passedPar);
						if (!codes.isEmpty()) {
							for (CommunicationCodes cc : codes) {
								if ((cc.getIn() != null && !cc.getIn().equals(passedPar)) || (cc.getTin() != null && !cc.getTin().equals(passedPar))) {
									addToResults = false;
								}
							}
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getAnnoFiscale())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getAnnoFiscale());
						try {
							Integer passedInt = Integer.valueOf(passedPar);
							if (passedInt.intValue() != (ent.getReportingPeriod().getYear())) {
								addToResults = false;
							}
						} catch (Exception exc) {
							addToResults = false;
						}
					}
					if (addToResults && !StringUtils.isBlank(searchParameters.getDenominazione())) {
						String passedPar = StringUtils.trimToEmpty(searchParameters.getDenominazione());
						List<CommunicationCodes> codes = communicationCodesRepository.findByComUuidAndDenominazione(cdata.getComUuid(), passedPar);
						if (codes.isEmpty()) {
							addToResults = false;
						}
					}
					if (addToResults) {
						// ricerco i codes da aggiungere al dto
						List<CommunicationCodes> codes = this.communicationCodesRepository.findByComUuid(cdata.getComUuid());
						if (!codes.isEmpty()) {
							for (CommunicationCodes cc : codes) {
								dto.getCodesDto().add(CommunicationCodesDto.getDto(cc));
							}
						}
						retList.add(dto);
					}
				}
			}
			return ResponseEntity.ok(retList);
		} catch (Exception e) {
			String msg = "Errore durante ricerca di comunicazioni mediante filtri";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/search-fast")
	public ResponseEntity<?> searchFast(@RequestBody CommunicationSearchDto searchParameters) {
		try {
			log.info("Ricerca (veloce) di comunicazioni mediante filtri...");
			if (searchParameters == null) {
				String msg = "Impossibile ricercare comunicazioni da parametri di ricerca nulli";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			List<CommunicationDto> retList = new LinkedList<>();

			MapSqlParameterSource parameters = new MapSqlParameterSource();
			boolean firstCondition = true;
			String query = "SELECT comm.* FROM COMMUNICATION AS comm";

			/*
			 * MapSqlParameterSource parameters = new MapSqlParameterSource();
			 * parameters.addValue("idOrganizzazione", idOrganizzazione);
			 * 
			 * String
			 * query="(SELECT cli.id AS idCliente, cli.id_organizzazione AS idOrganizzazione, cli.attivo, cli.persona_fisica,\r\n"
			 * +
			 * "	   cli.nome, cli.cognome, cli.denominazione_sintesi, cli.codice_fiscale, cli.email_fatturazione,\r\n"
			 * + "       cli.ragione_sociale, cli.partita_iva, cli.numero_telefonico,\r\n" +
			 * "       cli.id_termini_pagamento, cli.id_aliquota_iva, cli.id_modalita_pagamento,\r\n"
			 * +
			 * "       aliq.descrizione AS descrizioneAliQuotaIva, aliq.valore AS valoreAliQuotaIva,\r\n"
			 * +
			 * "       mp.descrizione AS descrizioneModalitaPagamento, mp.nome AS nomeModalitaPagamento,\r\n"
			 * +
			 * "       tp.descrizione AS descrizioneTerminiPagamento, tp.giorni_pagamento,\r\n"
			 * +
			 * "       result.idTipologiaAttuale, result.nomeTipologiaAttuale, result.idsTipologieCliente, result.nomiTipologieCliente,\r\n"
			 * + "       result.dataInizio, result.dataFine,\r\n" +
			 * "       result2.idsClassificazioneCliente, result2.valoriClassificazioneCliente, result2.codiciTipologieClassificazioneCliente,\r\n"
			 * + "       geo.admin_area_3_long\r\n" +
			 * "FROM (((cliente cli LEFT OUTER JOIN aliquota_iva aliq ON cli.id_aliquota_iva = aliq.id)\r\n"
			 * +
			 * "				     LEFT OUTER JOIN modalita_pagamento mp ON cli.id_modalita_pagamento = mp.id)\r\n"
			 * +
			 * "                     LEFT OUTER JOIN termini_pagamento tp ON cli.id_termini_pagamento = tp.id)     \r\n"
			 * +
			 * "     LEFT OUTER JOIN(SELECT c.id AS idCliente, tp.id AS idTipologiaAttuale, tp.nome AS nomeTipologiaAttuale,\r\n"
			 * +
			 * "							GROUP_CONCAT(tp.id ORDER BY stp.data_inizio DESC SEPARATOR '_%&%_') AS idsTipologieCliente,\r\n"
			 * +
			 * "                            GROUP_CONCAT(tp.nome ORDER BY stp.data_inizio DESC SEPARATOR '_%&%_') AS nomiTipologieCliente,\r\n"
			 * +
			 * "							GROUP_CONCAT(stp.data_inizio ORDER BY stp.data_inizio DESC SEPARATOR '_%&%_') AS dataInizio,\r\n"
			 * +
			 * "                            GROUP_CONCAT(stp.data_fine ORDER BY stp.data_inizio DESC SEPARATOR '_%&%_') AS dataFine\r\n"
			 * +
			 * "					 FROM cliente c LEFT OUTER JOIN storico_tipologia_cliente stp ON c.id = stp.id_cliente \r\n"
			 * +
			 * "									LEFT OUTER JOIN tipologia_cliente tp ON stp.id_tipologia_cliente = tp.id\r\n"
			 * +
			 * "                     GROUP BY c.id) AS result ON result.idCliente = cli.id\r\n"
			 * + "                     \r\n" +
			 * "	 LEFT OUTER JOIN cliente_geolocation cligeo ON cli.id = cligeo.id_cliente\r\n"
			 * +
			 * "     LEFT OUTER JOIN geolocation geo ON cligeo.id_geolocation = geo.id    \r\n"
			 * + "     \r\n" + "     LEFT OUTER JOIN (SELECT c.id AS idCliente, \r\n" +
			 * "							 GROUP_CONCAT(cc.id_classificazione_cliente SEPARATOR '_%&%_') AS idsClassificazioneCliente, \r\n"
			 * +
			 * "                             GROUP_CONCAT(cc.valore SEPARATOR '_%&%_') AS valoriClassificazioneCliente,\r\n"
			 * +
			 * "                             GROUP_CONCAT(tcc.codice SEPARATOR '_%&%_') AS codiciTipologieClassificazioneCliente\r\n"
			 * +
			 * "					  FROM cliente c LEFT OUTER JOIN cliente_classificazione cc ON c.id = cc.id_cliente\r\n"
			 * +
			 * "									 LEFT OUTER JOIN classificazione_cliente clc ON cc.id_classificazione_cliente = clc.id\r\n"
			 * +
			 * "                                     LEFT OUTER JOIN tipologia_classificazione_cliente tcc ON clc.id_tipologia_classificazione = tcc.id\r\n"
			 * +
			 * "					  GROUP BY c.id) AS result2 ON result2.idCliente = cli.id\r\n"
			 * + "                      \r\n" +
			 * "WHERE cli.id_organizzazione = :idOrganizzazione " +
			 * "ORDER BY cli.denominazione_sintesi ASC, cli.ragione_sociale ASC, cli.cognome ASC, cli.nome ASC)"
			 * ;
			 * 
			 * return jdbcTemplate.query(query, parameters, new ClienteRawMapper());
			 * 
			 */

			return ResponseEntity.ok(retList);
		} catch (Exception e) {
			String msg = "Errore durante ricerca di comunicazioni mediante filtri";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per il prelievo del blob (file XML) relativo ad una determinata comunicazione.
	 * 
	 * @param comUuid l'identificatore della comunicazione
	 * 
	 * @return il byte array codificato in base 64 del file XML
	 */
	@GetMapping(value = "/get-blob")
    public ResponseEntity getBlobByComUuid(String comUuid) throws IOException {
		try {
			log.info("Ricerca di un communication blob, uuid: " + comUuid);
			UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());
			if (StringUtils.isBlank(comUuid)) {
				String msg = "Impossibile effettuare una estrazione di un communication blob da parametri nulli";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			Optional<CommunicationData> cdata = this.communicationDataRepository.findByComUuid(StringUtils.trimToEmpty(comUuid));
			if (cdata.isPresent()) {
				byte[] xmlGzip = cdata.get().getCommunicationBlob();
				return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=COMUNICAZIONE_DAC7_" + comUuid + ".xml.gz")
					.contentType(MediaType.parseMediaType("application/xml"))
					.body(new ByteArrayResource(xmlGzip));
			} else {
				String msg = "Impossibile trovare i dati della comunicazione specificata";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			String msg = "Errore durante ricerca di un communication blob";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per il prelievo del blob (file XLSX) relativo ad una determinata comunicazione.
	 * 
	 * @param comUuid l'identificatore della comunicazione
	 * 
	 * @return il byte array codificato in base 64 del file XLSX
	 */
	@GetMapping(value = "/get-xlsx")
    public ResponseEntity getExcelByComUuid(String comUuid) throws IOException {
		try {
			log.info("Ricerca di un communication xlsx, uuid: " + comUuid);
			if (StringUtils.isBlank(comUuid)) {
				String msg = "Impossibile effettuare una estrazione di un communication xlsx da parametri nulli";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			Optional<CommunicationData> cdata = this.communicationDataRepository.findByComUuid(StringUtils.trimToEmpty(comUuid));
			if (cdata.isPresent()) {
				byte[] excelGzip = cdata.get().getExcelBlob();
				return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=COMUNICAZIONE_DAC7_" + comUuid + ".xlsx.gz")
					.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
					.body(new ByteArrayResource(excelGzip));
			} else {
				String msg = "Impossibile trovare i dati della comunicazione specificata";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			String msg = "Errore durante ricerca di un communication xlsx";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per il prelievo del blob (file XLSX) relativo ad una determinata comunicazione.
	 * 
	 * @param comUuid l'identificatore della comunicazione
	 * 
	 * @return il byte array codificato in base 64 del file XLSX
	 */
	@GetMapping(value = "/get-countries/{comUuid}")
	public ResponseEntity<?> getCountriesByComUuid(@PathVariable(value = "comUuid") String comUuid) {
		try {
			log.info("Ricerca dei country di una communication, uuid: " + comUuid);
			if (StringUtils.isBlank(comUuid)) {
				String msg = "Impossibile effettuare una estrazione dei country di una communication da parametri nulli";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			Optional<Communication> c = this.communicationRepository.findByComUuid(comUuid);
			if (c.isPresent()) {
				return ResponseEntity.ok(new CountryCoupleDto(c.get()));
			} else {
				String msg = "Impossibile trovare i dati della comunicazione specificata";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			String msg = "Errore durante ricerca dei country di una communication";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// preso dal crs
	/*
	 * @GetMapping(value = "/getBlobByComUuid/{comUuid}") public
	 * ResponseEntity<String> getBlobByComUuid(@PathVariable(value = "comUuid")
	 * String comUuid) {
	 * 
	 * log.info("search for communication uuid ---> " + comUuid);
	 * Optional<ComDataEntity> optComDataEntity =
	 * comDataRepository.findByComUuid(comUuid);
	 * 
	 * if (optComDataEntity.isPresent()) { log.info("Found com..."); String
	 * xmlBase64 =
	 * IOUtils.convertByteArrayToBase64(optComDataEntity.get().getComBlob()); return
	 * ResponseEntity.ok(xmlBase64); } else { log.info("Not found com..."); return
	 * ResponseEntity.notFound().build(); }
	 * 
	 * }
	 */

	// scrivere funzione analoga a quella di sopra, che dal blob restituisca un file
	// excel
	/*
	 * @GetMapping (value = "/excel.....")
	 */

	// metodo che riceve l'uuid della comunicazione e restisca le countries
	// coinvolte nella comunicazione (receiving country e transmitting country)

	/*
	 * detail-countries (valutare se sopstare uin altra classe) viene passato il
	 * communication uuid in dac2 viene restituita un array di json che hanno come
	 * valori: { 'comUuid' : "", 'countryCode' : "", 'accountCount' : 0,
	 * 'totalSubjects' : 0, 'ahPersonCount' : 0, 'ahOrganisationCount' : 0,
	 * 'controllingPersonCount' : 0, 'built' : false, 'lastBuildDate' : "",
	 * 'lastBuildMessageref' : "", 'lastBuildMrefStatus' : "", 'lastBuildSentDate' :
	 * "", }
	 */

	/*
	 * nella ui si distingue tra Esito e Stato della comunicazione. I valori
	 * relativi allo stato si trovano nella tabella communication_status. L'esito
	 * prevede solo due valori, accettato o scartato, sono sempre quelli che si
	 * trovano in communication_status? La tabella communication è legata alle
	 * tabelle Country, Communication_status e message_type indic, capire dove
	 * prendere i valori di esito.
	 */

	/*
	 * endpoint che restituisca la tipologia di messaggio. I valori bisogna
	 * prenderli nella tabella MESSAGE_TYPE_INDIC? DUBBIO PERCHè LA TABELLA NON è
	 * VALORIZZATA
	 */

	/*
	 * endopoint che restituisca la tipologia di comunicazione, da dove prenderlo?
	 */


	/**
	 * Servizio per il prelievo dei blobs (file XML) relativi a determinate comunicazioni.
	 * 
	 * @param comUuids lista degli identificatori delle comunicazioni
	 * 
	 * @return lista di byte array codificate in base 64 dei file XML
	 */
	@PostMapping(value = "/get-blobs")
	public ResponseEntity<?> getBlobsByComUuid(@RequestBody List<String> comUuids) {
		try {
			log.info("Ricerca di più communication blobs, uuid: " + comUuids.toString());
			if (CollectionUtils.isEmpty(comUuids)) {
				String msg = "Impossibile effettuare una estrazione di più communication blob da parametri nulli";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			List<ResponseEntity<?>> results = new ArrayList<>();
			for (String s: comUuids) {
				ResponseEntity<?> res = this.getBlobByComUuid(s);
				if (res.getStatusCode().equals(HttpStatus.OK)) {
					results.add(res);
				} else {
					String msg = "Impossibile trovare i blob della comunicazione con comUuid: " + s;
					log.warn(msg);
				}
			}
			if (!CollectionUtils.isEmpty(results)) {
				return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=COMUNICAZIONE_DAC7_" + System.nanoTime() + ".xlsx")
				.body(results);
			} else {
				String msg = "Impossibile trovare i blob delle comunicazioni specificate";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			String msg = "Errore durante ricerca di più communication blob";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per il recupero della tabella di ricerca delle comunicazioni.
	 * 
	 *
	 * @return lista di byte array codificate in base 64 dei file Excel
	 */
	@GetMapping(value = "/get-xlsxs")
	public ResponseEntity getExcels(CommunicationSearchDto searchParameters) throws Exception {
		try {
			log.info("Ricerca delle comunicazioni a partire da searchParameters ");

			ResponseEntity<?> response = this.search(searchParameters);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				byte[] excel = this.getTableExcel((List<CommunicationDto>) response.getBody(), searchParameters);
				
				byte[] zip = IOUtils.zip(excel, "COMUNICAZIONI_DAC7.xlsx");
		
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "EXCEL_COMUNICAZIONI_CRS_" + DateUtils.convertDateToString(new Date()).replace(" ", "_") + ".zip")
						.contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						.body(new ByteArrayResource(zip));
			} else {
				String msg = "Impossibile trovare comunicazioni in base ai filtri";
				log.warn(msg); 
			}
		} catch (Exception e) {
			String msg = "Errore durante ricerca di più communication xlsxs";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	public byte[] getTableExcel(List<CommunicationDto> list, CommunicationSearchDto searchFormBean) throws IOException {
		return interrogationResultExcel.build(list, searchFormBean);
	}
}
