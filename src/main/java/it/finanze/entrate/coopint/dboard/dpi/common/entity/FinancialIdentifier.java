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
@Table (name = "FINANCIAL_IDENTIFIER")
public class FinancialIdentifier {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "FINANCIAL_IDENTIFIER_SEQ")
    @SequenceGenerator (name = "FINANCIAL_IDENTIFIER_SEQ", sequenceName = "FINANCIAL_IDENTIFIER_SEQ", allocationSize = 1)
    @Column (name = "FINANCIAL_IDENTIFIER_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "IDENTIFIER", nullable = false)
    private Identifier identifier;

    @Size (max = 200)
    @Column (name = "ACCOUNT_HOLDER_NAME", length = 200)
    private String accountHolderName;

    @Size (max = 400)
    @Column (name = "OTHER_INFO", length = 400)
    private String otherInfo;

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