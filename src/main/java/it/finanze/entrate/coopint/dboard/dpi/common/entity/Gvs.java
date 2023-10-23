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
@Table (name = "GVS")
public class Gvs {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "GVS_SEQ")
    @SequenceGenerator (name = "GVS_SEQ", sequenceName = "GVS_SEQ", allocationSize = 1)
    @Column (name = "GVS_ID", nullable = false)
    private Long id;

    @Size (max = 200)
    @NotNull
    @Column (name = "NAME_GVS", nullable = false, length = 200)
    private String nameGvs;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "JURISDICTION_GVS", nullable = false)
    private Country jurisdictionGvs;

    @Size (max = 200)
    @NotNull
    @Column (name = "REFERENCE_GVS", nullable = false, length = 200)
    private String referenceGvs;

    @Size (max = 200)
    @Column (name = "OTHER_TIN_GVS", length = 200)
    private String otherTinGvs;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "FINANCIAL_IDENTIFIER")
    private FinancialIdentifier financialIdentifier;

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