package it.finanze.entrate.coopint.dboard.dpi.common.entity;

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
@Table (name = "PLATFORM_OPERATOR")
public class PlatformOperator {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PLATFORM_OPERATOR_SEQ")
    @SequenceGenerator (name = "PLATFORM_OPERATOR_SEQ", sequenceName = "PLATFORM_OPERATOR_SEQ", allocationSize = 1)
    @Column (name = "PLATFORM_OPERATOR_ID", nullable = false)
    private Long id;

    @Size (max = 2000)
    @NotNull
    @Column (name = "DOC_TYPE_INDIC", nullable = false, length = 2000)
    private String docTypeIndic;

    @Size (max = 200)
    @NotNull
    @Column (name = "DOC_REF_ID", nullable = false, length = 200)
    private String docRefId;

    @Size (max = 200)
    @Column (name = "CORR_DOC_REF_ID", length = 200)
    private String corrDocRefId;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ORGANISATION_PARTY_ID", nullable = false)
    private Organisation organisationParty;

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