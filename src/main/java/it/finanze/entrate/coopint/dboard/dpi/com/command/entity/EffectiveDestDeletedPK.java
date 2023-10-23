package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the EFFECTIVE_DEST_DELETED database table.
 *
 */
@Embeddable
public class EffectiveDestDeletedPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name="COM_UUID")
    private String comUuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="\"DATA\"")
    private java.util.Date data;

    @Column(name="COUNTRY_ID")
    private long countryId;

    public EffectiveDestDeletedPK() {
    }
    public String getComUuid() {
        return this.comUuid;
    }
    public void setComUuid(String comUuid) {
        this.comUuid = comUuid;
    }
    public java.util.Date getData() {
        return this.data;
    }
    public void setData(java.util.Date data) {
        this.data = data;
    }
    public long getCountryId() {
        return this.countryId;
    }
    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EffectiveDestDeletedPK)) {
            return false;
        }
        EffectiveDestDeletedPK castOther = (EffectiveDestDeletedPK)other;
        return
                this.comUuid.equals(castOther.comUuid)
                        && this.data.equals(castOther.data)
                        && (this.countryId == castOther.countryId);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.comUuid.hashCode();
        hash = hash * prime + this.data.hashCode();
        hash = hash * prime + ((int) (this.countryId ^ (this.countryId >>> 32)));

        return hash;
    }
}