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
@Table (name = "SM_FILE_ERROR", schema = "DBOARD_DPI_NATIONAL")
public class SmFileError {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SM_FILE_ERROR_SEQ")
    @SequenceGenerator (name = "SM_FILE_ERROR_SEQ", sequenceName = "SM_FILE_ERROR_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "FILE_ERROR_ID", nullable = false)
    private Long id;

    @Size (max = 10)
    @NotNull
    @Column (name = "CODE", nullable = false, length = 10)
    private String code;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "DETAILS_ID")
    private SmErrorDetail details;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "VALIDATION_ERRORS_ID")
    private SmValidationError validationErrors;

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