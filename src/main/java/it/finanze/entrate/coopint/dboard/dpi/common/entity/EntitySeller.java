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
@Table (name = "ENTITY_SELLER")
public class EntitySeller {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ENTITY_SELLER_SEQ")
    @SequenceGenerator (name = "ENTITY_SELLER_SEQ", sequenceName = "ENTITY_SELLER_SEQ", allocationSize = 1)
    @Column (name = "ENTITY_SELLER_ID", nullable = false)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY, optional = true)
    @JoinColumn (name = "GVS_ID", nullable = true)
    private Gvs gvs;

    @ManyToOne (fetch = FetchType.LAZY, optional = true)
    @JoinColumn (name = "STANDARD_ID", nullable = true)
    private StandardEntitySeller standard;

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