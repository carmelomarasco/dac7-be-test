package it.finanze.entrate.coopint.dboard.dpi.common.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.OtherPlatformOperator;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.PlatformOperator;
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
@Table (name = "MESSAGE_BODY")
public class MessageBody {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "MESSAGE_BODY_SEQ")
    @SequenceGenerator (name = "MESSAGE_BODY_SEQ", sequenceName = "MESSAGE_BODY_SEQ", allocationSize = 1)
    @Column (name = "MESSAGE_BODY_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "PLATFORM_OPERATOR_ID", nullable = false)
    private PlatformOperator platformOperator;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "OTHER_PLATFORM_OPERATORS_ID")
    private OtherPlatformOperator otherPlatformOperators;

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