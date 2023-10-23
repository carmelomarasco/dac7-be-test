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
@Table (name = "ORGANISATION_TIN")
public class OrganisationTin {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ORGANISATION_TIN_SEQ")
    @SequenceGenerator (name = "ORGANISATION_TIN_SEQ", sequenceName = "ORGANISATION_TIN_SEQ", allocationSize = 1)
    @Column (name = "ORGANISATION_TIN_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ORGANISATION_ID", nullable = false)
    private Organisation organisation;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TIN_ID", nullable = false)
    private Tin tin;

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