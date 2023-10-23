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
@Table (name = "PROPERTY_LISTING")
public class PropertyListing {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PROPERTY_LISTING_SEQ")
    @SequenceGenerator (name = "PROPERTY_LISTING_SEQ", sequenceName = "PROPERTY_LISTING_SEQ", allocationSize = 1)
    @Column (name = "PROPERTY_LISTING_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ADDRESS_ID", nullable = false)
    private Address address;

    @Size (max = 200)
    @Column (name = "LAND_REGISTRATION_NUMBER", length = 200)
    private String landRegistrationNumber;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CONSIDERATION_ID", nullable = false)
    private Consideration consideration;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "NUMBER_OF_ACTIVITIES_ID", nullable = false)
    private NumberOfActivity numberOfActivities;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FEES_ID", nullable = false)
    private Fees fees;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TAXES_ID", nullable = false)
    private Taxes taxes;

    @Size (max = 4000)
    @Column (name = "PROPERTY_TYPE", length = 4000)
    private String propertyType;

    @Size (max = 200)
    @Column (name = "OTHER_PROPERTY_TYPE", length = 200)
    private String otherPropertyType;

    @Column (name = "RENTED_DAYS")
    private Short rentedDays;

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