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
@Table (name = "OTHER_RPO_TIN")
public class OtherRpoTin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OTHER_RPO_TIN_SEQ")
    @SequenceGenerator (name = "OTHER_RPO_TIN_SEQ", sequenceName = "OTHER_RPO_TIN_SEQ", allocationSize = 1)
    @Column (name = "OTHER_RPO_TIN_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "OTHER_RPO_ID", nullable = false)
    private OtherRpo otherRpo;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TIN_ID", nullable = false)
    private Tin tin;

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