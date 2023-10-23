package it.finanze.entrate.coopint.dboard.dpi.res.command.entity;

import lombok.*;
import org.hibernate.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.*;
import java.time.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class StatusMessageId implements Serializable {
    private static final long serialVersionUID = -8685110944192216930L;
    @Size (max = 57)
    @NotNull
    @Column (name = "SM_UUID", nullable = false, length = 57)
    private String smUuid;

    @NotNull
    @Column (name = "SM_CHANGE_DATE", nullable = false)
    private Instant smChangeDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StatusMessageId entity = (StatusMessageId) o;
        return Objects.equals(this.smUuid, entity.smUuid) &&
                Objects.equals(this.smChangeDate, entity.smChangeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(smUuid, smChangeDate);
    }

}