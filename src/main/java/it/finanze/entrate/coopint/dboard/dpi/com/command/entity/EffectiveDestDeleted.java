package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import java.io.Serializable;
import javax.persistence.*;
//import java.math.Long;
import java.sql.Timestamp;


/**
 * The persistent class for the EFFECTIVE_DEST_DELETED database table.
 *
 */
@Entity
@Table(name="EFFECTIVE_DEST_DELETED")
@NamedQuery(name="EffectiveDestDeleted.findAll", query="SELECT e FROM EffectiveDestDeleted e")
public class EffectiveDestDeleted implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EffectiveDestDeletedPK id;

    @Column(name="ACCOUNT_COUNT")
    private Long accountCount;

    @Column(name="AH_ORGANISATION_COUNT")
    private Long ahOrganisationCount;

    @Column(name="AH_PERSON_COUNT")
    private Long ahPersonCount;

    private Long built;

    @Column(name="CONTROLLING_PERSON_COUNT")
    private Long controllingPersonCount;

    @Column(name="LAST_BUILD_DATE")
    private Timestamp lastBuildDate;

    @Column(name="LAST_BUILD_MESSAGEREF")
    private String lastBuildMessageref;

    @Column(name="LAST_BUILD_MREF_STATUS")
    private String lastBuildMrefStatus;

    @Column(name="LAST_BUILD_SENT_DATE")
    private Timestamp lastBuildSentDate;

    @Column(name="TOTAL_SUBJECTS")
    private Long totalSubjects;

    public EffectiveDestDeleted() {
    }

    public EffectiveDestDeletedPK getId() {
        return this.id;
    }

    public void setId(EffectiveDestDeletedPK id) {
        this.id = id;
    }

    public Long getAccountCount() {
        return this.accountCount;
    }

    public void setAccountCount(Long accountCount) {
        this.accountCount = accountCount;
    }

    public Long getAhOrganisationCount() {
        return this.ahOrganisationCount;
    }

    public void setAhOrganisationCount(Long ahOrganisationCount) {
        this.ahOrganisationCount = ahOrganisationCount;
    }

    public Long getAhPersonCount() {
        return this.ahPersonCount;
    }

    public void setAhPersonCount(Long ahPersonCount) {
        this.ahPersonCount = ahPersonCount;
    }

    public Long getBuilt() {
        return this.built;
    }

    public void setBuilt(Long built) {
        this.built = built;
    }

    public Long getControllingPersonCount() {
        return this.controllingPersonCount;
    }

    public void setControllingPersonCount(Long controllingPersonCount) {
        this.controllingPersonCount = controllingPersonCount;
    }

    public Timestamp getLastBuildDate() {
        return this.lastBuildDate;
    }

    public void setLastBuildDate(Timestamp lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getLastBuildMessageref() {
        return this.lastBuildMessageref;
    }

    public void setLastBuildMessageref(String lastBuildMessageref) {
        this.lastBuildMessageref = lastBuildMessageref;
    }

    public String getLastBuildMrefStatus() {
        return this.lastBuildMrefStatus;
    }

    public void setLastBuildMrefStatus(String lastBuildMrefStatus) {
        this.lastBuildMrefStatus = lastBuildMrefStatus;
    }

    public Timestamp getLastBuildSentDate() {
        return this.lastBuildSentDate;
    }

    public void setLastBuildSentDate(Timestamp lastBuildSentDate) {
        this.lastBuildSentDate = lastBuildSentDate;
    }

    public Long getTotalSubjects() {
        return this.totalSubjects;
    }

    public void setTotalSubjects(Long totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

}