package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.MessageTypeIndicDto;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Insieme di endpoint per l'accesso ai message type indic.
 * 
 * @author mastr
 */
@RestController
@RequestMapping(value = "/national/message-type-indic")
@CommonsLog
public class MessageTypeIndicRest {

	/**
	 * Servizio per l'estrazione dei valori possibili per i message type indic, che
	 * fanno riferimento al campo <code>messageTypeIndic</code> delle comunicazioni
	 * stesse. Nella implementazione attuale i valori vengono imposti dal servizio
	 * piuttosto che realmente estratti da risorse del database.
	 * 
	 * @return una lista/array di oggetti del tipo <code>MessageTypeIndicDto</code>
	 */
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll() {
		try {
			log.info("Estrazione di tutti i communication message type indic (mock version)...");
			List<MessageTypeIndicDto> retList = new LinkedList<>();
			MessageTypeIndicDto item1 = new MessageTypeIndicDto();
			item1.setValue("1");
			item1.setDescription("DPI401");
			retList.add(item1);
			MessageTypeIndicDto item2 = new MessageTypeIndicDto();
			item2.setValue("2");
			item2.setDescription("DPI402");
			retList.add(item2);
			MessageTypeIndicDto item3 = new MessageTypeIndicDto();
			item3.setValue("3");
			item3.setDescription("DPI403");
			retList.add(item3);
			return ResponseEntity.ok(retList);
		} catch (Exception e) {
			String msg = "Errore durante estrazione dei communication message type indic (mock version)";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
