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
@Table (name = "ORGANISATION_IN")
public class OrganisationIn {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ORGANISATION_IN_SEQ")
    @SequenceGenerator (name = "ORGANISATION_IN_SEQ", sequenceName = "ORGANISATION_IN_SEQ", allocationSize = 1)
    @Column (name = "ORGANISATION_IN_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 4000)
    private String value;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "ISSUED_BY_ID")
    private Country issuedBy;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "IN_ID", nullable = false)
    private In in;

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