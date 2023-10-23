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
@Table (name = "STAND_IND_SEL_FIN_IDN")
public class StandardIndividualSellerFinancialIdentifier {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "STAND_IND_SEL_FIN_IDN_SEQ")
    @SequenceGenerator (name = "STAND_IND_SEL_FIN_IDN_SEQ", sequenceName = "STAND_IND_SEL_FIN_IDN_SEQ", allocationSize = 1)
    @Column (name = "STAND_IND_SEL_FIN_IDN_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "SELLER_STANDARD_INDIVIDUAL_SELLER_ID", nullable = false)
    private StandardIndividualSeller sellerStandardIndividualSeller;

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