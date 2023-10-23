package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.LanguageCode;
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
@Table (name = "SM_ERROR_DETAIL", schema = "DBOARD_DPI_NATIONAL")
public class SmErrorDetail {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SM_ERROR_DETAIL_SEQ")
    @SequenceGenerator (name = "SM_ERROR_DETAIL_SEQ", sequenceName = "SM_ERROR_DETAIL_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "ERROR_DETAIL_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "LANGUAGE_ID", nullable = false)
    private LanguageCode language;

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