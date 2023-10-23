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
@Table (name = "REPORTABLE_SELLER")
public class ReportableSeller {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "REPORTABLE_SELLER_SEQ")
    @SequenceGenerator (name = "REPORTABLE_SELLER_SEQ", sequenceName = "REPORTABLE_SELLER_SEQ", allocationSize = 1)
    @Column (name = "REPORTABLE_SELLER_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "PARTY_ID", nullable = false)
    private Party party;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "RELEVANT_ACTIVITIES_ID", nullable = false)
    private RelevantActivity relevantActivities;

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

    @Size (max = 1000)
    @NotNull
    @Column (name = "DOC_TYPE_INDIC", nullable = false, length = 1000)
    private String docTypeIndic;

    @Size (max = 200)
    @NotNull
    @Column (name = "DOC_REF_ID", nullable = false, length = 200)
    private String docRefId;

    @Size (max = 200)
    @Column (name = "CORR_DOC_REF_ID", length = 200)
    private String corrDocRefId;

}