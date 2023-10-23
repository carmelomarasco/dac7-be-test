package it.finanze.entrate.coopint.dboard.dpi.res.command.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "RECEIVE_MESSAGE", schema = "DBOARD_DPI_R")
public class ReceiveMessageLazy implements Serializable {
    @EmbeddedId
    private ReceiveMessageId id;

//	@Id
//    @Size (max = 57)
//    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57, insertable=false, updatable=false )
    private String msgUuid;
//
//    @Id
//    @NotNull
//    @Column (name = "\"DATA\"", nullable = false)
//    private Instant data;
    
    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "COUNTRY_ID", nullable = false)
    private Country country;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "STATUS_ID", nullable = false)
    private ReceiveMessageStatus status;

    @Column (name = "MESSAGE_TYPE_INDIC_ID")
    private Short messageTypeIndicId;

    @Size (max = 4000)
    @Column (name = "MESSAGE_REF", length = 4000)
    private String messageRef;

    @Column (name = "MESSAGE_DATE")
    private Instant messageDate;

    @Size (max = 255)
    @Column (name = "XSD_VERSION")
    private String xsdVersion;

    @Column (name = "FISCAL_YEAR")
    private Short fiscalYear;

    @Size (max = 255)
    @NotNull
    @Column (name = "CCN2_MSG_MSGID", nullable = false)
    private String ccn2MsgMsgid;

    @NotNull
    @Column (name = "TOTAL_SELLER_NUMBER", nullable = false)
    private Integer totalSellerNumber;

    @NotNull
    @Column (name = "PLATFORM_OPERATORS_LIST", length = 4000)
    private String listPO;

    @NotNull
    @Column (name = "RECEIVED_ON", nullable = false)
    private Instant receivedOn;

    @OneToMany(mappedBy = "messageByMsgUuid", cascade = CascadeType.ALL)
    private List<StatusMessageLazy> statusMessagesByMsgUuid;
}