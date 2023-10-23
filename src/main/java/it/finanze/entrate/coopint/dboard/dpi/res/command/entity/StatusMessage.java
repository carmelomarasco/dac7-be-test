package it.finanze.entrate.coopint.dboard.dpi.res.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "STATUS_MESSAGE", schema = "DBOARD_DPI_R")
public class StatusMessage {
    @EmbeddedId
    private StatusMessageId id;

    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "SM_STATUS_ID", nullable = false)
    private StatusMessageStatus smStatus;

    @NotNull
    @Column (name = "BLOCKING_ERROR_PERCENTAGE", nullable = false)
    private Short blockingErrorPercentage;

    @Size (max = 255)
    @Column (name = "VALIDATION_RESULT")
    private String validationResult;

    @Size (max = 4000)
    @Column (name = "VALIDATED_BY", length = 4000)
    private String validatedBy;

    @Size (max = 4000)
    @Column (name = "SM_MESSAGE_REF", length = 4000)
    private String smMessageRef;

    @NotNull
    @Column (name = "SM_BLOB", nullable = false)
    private byte[] smBlob;

    @Size (max = 255)
    @NotNull
    @Column (name = "CCN2_MSG_CORRELID", nullable = false)
    private String ccn2MsgCorrelid;

    @Size (max = 255)
    @Column (name = "CCN2_MSG_MSGID")
    private String ccn2MsgMsgid;

}