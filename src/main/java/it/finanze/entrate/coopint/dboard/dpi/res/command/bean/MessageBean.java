package it.finanze.entrate.coopint.dboard.dpi.res.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.ReceiveMessageLazy;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.StatusMessageLazy;
import it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration.StatusMessageStatusEnum;
import it.finanze.entrate.coopint.dboard.dpi.res.command.utils.DateUtil;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageBean {
    private String uuid;
    private String smUuid;
    private String msgRef;
    private String country;
    private String type;
    private String date;
    private String listPO;
    private Long numSeller;
    private String outcome;
    private String statusSM;
    private String notes;
    private String context;
    private String metadataId; // TODO rinominare ccn2id
    private boolean canDelete;
    private String smDate;

    public static MessageBean fromEntity(ReceiveMessageLazy input) {
        List<String> statusSm = new ArrayList<>();
        statusSm.add(StatusMessageStatusEnum.TRANSMITTED.getValue());
        statusSm.add(StatusMessageStatusEnum.DELIVERED.getValue());
        statusSm.add(StatusMessageStatusEnum.DOWNLOADED.getValue());
        statusSm.add(StatusMessageStatusEnum.INHIBITED.getValue());
        boolean canDelete = input.getStatusMessagesByMsgUuid().stream().noneMatch(sm -> statusSm.contains(sm.getSmStatus().getStatus()));

        StatusMessageLazy sm = input.getStatusMessagesByMsgUuid().stream().max( new Comparator<StatusMessageLazy>() {
            @Override
            public int compare(StatusMessageLazy o1, StatusMessageLazy o2) {
                if( o2 == null ){
                    return ( o1 == null) ? 0 : 1;
                }
                if( o1 == null ){
                    return -1;
                }
                return o1.getId().getSmChangeDate().compareTo(o2.getId().getSmChangeDate());
            }
        }).orElse(null);
        return MessageBean.builder()
                .uuid(input.getId().getMsgUuid())
                .smUuid(sm != null ? sm.getId().getSmUuid() : "")
                .msgRef(input.getMessageRef() != null ? input.getMessageRef() : "--")
                .country(input.getCountry().getValue())
                .type(input.getMessageTypeIndicId() != null ? input.getMessageTypeIndicId().toString() : "--")
                .date(DateUtil.convertDateToString(Date.from(input.getReceivedOn())))
                .listPO(input.getListPO())
                .numSeller(Long.valueOf(input.getTotalSellerNumber()))
                .outcome(input.getStatus().getId().toString())
                .statusSM(sm != null ? sm.getSmStatus().getId().toString() : "")
                .metadataId(input.getCcn2MsgMsgid())
                .canDelete(canDelete)
                .smDate(sm != null ? sm.getId().getSmChangeDate().toString() : "")
                .build();
    }

    public static List<MessageBean> listFromEntities(List<ReceiveMessageLazy> listInput) {
        return listInput.stream().sorted(Comparator.comparing(ReceiveMessageLazy::getReceivedOn).reversed()).map(MessageBean::fromEntity).collect(Collectors.toList());

    }

}
