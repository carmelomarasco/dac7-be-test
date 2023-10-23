package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.ToString;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class  DpiCommunicationPayload {

    @ToString.Exclude private String xmlGZipBase64; // payload xml gzipped and then base64ed
    @ToString.Exclude private String metadatiGZipBase64; // the receipt gzipped and then base64ed
    // Costruire l'evento in base allo status.
    private String status; // the status of the communication (ACCEPTED, REJECTED, ACCEPTED_NON_CONTRIBUTING)
    private String sendingFiscalCode; // the sender fiscal code
    private String sentDate; // the sent date
    private String intention; // the intention value of this communication see CommnunicationIntention enum
    private String messageRefId; // the messagerefid of this communication
    private String protocol; // the sid protocol of this communication
    private String protocolToUpdate; // the protocol whom intention is applied in case of deletion or replacement concat with |
    private String late;


}
