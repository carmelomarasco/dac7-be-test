package it.finanze.entrate.coopint.dboard.dpi.common.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ")
    @SequenceGenerator (name = "MESSAGE_SEQ", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
    @Column (name = "MESSAGE_ID", nullable = false)
    private Long id;

    @Size (max = 200)
    @Column (name = "SENDING_ENTITY_IN", length = 200)
    private String sendingEntityIn;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TRANSMITTING_COUNTRY_ID", nullable = false)
    private Country transmittingCountry;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "RECEIVING_COUNTRY_ID", nullable = false)
    private Country receivingCountry;

    @Size (max = 4000)
    @NotNull
    @Column (name = "MESSAGE_TYPE", nullable = false, length = 4000)
    private String messageType;

    @Size (max = 4000)
    @Column (name = "WARNING", length = 4000)
    private String warning;

    @Size (max = 4000)
    @Column (name = "CONTACT", length = 4000)
    private String contact;

    @Size (max = 1000)
    @Column (name = "VERSION", length = 1000)
    private String version;

    @Size (max = 170)
    @NotNull
    @Column (name = "MESSAGE_REF_ID", nullable = false, length = 170)
    private String messageRefId;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MESSAGE_TYPE_INDIC_ID", nullable = false)
    private MessageTypeIndic messageTypeIndic;

    @NotNull
    @Column (name = "REPORTING_PERIOD", nullable = false)
    private LocalDate reportingPeriod;

    @NotNull
    @Column (name = "TIMESTAMP", nullable = false)
    private LocalDate timestamp;

    @Size (max = 100)
    @NotNull
    @Column (name = "CREATED_BY", nullable = false, length = 100)
    private String createdBy;

    @NotNull
    @Column (name = "CREATED_ON", nullable = false)
    private Instant createdOn;

    @Size (max = 100)
    @Column (name = "MODIFIED_BY", length = 100)
    private String modifiedBy;

    @Column (name = "MODIFIED_ON")
    private Instant modifiedOn;

}