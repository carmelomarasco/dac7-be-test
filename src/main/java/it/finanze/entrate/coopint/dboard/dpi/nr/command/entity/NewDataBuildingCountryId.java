package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;
import org.hibernate.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.*;
import java.util.*;

@Getter
@Setter
@Embeddable
public class NewDataBuildingCountryId implements Serializable {
    private static final long serialVersionUID = 1172299697376280252L;
    @Size (max = 57)
    @NotNull
    @Column (name = "BUILDING_REPORT_ID", nullable = false, length = 57)
    private String buildingReportId;

    @NotNull
    @Column (name = "COUNTRY_ID", nullable = false)
    private Short countryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NewDataBuildingCountryId entity = (NewDataBuildingCountryId) o;
        return Objects.equals(this.countryId, entity.countryId) &&
                Objects.equals(this.buildingReportId, entity.buildingReportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, buildingReportId);
    }

}