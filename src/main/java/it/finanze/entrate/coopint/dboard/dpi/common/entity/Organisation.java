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
@Table (name = "ORGANISATION")
public class Organisation {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ORGANISATION_SEQ")
    @SequenceGenerator (name = "ORGANISATION_SEQ", sequenceName = "ORGANISATION_SEQ", allocationSize = 1)
    @Column (name = "ORGANISATION_ID", nullable = false)
    private Long id;

    @Size (max = 200)
    @Column (name = "VAT", length = 200)
    private String vat;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "NEXUS_ID")
    private Nexus nexus;

    @Column (name = "ASSUMED_REPORTING")
    private Boolean assumedReporting;

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