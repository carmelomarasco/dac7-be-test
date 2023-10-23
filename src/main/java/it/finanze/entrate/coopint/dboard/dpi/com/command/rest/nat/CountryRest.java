package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CountryRepository;
import it.finanze.entrate.coopint.dboard.dpi.common.comparator.CountryByDescriptionComparator;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CountryDto;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Insieme di endpoint per l'accesso ai paesi (nazioni).
 * 
 * @author mastr
 */
@RestController
@RequestMapping(value = "/national/country")
@CommonsLog
public class CountryRest {

	/**
	 * Riporta tutti le sigle internazionali dei paesi della UE tranne l'Italia. I
	 * paesi in particolare sono nell'ordine i seguenti: Austria, Belgio, Bulgaria,
	 * Cipro, Croazia, Danimarca, Estonia, Finlandia, Francia, Germania, Grecia,
	 * Irlanda, Italia (esclusa), Lettonia, Lituania, Lussemburgo, Malta, Paesi
	 * Bassi, Polonia, Portogallo, Repubblica Ceca, Romania, Slovacchia, Slovenia,
	 * Spagna, Svezia, Ungheria
	 */
	private static final Set<String> ueCountriesExceptItaly = new HashSet<>(
			Arrays.asList("AT", "BE", "BG", "CY", "HR", "DK", "EE", "FI", "FR", "DE", "GR", "IE", "LV", "LT", "LU",
					"MT", "NL", "PL", "PT", "CZ", "RO", "SK", "SI", "ES", "SE", "HU"));

	@Autowired
	@Qualifier("countryRepositoryNazionale")
	private CountryRepository countryRepository;

	/**
	 * Servizio per l'estrazione di tutti i paesi esistenti.
	 * 
	 * @return una lista/array di oggetti di tipo <code>CountryDto</code>
	 */
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll() {
		try {
			log.info("Estrazione di tutti i country...");
			List<Country> entList = this.countryRepository.findAll();
			entList.sort(new CountryByDescriptionComparator());
			return ResponseEntity.ok(CountryDto.getDtos(entList));
		} catch (Exception e) {
			String msg = "Errore durante estrazione di tutti i country";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Servizio per l'estrazione di tutti i paesi UE tranne l'Italia.
	 * 
	 * @return una lista/array di oggetti di tipo <code>CountryDto</code>
	 */
	@GetMapping("/get-all-ue-less-it")
	public ResponseEntity<?> getAllUeLessItaly() {
		try {
			log.info("Estrazione di tutti i country in UE tranne l'Italia...");
			List<CountryDto> retList = new LinkedList<>();
			List<Country> entList = this.countryRepository.findAll();
			entList.sort(new CountryByDescriptionComparator());
			for (Country cou : entList) {
				if (CountryRest.ueCountriesExceptItaly
						.contains(StringUtils.toRootUpperCase(StringUtils.trimToEmpty(cou.getValue())))) {
					retList.add(CountryDto.getDto(cou));
				}
			}
			return ResponseEntity.ok(retList);
		} catch (Exception e) {
			String msg = "Errore durante estrazione di tutti i country in UE tranne l'Italia";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
