package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CommunicationId implements Serializable {
	
    private static final long serialVersionUID = -5920653066995054287L;
    
    @Size (max = 57)
    @NotNull
    @Column (name = "COM_UUID", nullable = false, length = 57)
    private String comUuid;

    @NotNull
    @Column (name = "DATA", nullable = false)
    private Instant data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommunicationId entity = (CommunicationId) o;
        return Objects.equals(this.data, entity.data) &&
                Objects.equals(this.comUuid, entity.comUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, comUuid);
    }

}