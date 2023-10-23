package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.NameReportableSeller;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Organisation;
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
@Table (name = "ACCOUNT_HOLDER", schema = "DBOARD_DPI_NATIONAL")
public class AccountHolder {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_HOLDER_SEQ")
    @SequenceGenerator (name = "ACCOUNT_HOLDER_SEQ", sequenceName = "ACCOUNT_HOLDER_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "ACCOUNT_HOLDER_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "INDIVIDUAL_ID", nullable = false)
    private NameReportableSeller individual;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ORGANISATION_ID", nullable = false)
    private Organisation organisation;

    @Size (max = 4000)
    @Column (name = "ACCT_HOLDER_TYPE", length = 4000)
    private String acctHolderType;

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