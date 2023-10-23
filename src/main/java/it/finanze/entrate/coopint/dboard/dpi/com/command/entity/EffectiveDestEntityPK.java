package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EffectiveDestEntityPK implements Serializable {
    @Column(name = "COM_UUID")
    @Id
    private String comUuid;
    @Column(name = "DATA")
    @Id
    private Date data;
    @Column(name = "COUNTRY_ID")
    @Id
    private long countryId;

}
