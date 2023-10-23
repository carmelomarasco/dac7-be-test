package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The persistent class for the POTENTIAL_DEST_DELETED database table.
 *
 */
@Entity
@Table(name="POTENTIAL_DEST_DELETED")
public class PotentialDestDeleted implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PotentialDestDeletedPK id;

    public PotentialDestDeleted() {
    }

    public PotentialDestDeletedPK getId() {
        return this.id;
    }

    public void setId(PotentialDestDeletedPK id) {
        this.id = id;
    }

}