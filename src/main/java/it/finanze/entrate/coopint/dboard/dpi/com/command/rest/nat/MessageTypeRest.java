package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.ComClassEntity;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.ComClassRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.MessageTypeDto;
import lombok.extern.apachecommons.CommonsLog;

/**
 * Insieme di endpoint per l'accesso ai tipo messaggi.
 * 
 * @author mastr
 */
@RestController
@RequestMapping(value = "/national/message-type")
@CommonsLog
public class MessageTypeRest {

	@Autowired
	@Qualifier("comClassRepositoryNazionale")
	private ComClassRepository comClassRepository;

	/**
	 * Servizio per l'estrazione dei valori possibili per le tipologie di
	 * comunicazione, che fanno riferimento al <code>messageType</code> delle
	 * comunicazioni stesse. Nella implementazione attuale i valori vengono imposti
	 * dal servizio piuttosto che realmente estratti da risorse del database.
	 * 
	 * @return una lista/array di oggetti del tipo <code>MessageTypeDto</code>
	 */
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll() {
		try {
			log.info("Estrazione di tutti i communication message type (mock version)...");
			List<MessageTypeDto> retList = new LinkedList<>();
			/*MessageTypeDto item1 = new MessageTypeDto();
			item1.setValue("1");
			item1.setDescription("ANNULLAMENTO");
			retList.add(item1);
			MessageTypeDto item2 = new MessageTypeDto();
			item2.setValue("2");
			item2.setDescription("ASSENZA DI DATI DA COMUNICARE");
			retList.add(item2);
			MessageTypeDto item3 = new MessageTypeDto();
			item3.setValue("3");
			item3.setDescription("CORRETTIVA");
			retList.add(item3);
			MessageTypeDto item4 = new MessageTypeDto();
			item4.setValue("4");
			item4.setDescription("INTEGRATIVA");
			retList.add(item4);
			MessageTypeDto item5 = new MessageTypeDto();
			item5.setValue("5");
			item5.setDescription("NUOVI DATI FUORI DAI TERMINI");
			retList.add(item5);
			MessageTypeDto item6 = new MessageTypeDto();
			item6.setValue("6");
			item6.setDescription("NUOVI DATI NEI TERMINI");
			retList.add(item6);*/

			List<ComClassEntity> list = comClassRepository.findAll();
			for (ComClassEntity comClassEntity : list) {
				MessageTypeDto item1 = new MessageTypeDto();
				item1.setValue(String.valueOf(comClassEntity.getComClassId()));
				item1.setDescription(comClassEntity.getComClassDescription());
				retList.add(item1);
			}

			return ResponseEntity.ok(retList);
		} catch (Exception e) {
			String msg = "Errore durante estrazione dei communication message type (mock version)";
			log.error(msg, e);
			return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
