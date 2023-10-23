package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEffectiveBean {

    private List<EffectiveDestBean> effectiveDests;
    private String countryCode;
    private Long fiscalYear;
    private boolean expiredYear;

}
