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
@Table (name = "PERMANENT_ESTABLISHMENTS")
public class PermanentEstablishment {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PERMANENT_ESTABLISHMENTS_SEQ")
    @SequenceGenerator (name = "PERMANENT_ESTABLISHMENTS_SEQ", sequenceName = "PERMANENT_ESTABLISHMENTS_SEQ", allocationSize = 1)
    @Column (name = "PERMANENT_ESTABLISHMENTS_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "PERMANENT_ESTABLISHMENT_ID", nullable = false)
    private MsCountryCode permanentEstablishment;

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