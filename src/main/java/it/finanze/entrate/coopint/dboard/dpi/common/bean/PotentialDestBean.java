package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.PotentialDestEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PotentialDestBean {

    private String comUuid;
    private Date data;
    private long countryId;

    public static PotentialDestBean fromEntityToDTO(PotentialDestEntity pde) {
        return PotentialDestBean.builder().
                comUuid(pde.getComUuid())
                .data(pde.getData())
                .countryId(pde.getCountryId())
                .build();
    }
}
