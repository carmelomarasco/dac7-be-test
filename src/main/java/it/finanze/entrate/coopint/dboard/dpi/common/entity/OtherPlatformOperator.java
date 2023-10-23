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
@Table (name = "OTHER_PLATFORM_OPERATORS")
public class OtherPlatformOperator {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "OTHER_PLATFORM_OPERATORS_SEQ")
    @SequenceGenerator (name = "OTHER_PLATFORM_OPERATORS_SEQ", sequenceName = "OTHER_PLATFORM_OPERATORS_SEQ", allocationSize = 1)
    @Column (name = "OTHER_PLATFORM_OPERATORS_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ASSUMING_PLATFORM_OPERATOR_ID", nullable = false)
    private OtherRpo assumingPlatformOperator;

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