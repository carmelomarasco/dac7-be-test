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
@Table (name = "STAND_ENTITY_SEL_OID_F_IDN")
public class StandardEntitySellerOidFinancialIdentifier {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "STAND_ENTITY_SEL_OID_F_IDN_SEQ")
    @SequenceGenerator (name = "STAND_ENTITY_SEL_OID_F_IDN_SEQ", sequenceName = "STAND_ENTITY_SEL_OID_F_IDN_SEQ", allocationSize = 1)
    @Column (name = "STAND_ENTITY_SEL_OID_F_IDN_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "STANDARD_ENTITY_SELLER_OID", nullable = false)
    private StandardEntitySeller standardEntitySellerOid;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FINANCIAL_IDENTIFIER_ID", nullable = false)
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