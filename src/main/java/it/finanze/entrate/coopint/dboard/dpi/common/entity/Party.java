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
@Table (name = "PARTY")
public class Party {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PARTY_SEQ")
    @SequenceGenerator (name = "PARTY_SEQ", sequenceName = "PARTY_SEQ", allocationSize = 1)
    @Column (name = "PARTY_ID", nullable = false)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "ENTITY_SELLER_ID")
    private EntitySeller entitySeller;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "INDIVIDUAL_SELLER_ID")
    private IndividualSeller individualSeller;

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