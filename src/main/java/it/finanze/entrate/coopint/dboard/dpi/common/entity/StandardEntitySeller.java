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
@Table (name = "STANDARD_ENTITY_SELLER")
public class StandardEntitySeller {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "STANDARD_ENTITY_SELLER_SEQ")
    @SequenceGenerator (name = "STANDARD_ENTITY_SELLER_SEQ", sequenceName = "STANDARD_ENTITY_SELLER_SEQ", allocationSize = 1)
    @Column (name = "STANDARD_ENTITY_SELLER_OID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ENT_SELLER_ID", nullable = false)
    private Organisation entSeller;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "PERMANENT_ESTABLISHMENTS_ID")
    private PermanentEstablishment permanentEstablishments;

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