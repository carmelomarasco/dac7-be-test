package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MESSAGE_OUT", schema = "DBOARD_DPI_NR")
public class MessageOutLazy {
    @EmbeddedId
    private MessageOutId id;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "COUNTRY_ID", nullable = false)
    private Country country;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "STATUS_ID", nullable = false)
    private MsgStatus status;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "MESSAGE_TYPE_INDIC_ID", nullable = false)
    private MessageTypeIndic messageTypeIndic;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "MSG_CLASS_ID", nullable = false)
    private MsgClass msgClass;

    @NotNull
    @ManyToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "FISCAL_YEAR", nullable = false)
    private FiscalYear fiscalYear;

    @Size (max = 255)
    @NotNull
    @Column (name = "MESSAGE_REF", nullable = false)
    private String messageRef;

    @NotNull
    @Column (name = "REPORTABLE_SELLERS_COUNT", nullable = false)
    private Integer reportableSellersCount;

    @NotNull
    @Column (name = "PLATFORM_OPERATORS_COUNT", nullable = false)
    private Short platformOperatorsCount;

    @NotNull
    @Column (name = "PLATFORM_OPERATORS_LIST", length = 4000)
    private String listPO;

    @Column (name = "MSG_DATE")
    private Instant msgDate;

    @Size (max = 48)
    @Column (name = "CCN2_MSG_MSGID", length = 48)
    private String ccn2MsgMsgid;

    @Size (max = 24)
    @Column (name = "CCN2_MSG_MSGID_ASCII", length = 24)
    private String ccn2MsgMsgidAscii;

    @Size (max = 255)
    @NotNull
    @Column (name = "XSD_VERSION", nullable = false)
    private String xsdVersion;

    @NotNull
    @Column (name = "BUILD_START_DATE", nullable = false)
    private Instant buildStartDate;

    @Column (name = "BUILD_END_DATE")
    private Instant buildEndDate;

    @Column (name = "SEND_DATE")
    private Instant sendDate;

    @Size (max = 255)
    @Column (name = "SEND_USER_CF")
    private String sendUserCf;

    // TODO Spostare nel repository
//    @OneToMany(mappedBy = "messageOut")
//    private List<StatusMessageIn> statusMessageIns;

}