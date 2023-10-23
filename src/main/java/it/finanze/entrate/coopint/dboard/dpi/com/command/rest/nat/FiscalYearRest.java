package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.FiscalYearRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.FiscalYearDto;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Insieme di endpoint per l'accesso agli anni fiscali.
 * 
 * @author mastr
 */
@RestController
@RequestMapping(value = "/national/fiscal-year")
@CommonsLog
public class FiscalYearRest {

	@Autowired
	@Qualifier("fiscalYearRepositoryNazionale")
	private FiscalYearRepository fiscalYearRepository;

	/**
	 * Servizio per l'estrazione dei dati completi di un anno fiscale a partire
	 * dall'anno indicato come numero.
	 * 
	 * @param year l'anno fiscale di interesse
	 * @return un oggetto di tipo <code>FiscalYearDto</code> se l'anno indicato
	 *         esiste
	 */
	@GetMapping("/{year}")
	public ResponseEntity<?> getFiscalYear(@PathVariable("year") long year) {
		try {
			log.info("Estrazione di un singolo fiscal year...");
			if (year < 0) {
				String msg = "Impossibile estrarre un anno negativo";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			FiscalYear fye = this.fiscalYearRepository.findByYear(year);
			if (fye == null) {
				String msg = "Impossibile trovare l'anno indicato";
				log.error(msg);
				return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
			}
			return ResponseEntity.ok(FiscalYearDto.getDto(fye));
		} catch (Exception e) {
			String msg = "Errore durante estrazione di singolo fiscal year";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per l'estrazione di tutti gli anni fiscali esistenti.
	 * 
	 * @return una lista/array di oggetti di tipo <code>FiscalYearDto</code>
	 */
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll() {
		try {
			log.info("Estrazione di tutti i fiscal year...");
			List<FiscalYear> fiscalYearList = fiscalYearRepository.findAll();
			return ResponseEntity.ok(FiscalYearDto.getDtos(fiscalYearList));
		} catch (Exception e) {
			String msg = "Errore durante estrazione di tutti i fiscal year";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per l'estrazione di tutti gli anni fiscali esistenti che non sono
	 * expired.
	 * 
	 * @return una lista/array di oggetti di tipo <code>FiscalYearDto</code>
	 */
	@GetMapping("/get-all-not-expired")
	public ResponseEntity<?> getAllNotExpired() {
		try {
			log.info("Estrazione di tutti i fiscal year non expired...");
			List<FiscalYear> fiscalYearList = fiscalYearRepository.findByExpired(false);
			return ResponseEntity.ok(FiscalYearDto.getDtos(fiscalYearList));
		} catch (Exception e) {
			String msg = "Errore durante estrazione di tutti i fiscal year non expired";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio di aggiornamento della data di expiring di un Fiscal Year. Verranno
	 * utilizzati solo i campi <code>fiscalYear</code> e <code>expirationDate</code>
	 * dell'oggetto <code>FiscalYearDto</code> passato. Verrà restituito il
	 * <code>FiscalYearDto</code> corrispondente al record aggiornato su database, o
	 * un errore se tale record non esiste.
	 * 
	 * @param year l'anno fiscale di interesse da aggiornare
	 * @return un oggetto di tipo <code>FiscalYearDto</code> se l'anno indicato
	 *         esiste ed è stato correttamente aggiornato
	 */
	@PostMapping(value = "/update-expiration-date", consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateExpirationDate(@RequestBody FiscalYearDto fiscalYearToUpdate) {
		try {
			log.info("Aggiornamento data di expiration per un fiscal year...");
			if (fiscalYearToUpdate == null || fiscalYearToUpdate.getFiscalYear() == null) {
				return new ResponseEntity<>("Impossibile aggiornare un anno fiscale nullo.", HttpStatus.BAD_REQUEST);
			}
			log.debug("Anno da aggiornare: " + fiscalYearToUpdate.getFiscalYear());
			FiscalYear toUpdateDb = this.fiscalYearRepository.findByYear(fiscalYearToUpdate.getFiscalYear());
			if (toUpdateDb == null) {
				return new ResponseEntity<>("Impossibile trovare l'anno fiscale specificato.", HttpStatus.BAD_REQUEST);
			}
			Date updatedDate = null;
			try {
				updatedDate = FiscalYearDto.onlyDateItalianFormatter.parse(fiscalYearToUpdate.getExpirationDate());
			} catch (ParseException e) {
				return new ResponseEntity<>("Impossibile elaborare la nuova data per l'aggiornamento.",
						HttpStatus.BAD_REQUEST);
			}
			toUpdateDb.setExtendedExpDate(updatedDate);
			toUpdateDb.setExpired(false);
			toUpdateDb.setExpirationDate(updatedDate);
			toUpdateDb = this.fiscalYearRepository.save(toUpdateDb);
			return ResponseEntity.ok(FiscalYearDto.getDto(toUpdateDb));
		} catch (Exception e) {
			String msg = "Errore durante aggiornamento data di expiration per un fiscal year";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
