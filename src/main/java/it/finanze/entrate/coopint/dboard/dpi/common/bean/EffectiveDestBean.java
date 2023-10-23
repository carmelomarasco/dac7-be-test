package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import java.util.Date;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.EffectiveDestEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EffectiveDestBean {

    private String comUuid;
    private Date data;
    private long countryId;
    @Builder.Default
    private long reportableSellerCount = 0L;
    @Builder.Default
    private long immovablePropertyCount = 0L;
    private boolean built;
    private String countryCode;

    public EffectiveDestBean incrReportableSellerCount(){
        this.reportableSellerCount++;
        return this;
    }

    public EffectiveDestBean incrImmovablePropertyCountr(){
        this.immovablePropertyCount++;
        return this;
    }

    public static EffectiveDestBean fromEntityToDTO(EffectiveDestEntity ede) {
        return EffectiveDestBean.builder().
                comUuid(ede.getComUuid())
                .data(ede.getData())
                .countryId(ede.getCountryId())
                //.countryCode(ede.getCountryByCountryId().getValue()) //TODO
                .reportableSellerCount(ede.getReportableSellerCount())
                .immovablePropertyCount(ede.getImmovablePropertyCount())
                .build();
    }

}
