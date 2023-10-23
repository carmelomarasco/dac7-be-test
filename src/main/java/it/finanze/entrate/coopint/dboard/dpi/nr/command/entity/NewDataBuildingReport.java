package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "NEW_DATA_BUILDING_REPORT", schema = "DBOARD_DPI_NR")
public class NewDataBuildingReport {
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




    @OneToMany(mappedBy = "newDataBuildingReport")
    private List<MessageOut> messageOutList;

//    @OneToMany(mappedBy = "newDataBuildingReport",cascade = CascadeType.ALL)
//    private List<NewDataBuildingCountry> newDataBuildingCountryList;

//    @ManyToOne
//    @JoinColumn(name = "FISCAL_YEAR", referencedColumnName = "FISCAL_YEAR", nullable = false, insertable = false, updatable = false)
//    private QFiscalYearEntity qFiscalYearEntity;

    @ManyToOne
    @JoinColumn(name = "BUILDING_STATUS_ID", referencedColumnName = "BUILDING_STATUS_ID", nullable = false, insertable = false, updatable = false)
    private BuildingStatus buildingStatus;

}