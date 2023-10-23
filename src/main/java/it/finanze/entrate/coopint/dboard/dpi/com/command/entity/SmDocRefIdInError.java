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
@Table (name = "SM_DOC_REF_ID_IN_ERROR", schema = "DBOARD_DPI_NATIONAL")
public class SmDocRefIdInError {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SM_DOC_REF_ID_IN_ERROR_SEQ")
    @SequenceGenerator (name = "SM_DOC_REF_ID_IN_ERROR_SEQ", sequenceName = "SM_DOC_REF_ID_IN_ERROR_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "DOC_REF_ID_IN_ERROR", nullable = false)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "RECORD_ERROR_ID")
    private SmRecordError recordError;

    @NotNull
    @Column (name = "DOC_REF_ID", nullable = false)
    private Long docRefId;

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