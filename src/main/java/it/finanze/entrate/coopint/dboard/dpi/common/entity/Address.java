package it.finanze.entrate.coopint.dboard.dpi.common.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
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
@Table (name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
    @SequenceGenerator (name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", allocationSize = 1)
    @Column (name = "ADDRESS_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "COUNTRY_CODE_ID", nullable = false)
    private Country countryCode;

    @Size (max = 4000)
    @Column (name = "LEGAL_ADDRESS_TYPE", length = 4000)
    private String legalAddressType;

    @Size (max = 4000)
    @Column (name = "ADDRESS_FREE", length = 4000)
    private String addressFree;

    @Column (name = "ADDRESS_FIX_ID")
    private Long addressFixId;

    @Size (max = 4000)
    @Column (name = "STREET", length = 4000)
    private String street;

    @Size (max = 4000)
    @Column (name = "BUILDING_IDENTIFIER", length = 4000)
    private String buildingIdentifier;

    @Size (max = 4000)
    @Column (name = "SUITE_IDENTIFIER", length = 4000)
    private String suiteIdentifier;

    @Size (max = 4000)
    @Column (name = "FLOOR_IDENTIFIER", length = 4000)
    private String floorIdentifier;

    @Size (max = 4000)
    @Column (name = "DISTRICT_NAME", length = 4000)
    private String districtName;

    @Size (max = 4000)
    @Column (name = "POB", length = 4000)
    private String pob;

    @Size (max = 4000)
    @Column (name = "POST_CODE", length = 4000)
    private String postCode;

    @Size (max = 4000)
    @Column (name = "CITY", length = 4000)
    private String city;

    @Size (max = 4000)
    @Column (name = "COUNTRY_SUBENTITY", length = 4000)
    private String countrySubentity;

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