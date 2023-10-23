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
@Table (name = "IMV_PROP_PROP_LISTING")
public class ImmovablePropertyPropertyListing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMV_PROP_PROP_LISTING_SEQ")
    @SequenceGenerator (name = "IMV_PROP_PROP_LISTING_SEQ", sequenceName = "IMV_PROP_PROP_LISTING_SEQ", allocationSize = 1)
    @Column (name = "IMV_PROP_PROP_LISTING_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "IMMOVABLE_PROPERTY_ID", nullable = false)
    private ImmovableProperty immovableProperty;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "PROPERTY_LISTING_ID", nullable = false)
    private PropertyListing propertyListing;

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