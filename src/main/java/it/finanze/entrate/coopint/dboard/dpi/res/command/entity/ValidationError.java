package it.finanze.entrate.coopint.dboard.dpi.res.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "VALIDATION_ERRORS", schema = "DBOARD_DPI_R")
public class ValidationError {
    @Id
    @Size (max = 57)
    @Column (name = "VALIDATION_ERROR_UUID", nullable = false, length = 57)
    private String validationErrorUuid;

    @Size (max = 57)
    @NotNull
    @Column (name = "SM_UUID", nullable = false, length = 57)
    private String smUuid;

    @NotNull
    @Column (name = "IS_FILE_ERROR", nullable = false)
    @Builder.Default
    private Boolean isFileError = false;

    @NotNull
    @Column (name = "IS_BLOCK", nullable = false)
    @Builder.Default
    private Boolean isBlock = false;

    @NotNull
    @Column (name = "ERROR_CODE", nullable = false)
    private Integer errorCode;

    @Size (max = 4000)
    @NotNull
    @Column (name = "ERROR_DESCRIPTION", nullable = false, length = 4000)
    private String errorDescription;

    @Size (max = 4000)
    @Column (name = "DOCREFID_IN_ERROR", length = 4000)
    private String docrefidInError;

    @Size (max = 4000)
    @Column (name = "DOCREFID_XPATH", length = 4000)
    private String docrefidXpath;

}