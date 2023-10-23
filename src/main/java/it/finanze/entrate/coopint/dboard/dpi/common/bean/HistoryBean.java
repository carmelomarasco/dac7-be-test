package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryBean {

    private String comUuidToInsert;
    private String protocol;
    private String protocolToUpdate;
    private String intention;
    private String comRepOrCanc;

    public static HistoryBean buildBean(Communication entity, String intention) {
        return HistoryBean.builder()
                .comUuidToInsert(entity.getId().getComUuid())
                .protocol(entity.getProtocol())
                .intention(intention)
                .build();
    }
}
