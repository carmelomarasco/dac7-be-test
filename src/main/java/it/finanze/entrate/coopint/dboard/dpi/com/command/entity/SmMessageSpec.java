package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
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
@Table (name = "SM_MESSAGE_SPEC", schema = "DBOARD_DPI_NATIONAL")
public class SmMessageSpec {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SM_MESSAGE_SPEC_SEQ")
    @SequenceGenerator (name = "SM_MESSAGE_SPEC_SEQ", sequenceName = "SM_MESSAGE_SPEC_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "MESSAGE_STATUS_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TRANSMITTING_COUNTRY_ID", nullable = false)
    private Country transmittingCountry;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "RECEIVING_COUNTRY_ID", nullable = false)
    private Country receivingCountry;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MESSAGE_TYPE_ID", nullable = false)
    private MessageTypeIndic messageType;

    @Size (max = 4000)
    @Column (name = "WARNING", length = 4000)
    private String warning;

    @Size (max = 4000)
    @Column (name = "CONTACT", length = 4000)
    private String contact;

    @Size (max = 170)
    @NotNull
    @Column (name = "MESSAGE_REF_ID", nullable = false, length = 170)
    private String messageRefId;

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