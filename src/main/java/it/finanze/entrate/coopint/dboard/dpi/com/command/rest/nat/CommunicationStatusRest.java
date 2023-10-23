package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationStatus;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CommunicationStatusRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.comparator.CommunicationStatusComparator;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationStatusDto;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Insieme di endpoint per l'accesso agli stati delle comunicazioni.
 * 
 * @author mastr
 */
@RestController
@RequestMapping(value = "/national/communication-status")
@CommonsLog
public class CommunicationStatusRest {

	@Autowired
	@Qualifier("communicationStatusRepositoryNazionale")
	private CommunicationStatusRepository communicationStatusRepository;

	/**
	 * Servizio per l'estrazione di tutti gli stati delle comunicazioni esistenti.
	 * 
	 * @return una lista/array di oggetti di tipo
	 *         <code>CommunicationStatusDto</code>
	 */
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll() {
		try {
			log.info("Estrazione di tutti i communication status...");
			List<CommunicationStatus> entList = this.communicationStatusRepository.findAll();
			Collections.sort(entList, new CommunicationStatusComparator());
			return ResponseEntity.ok(CommunicationStatusDto.getDtos(entList));
		} catch (Exception e) {
			String msg = "Errore durante estrazione di tutti i communication status";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
