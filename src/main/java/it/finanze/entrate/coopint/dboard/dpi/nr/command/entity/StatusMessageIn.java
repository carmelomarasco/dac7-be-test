package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "STATUS_MESSAGE_IN", schema = "DBOARD_DPI_NR")
public class StatusMessageIn {
    @Id
    @Size (max = 57)
    @Column (name = "STATUS_MESSAGE_UUID", nullable = false, length = 57)
    private String statusMessageUuid;

    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "STATUS_ID", nullable = false)
    private StatusMessageInStatus status;

    @Size (max = 4000)
    @Column (name = "MESSAGE_REF", length = 4000)
    private String messageRef;

    @Size (max = 255)
    @Column (name = "VALIDATION_RESULT")
    private String validationResult;

    @Size (max = 4000)
    @Column (name = "VALIDATED_BY", length = 4000)
    private String validatedBy;

    @NotNull
    @Column (name = "SM_BLOB", nullable = false)
    private byte[] smBlob;

    @Size (max = 48)
    @NotNull
    @Column (name = "CCN2_MSG_MSGID", nullable = false, length = 48)
    private String ccn2MsgMsgid;

    @NotNull
    @Column (name = "DATA_INS", nullable = false)
    private Instant dataIns;

}