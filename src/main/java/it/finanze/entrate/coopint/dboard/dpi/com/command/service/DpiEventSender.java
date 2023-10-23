package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

import java.util.Date;

import it.finanze.entrate.coopint.dboard.dpi.common.bean.AllInfoAcquiredBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.HistoryBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.UpdateBean;

public interface DpiEventSender {

    void sendEventUpdateStatuses(UpdateBean pwb, String statusFrom);

    void sendEventUpdateStatus(String msgKey, String status, String dpiCommandSideCommand, Date reportingPeriod, String previousStatus, String msgTypeIndic, String comUuid, Boolean invalidatedBySystem);

    void sendEventDeleteCommunications(UpdateBean pwb);

    void sendEventAcquiredCommunication(String dpiCommandSideEvent, String protocol, String eventName, AllInfoAcquiredBean allInfoAcquiredBean, String sidOutcome);

    void sendEventHistory(HistoryBean hb, String dpiCommandSideCommand);

    void sendEventDeleteCommunication(String idComToDelete, String dpiCommandSideEvent);

    void sendEventExpiredFiscalYear(Long year, String dpiCommandSideEvent);

    void sendEventUpdateFiscalYear(UpdateBean pwb, String topic, String payloadToNotify);

    void sendEventFinishedExpiredCommunications(String dpiCommandSideEvent, Long year);

    void sendCommandFinalizeCommunication( String protocol, boolean finishedProcess);

    void sendEventFinalizeCommunication(Long year, String dpiCommandSideEvent, String comUuid, Boolean isFinished, String senderCf, long time, String urlCallback, String protocol,Date reportingPeriod,String previousStatus);

    void sendEventLateCommunication(Long year, String payload, String senderCf, long time, String protocol, String comUuid, String urlComView);

    void sendEventStartBuildingMessages(long year);

    void sendCommandAddContributeToEffDest(String commUuid);

    void sendEventStartBuildExcel( String protocol );

}
