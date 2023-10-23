package it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationSearchDto implements Serializable {

	private static final long serialVersionUID = 145454545455L;

	public static DateTimeFormatter dateTimeItalianFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
			.withZone(ZoneId.systemDefault());
	
    /* Search communication
    Nell'url vengono passati:
        comUuid
        dataRicezioneDa // la messageDate
        identificativoComunicazione // il messageRefId
        protocollo
        tipologiaMes // "NUOVO MESSAGGIO", "ANNULLAMENTO" --> messageType
        tipologiaCom // "DPI401", "DPI402", "DPI403" --> messageTypeIndic
        esitoRicezione // ELIMINARE
        statoComunicazione
        destinazione // SCELGO TRA GLI STATI MA SOLO EUROPEI, FILTRO SULLE effectiveDest e sulle potentialDest della comunicazione

    cercare tutte le comunicazioni che corrispondono a questi valori di ricerca. Delle comunicazioni così trovate, restituire solo i campi nella lista sopra
 */

	private String messageDateFrom;
	private String messageDateTo;
	private String messageType;
	private String protocol;
	private String statoComunicazione;
	private String destinationCountryId;
	private String messageRefId;
	private String messageTypeIndic;
	private String cfUser;
    private String annoFiscale; 
    private String denominazione;

}