package it.finanze.entrate.coopint.dboard.dpi.common.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "PERSON")
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PERSON_SEQ")
    @SequenceGenerator (name = "PERSON_SEQ", sequenceName = "PERSON_SEQ", allocationSize = 1)
    @Column (name = "PERSON_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @Column (name = "VAT", length = 4000)
    private String vat;

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

    @Column (name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Size (max = 4000)
    @Column (name = "BIRTH_CITY", length = 4000)
    private String birthCity;

    @Size (max = 4000)
    @Column (name = "BIRTH_CITY_SUBENTITY", length = 4000)
    private String birthCitySubentity;

    @ManyToOne (fetch = FetchType.LAZY, optional = true)
    @JoinColumn (name = "BIRTH_COUNTRY_ID", nullable = true)
    private Country birthCountry;

    @Size (max = 4000)
    @Column (name = "BIRTH_FORMER_COUNTRY_NAME", length = 4000)
    private String birthFormerCountryName;

}