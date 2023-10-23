package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.BuildingStatus;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.NewDataBuildingCountryId;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.NewDataBuildingReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "NEW_DATA_BUILDING_COUNTRIES", schema = "DBOARD_DPI_NR")
public class NewDataBuildingCountryLazy {
    @EmbeddedId
    private NewDataBuildingCountryId id;

    @MapsId ("buildingReportId")
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "BUILDING_REPORT_ID", nullable = false)
    private NewDataBuildingReport buildingReport;

    @MapsId ("countryId")
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "COUNTRY_ID", nullable = false)
    private Country country;

    @Column (name = "BUILD_START_DATE")
    private Instant buildStartDate;

    @Column (name = "BUILD_END_DATE")
    private Instant buildEndDate;

    @NotNull
    @Column (name = "ACTUAL_RS_COUNT", nullable = false)
    private Integer actualRsCount;

    @NotNull
    @Column (name = "ACTUAL_IP_COUNT", nullable = false)
    private Integer actualIpCount;

    @NotNull
    @Column (name = "TOTAL_REPORTABLE_SELLERS", nullable = false)
    private Integer totalReportableSellers;

    @NotNull
    @Column (name = "TOTAL_IMMOVABLE_PROPERTIES", nullable = false)
    private Integer totalImmovableProperties;

    @NotNull
    @Column (name = "PROGRESS_PERCENTUAL", nullable = false)
    private Short progressPercentual;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "BUILDING_STATUS_ID", nullable = false)
    private BuildingStatus buildingStatus;

}