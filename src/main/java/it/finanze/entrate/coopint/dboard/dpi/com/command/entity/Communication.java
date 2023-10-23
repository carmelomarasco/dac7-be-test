package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMUNICATION", schema = "DBOARD_DPI_NATIONAL")
public class Communication implements Cloneable {
	
    @EmbeddedId
    private CommunicationId id;

    // @Basic
    // @Column(name = "FISCAL_YEAR")
    // private long fiscalYear;

    @Size(max = 17)
    @Column(name = "PROTOCOL", length = 17)
    private String protocol;

    @Size(max = 4000)
    @Column(name = "SENDER_CF", length = 4000)
    private String senderCf;

    @Column(name = "SENT_DATE")
    private Instant sentDate;

    @Size(max = 4000)
    @Column(name = "RECEIPT", length = 4000)
    private String receipt;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "STATUS_ID", nullable = false)
    private CommunicationStatus status;

    @Size(max = 4000)
    @Column(name = "SENDING_ENTITY_IN", length = 4000)
    private String sendingEntityIn;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TRANSMITTING_COUNTRY_ID", nullable = false)
    private Country transmittingCountry;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RECEIVING_COUNTRY_ID", nullable = false)
    private Country receivingCountry;

    @Size(max = 4000)
    @Column(name = "MESSAGE_TYPE", length = 4000)
    private String messageType;

    @Size(max = 4000)
    @Column(name = "MESSAGE_REF_ID", length = 4000)
    private String messageRefId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MESSAGE_TYPE_INDIC_ID", nullable = false)
    private MessageTypeIndic messageTypeIndic;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "COM_CLASS_ID", nullable = false)
    private ComClassEntity comClass;

    @Column(name = "REPORTING_PERIOD")
    private LocalDate reportingPeriod;

    @Column(name = "MESSAGE_DATE")
    private Instant messageDate;

    @Size(max = 4000)
    @Column(name = "PLATFORM_OPERATOR_NAME", length = 4000)
    private String platformOperatorName;

    @Column(name = "REPORTABLE_SELLERS_COUNT")
    private Long reportableSellersCount;

    @Size(max = 100)
    @NotNull
    @Column(name = "CREATED_BY", nullable = false, length = 100)
    private String createdBy;

    @NotNull
    @Column(name = "CREATED_ON", nullable = false)
    private Instant createdOn;

    @Size(max = 100)
    @Column(name = "MODIFIED_BY", length = 100)
    private String modifiedBy;

    @Column(name = "MODIFIED_ON")
    private Instant modifiedOn;

    @Column (name = "IMMOVABLE_PROPERTIES_COUNT")
    private Long immovablePropertiesCount;

    // TODO Non esiste un link su EffectiveDestEntity
    @OneToMany(mappedBy = "communication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<EffectiveDestEntity> effectiveDests;
    
    @OneToMany(mappedBy = "communication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<PotentialDestEntity> potentialDests;
    
    @ManyToOne
    @JoinColumn(name = "MESSAGE_TYPE_INDIC_ID", referencedColumnName = "MESSAGE_TYPE_INDIC_ID", nullable = false, insertable = false, updatable = false)
    private MessageTypeIndic messageTypeIndicByMessageTypeIndicId;

    public Communication clone() throws CloneNotSupportedException {
        return (Communication) super.clone();
    }


}