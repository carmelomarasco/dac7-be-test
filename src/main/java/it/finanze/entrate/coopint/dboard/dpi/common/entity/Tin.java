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
@Table (name = "TIN")
public class Tin {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "TIN_SEQ")
    @SequenceGenerator (name = "TIN_SEQ", sequenceName = "TIN_SEQ", allocationSize = 1)
    @Column (name = "TIN_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 4000)
    private String value;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "ISSUED_BY_ID")
    private Country issuedBy;

    @Column (name = "UNKNOWN")
    private Boolean unknown;

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