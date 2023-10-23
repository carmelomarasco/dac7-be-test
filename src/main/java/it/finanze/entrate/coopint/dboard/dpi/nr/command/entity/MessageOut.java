package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MESSAGE_OUT", schema = "DBOARD_DPI_NR")
public class MessageOut {
    @EmbeddedId
    private MessageOutId id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "COUNTRY_ID", nullable = false)
    private Country country;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "STATUS_ID", nullable = false)
    private MsgStatus status;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MESSAGE_TYPE_INDIC_ID", nullable = false)
    private MessageTypeIndic messageTypeIndic;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MSG_CLASS_ID", nullable = false)
    private MsgClass msgClass;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
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

    @Size (max = 57)
    @Column (name = "BUILDING_REPORT_ID", nullable = true, length = 57)
    private String buildingReportId;

    @Column (name = "SEND_DATE")
    private Instant sendDate;

    @Size (max = 255)
    @Column (name = "SEND_USER_CF")
    private String sendUserCf;








//    @OneToMany(mappedBy = "messageOut", cascade = CascadeType.ALL)
//    private List<Communication> communicationsList;
//
//    @OneToMany(mappedBy = "messageOut", cascade = CascadeType.ALL)
//    private List<CorrectableElement> correctableElementsList;

//    @OneToOne(mappedBy = "qMessageEntity", cascade = CascadeType.ALL)
//    private QExcelEntity qExcelByMsgUuid;

    @ManyToOne
    @JoinColumn(name = "BUILDING_REPORT_ID", referencedColumnName = "BUILDING_REPORT_ID", nullable = false, insertable = false, updatable = false)
    private NewDataBuildingReport newDataBuildingReport;

//    @ManyToOne
//    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", nullable = false, insertable = false, updatable = false)
//    private QCountryEntity qCountryEntity;

//    @ManyToOne
//    @JoinColumn(name = "MESSAGE_TYPE_INDIC_ID", referencedColumnName = "MESSAGE_TYPE_INDIC_ID", nullable = false, insertable = false, updatable = false)
//    private QMessageTypeIndicEntity qMessageTypeIndicEntity;

//    @ManyToOne
//    @JoinColumn(name = "MSG_CLASS_ID", referencedColumnName = "MSG_CLASS_ID", nullable = false, insertable = false, updatable = false)
//    private QMsgClassEntity qMsgClassEntity;

//    @ManyToOne
//    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable = false, insertable = false, updatable = false)
//    private QMessageStatusEntity qMessageStatusEntity;

//    @ManyToOne
//    @JoinColumn(name = "FISCAL_YEAR", referencedColumnName = "FISCAL_YEAR", nullable = false, insertable = false, updatable = false)
//    private QFiscalYearEntity qFiscalYearEntity;


    // TODO Spostare questi riferimenti nel repository
//    @OneToOne(mappedBy = "messageOut", cascade = CascadeType.ALL)
//    private MsgData msgData;
//
//    @OneToMany(mappedBy = "messageOut", cascade = CascadeType.ALL)
//    private List<StatusMessageIn> statusMessageIns;

}