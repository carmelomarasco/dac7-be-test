package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunicationBean {

    private String status; // the status of the communication (ACCEPTED, REJECTED, CANCELED, REPLACED, ACCEPTED_NON_CONTRIBUTING)
    private String sendingFiscalCode; // the sender fiscal code
    private String sentDate; // the sent date
    private String messageRefId; // the messagerefid of this communication
    private String protocol; // the sid protocol of this communication
    private String protocolToUpdate; // the protocol whom intention is applied in case of deletion or replacement
    private String comUuid;
    private String metadatiGZipBase64;
    private List<PotentialDestBean> potentialDests;
    private List<EffectiveDestBean> effectiveDests;
    private String late;

    public static CommunicationBean buildDTO(Communication e, DpiCommunicationPayload p) {

        List<PotentialDestBean> pds = e.getPotentialDests().stream().map(PotentialDestBean::fromEntityToDTO).collect(Collectors.toList());
        List<EffectiveDestBean> eds = e.getEffectiveDests().stream().map(EffectiveDestBean::fromEntityToDTO).collect(Collectors.toList());


        return CommunicationBean.builder()
                .status(e.getStatus().getStatus())
                .sendingFiscalCode(e.getSenderCf())
                .sentDate(DateUtil.fromTimestampToString(Timestamp.from(e.getSentDate())))
                .messageRefId(e.getMessageRefId())
                .protocol(e.getProtocol())
                .comUuid(e.getId().getComUuid())
                .metadatiGZipBase64(p.getMetadatiGZipBase64())
                .potentialDests(pds)
                .effectiveDests(eds)
                .late(p.getLate())
                .build();
    }


}
