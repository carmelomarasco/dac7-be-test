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
public class CommunicationId implements Serializable {
    private static final long serialVersionUID = 4544220443297059471L;
    @Size (max = 57)
    @NotNull
    @Column (name = "COM_UUID", nullable = false, length = 57)
    private String comUuid;

    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommunicationId entity = (CommunicationId) o;
        return Objects.equals(this.msgUuid, entity.msgUuid) &&
                Objects.equals(this.comUuid, entity.comUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgUuid, comUuid);
    }

}