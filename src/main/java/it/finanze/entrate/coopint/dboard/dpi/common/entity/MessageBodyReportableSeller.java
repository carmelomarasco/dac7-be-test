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
@Table (name = "MSG_BODY_REPORTABLE_SELLER")
public class MessageBodyReportableSeller {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "MSG_BODY_REPORTABLE_SELLER_SEQ")
    @SequenceGenerator (name = "MSG_BODY_REPORTABLE_SELLER_SEQ", sequenceName = "MSG_BODY_REPORTABLE_SELLER_SEQ", allocationSize = 1)
    @Column (name = "MSG_BODY_REPORTABLE_SELLER_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MESSAGE_BODY_ID", nullable = false)
    private MessageBody messageBody;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "REPORTABLE_SELLER_ID", nullable = false)
    private ReportableSeller reportableSeller;

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