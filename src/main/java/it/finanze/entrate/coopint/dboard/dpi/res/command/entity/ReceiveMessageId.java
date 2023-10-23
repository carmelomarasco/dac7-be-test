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
public class ReceiveMessageId implements Serializable {
    private static final long serialVersionUID = 2773866061768232307L;
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
        ReceiveMessageId entity = (ReceiveMessageId) o;
        return Objects.equals(this.msgUuid, entity.msgUuid) &&
                Objects.equals(this.data, entity.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgUuid, data);
    }

}