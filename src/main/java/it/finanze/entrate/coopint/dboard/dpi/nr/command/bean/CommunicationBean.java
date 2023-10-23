package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.Communication;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationBean {

    private String uuid;
    private String msgUuid;
    private String msgRef;
    private String totAccForCountry;
    private String poTin;
    private String poName;

    public static CommunicationBean getFromEntity(Communication input) {
        return CommunicationBean.builder()
                .uuid(input.getId().getComUuid())
                .msgUuid(input.getId().getMsgUuid())
                .msgRef(input.getMessageRef())
                .totAccForCountry(input.getReportableSellersCount().toString())
                .poTin(input.getPlatformOperatorTin())
                .poName(input.getPlatformOperatorName())
                .build();
    }

    public static List<CommunicationBean> getListFromEntity(List<Communication> listInput) {
        List<CommunicationBean> listResult = new ArrayList<>();

        listInput.forEach(data -> listResult.add(CommunicationBean.getFromEntity(data)));

        return listResult;
    }
}
