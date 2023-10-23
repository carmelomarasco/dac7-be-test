package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "SM_MESSAGE", schema = "DBOARD_DPI_NATIONAL")
public class SmMessage {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SM_MESSAGE_SEQ")
    @SequenceGenerator (name = "SM_MESSAGE_SEQ", sequenceName = "SM_MESSAGE_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "DAC7_STATUS_MESSAGE_ID", nullable = false)
    private Long id;

    @Size (max = 10)
    @NotNull
    @Column (name = "VERSION", nullable = false, length = 10)
    private String version;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MESSAGE_ID", nullable = false)
    private SmMessageSpec message;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "DSM_BODY_ID", nullable = false)
    private SmStatus dsmBody;

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