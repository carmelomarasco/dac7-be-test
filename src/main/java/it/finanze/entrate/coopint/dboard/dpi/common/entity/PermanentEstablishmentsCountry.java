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
@Table (name = "PERM_ESTAB_COUNTRY")
public class PermanentEstablishmentsCountry {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PERM_ESTAB_COUNTRY_SEQ")
    @SequenceGenerator (name = "PERM_ESTAB_COUNTRY_SEQ", sequenceName = "PERM_ESTAB_COUNTRY_SEQ", allocationSize = 1)
    @Column (name = "PERM_ESTAB_COUNTRY_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "PERMANENT_ESTABLISHMENTS_ID", nullable = false)
    private PermanentEstablishment permanentEstablishments;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "COUNTRY_ID", nullable = false)
    private Country country;

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