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
@Table (name = "SM_STATUS", schema = "DBOARD_DPI_NATIONAL")
public class SmStatus {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SM_STATUS_SEQ")
    @SequenceGenerator (name = "SM_STATUS_SEQ", sequenceName = "SM_STATUS_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "DAC7_MESSAGE_STATUS_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ORIGINAL_MESSAGE_ID", nullable = false)
    private OriginalMessage originalMessage;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "VALIDATION_ERRORS_ID", nullable = false)
    private SmValidationError validationErrors;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "VALIDATION_RESULT_ID", nullable = false)
    private SmValidationResult validationResult;

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