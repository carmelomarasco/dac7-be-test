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
@Table (name = "RELEVANT_ACTIVITIES")
public class RelevantActivity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "RELEVANT_ACTIVITIES_SEQ")
    @SequenceGenerator (name = "RELEVANT_ACTIVITIES_SEQ", sequenceName = "RELEVANT_ACTIVITIES_SEQ", allocationSize = 1)
    @Column (name = "RELEVANT_ACTIVITIES_ID", nullable = false)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "IMMOVABLE_PROPERTY_ID")
    private ImmovableProperty immovableProperty;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "PERSONAL_SERVICES_ID")
    private OtherActivity personalServices;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "SALE_OF_GOODS_ID")
    private OtherActivity saleOfGoods;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "TRANSPORTATION_RENTAL_ID")
    private OtherActivity transportationRental;

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