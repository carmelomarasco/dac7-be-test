package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "NEW_DATA_BUILDING_REPORT", schema = "DBOARD_DPI_NR")
public class NewDataBuildingReportLazy {
    @Id
    @Size (max = 57)
    @Column (name = "BUILDING_REPORT_ID", nullable = false, length = 57)
    private String buildingReportId;

    @NotNull
    @Column (name = "FISCAL_YEAR", nullable = false)
    private Short fiscalYear;

    @NotNull
    @Column (name = "BUILDING_STATUS_ID", nullable = false)
    private Short buildingStatusId;

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

}