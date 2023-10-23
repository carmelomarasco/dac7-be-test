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
public class CommunicationPartialId implements Serializable {
    private static final long serialVersionUID = 483581349289008376L;
    @NotNull
    @Column (name = "FISCAL_YEAR", nullable = false)
    private Short fiscalYear;

    @Size (max = 2)
    @NotNull
    @Column (name = "COUNTRY", nullable = false, length = 2)
    private String country;

    @Size (max = 57)
    @NotNull
    @Column (name = "COM_UUID", nullable = false, length = 57)
    private String comUuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommunicationPartialId entity = (CommunicationPartialId) o;
        return Objects.equals(this.country, entity.country) &&
                Objects.equals(this.comUuid, entity.comUuid) &&
                Objects.equals(this.fiscalYear, entity.fiscalYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, comUuid, fiscalYear);
    }

}