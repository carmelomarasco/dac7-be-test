package it.finanze.entrate.coopint.dboard.dpi.com.command.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private String msgUuid;

    private String buildingReportId;

    private String countryCode;

    private String messageTypeIndic;

    private String msgClass;

    private String status;

    private Long fiscalYear;

    private String messageRef;

    private Long totalAccount;

    private Long totalRfi;

    private String context;

    private Date msgDate;

    private String mwMsgMsgid;

    private String mwMsgMsgidAscii;

    private Date buildStartDate;

    private Date buildEndDate;

    private String rfiNameList;

    private String xsdVersion;

    private List<CommunicationDto> qCommunicationsList;


}
