package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "POTENTIAL_DEST", schema = "DBOARD_DPI_NATIONAL", catalog = "")
@IdClass(PotentialDestEntityPK.class)
public class PotentialDestEntity {
    @Id
    @Column(name = "COM_UUID")
    private String comUuid;
    @Id
    @Column(name = "DATA")
    private Date data;
    @Id
    @Column(name = "COUNTRY_ID")
    private long countryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
            {@JoinColumn(name = "COM_UUID", referencedColumnName = "COM_UUID", nullable = false, insertable = false, updatable = false),
                    @JoinColumn(name = "DATA", referencedColumnName = "DATA", nullable = false, insertable = false, updatable = false)})
    private Communication communication;
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", nullable = false, insertable = false, updatable = false)
    private Country countryByCountryId;


    public static List<PotentialDestEntity> buildListEntities(List<Country> countriesList, String comUuid, Date data) {
        log.info("Number of Potential dests: " + countriesList.size());
        return countriesList.stream()
                .map(ce -> PotentialDestEntity.builder().countryId(ce.getId()).data(data).comUuid(comUuid).build())
                .collect(Collectors.toList());
    }
}
