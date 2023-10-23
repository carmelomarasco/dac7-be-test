package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;
import org.hibernate.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.*;
import java.time.*;
import java.util.*;

@Getter
@Setter
@Embeddable
public class MessageOutId implements Serializable {
    private static final long serialVersionUID = 5931457781879380972L;
    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @NotNull
    @Column (name = "\"DATA\"", nullable = false)
    private Instant data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MessageOutId entity = (MessageOutId) o;
        return Objects.equals(this.msgUuid, entity.msgUuid) &&
                Objects.equals(this.data, entity.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgUuid, data);
    }

}