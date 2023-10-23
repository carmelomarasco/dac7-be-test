package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.finanze.entrate.coopint.dboard.dpi.com.command.kafka.CustomHeader;
import it.finanze.entrate.coopint.dboard.dpi.com.command.kafka.KafkaUtils;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiEventSender;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.KafkaProduceService;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.DateUtil;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.JsonOperation;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.AllInfoAcquiredBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.HistoryBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.UpdateBean;
import it.finanze.entrate.coopint.dboard.dpi.common.config.EventNameProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.config.ProducerProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.config.TopicProperties;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class DpiEventSenderImpl implements DpiEventSender {

    @Autowired
    KafkaProduceService kafkaProduceService;

    @Override
    public void sendEventUpdateStatuses(UpdateBean pwb, String statusTo) {
        //prepare header to send UPDATE_STATUS for each id,to itself
        pwb.getIdCommunications().forEach(idCommunication -> sendEventUpdateStatus(idCommunication, statusTo, TopicProperties.DPI_NAT_COMMAND, null, null, null, idCommunication, false));
    }

    @Override
    public void sendEventUpdateStatus(String messKey, String statusTo, String topic, Date reportingPeriod, String previousStatus, String msgTypeIndic, String comUuid, Boolean invalidatedBySystem) {
        //prepare header to send UPDATE_STATUS,to EVENT topic

        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                topic,
                messKey,
                EventNameProperties.UPDATE_STATUS,
                ProducerProperties.PRODUCER_NAME
        );
        header.put(CustomHeader.REPORTING_PERIOD, reportingPeriod != null ? reportingPeriod.getTime() : null);
        header.put(CustomHeader.COM_UUID, comUuid);
        //this key is used by sinottico
        header.put(CustomHeader.PREVIOUS_STATUS, previousStatus);
        header.put(CustomHeader.MESSAGE_TYPE_INDIC, msgTypeIndic);
        header.put(CustomHeader.INVALIDATE_BY_SYSTEM, invalidatedBySystem);

        String payloadB64 = IOUtils.convertByteArrayToBase64(statusTo.getBytes());
        kafkaProduceService.send(header, payloadB64);
    }

    @Override
    public void sendEventDeleteCommunications(UpdateBean pwb) {
        //prepare header to send DELETE_COMMUNICATION for each id,to itself
        pwb.getIdCommunications().forEach(idCommunication -> {
            sendEventDeleteCommunication(idCommunication, TopicProperties.DPI_NAT_COMMAND);
        });
    }

    @Override
    public void sendEventDeleteCommunication(String idCommunication, String topic) {

        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                topic,
                idCommunication,
                EventNameProperties.DELETE_COMMUNICATION,
                ProducerProperties.PRODUCER_NAME
        );
        kafkaProduceService.send(header, CustomHeader.EMPTY_PAYLOAD);
    }

    @Override
    public void sendEventExpiredFiscalYear(Long year, String topic) {

        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                topic,
                String.valueOf(year),
                EventNameProperties.EXPIRED_FISCAL_YEAR,
                ProducerProperties.PRODUCER_NAME
        );
        kafkaProduceService.send(header, CustomHeader.EMPTY_PAYLOAD);
    }

    @Override
    public void sendEventAcquiredCommunication(String dpiCommandSideEvent, String protocol, String eventName, AllInfoAcquiredBean allInfoAcquiredBean, String sidOutcome) {

        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                TopicProperties.DPI_NAT_EVENT,
                protocol,
                eventName,
                ProducerProperties.PRODUCER_NAME
        );
        header.put(CustomHeader.MESSAGE_TYPE_INDIC, allInfoAcquiredBean.getMessageTypeIndic());
        header.put(CustomHeader.PAYLOAD_II, JsonOperation.objectToJson(allInfoAcquiredBean.getDpiNationalNotifyBean()));
        header.put(CustomHeader.REPORTING_PERIOD, allInfoAcquiredBean.getReportingPeriod().getTime());
        header.put(CustomHeader.SENDING_FISCAL_CODE, allInfoAcquiredBean.getCommunicationBean().getSendingFiscalCode());
        header.put(CustomHeader.DATE_RECEIVED_TIMESTAMP, Objects.requireNonNull(DateUtil.toTimestamp(allInfoAcquiredBean.getCommunicationBean().getSentDate())).getTime());
        header.put(CustomHeader.LATE_COM, allInfoAcquiredBean.getFyForCurrentComIsExpired());
        header.put(CustomHeader.SID, sidOutcome);

        String payloadJson = JsonOperation.objectToJson(allInfoAcquiredBean.getCommunicationBean());
        String payloadB64 = IOUtils.convertByteArrayToBase64(payloadJson.getBytes());
        kafkaProduceService.send(header, payloadB64);
    }


    @Override
    public void sendEventHistory(HistoryBean hb, String topic) {
        Map<String, Object> headerHistory = KafkaUtils.buildCommonHeader(
                topic,
                hb.getProtocol(),
                EventNameProperties.PROCESS_HISTORY,
                ProducerProperties.PRODUCER_NAME
        );
        String payloadJson = JsonOperation.objectToJson(hb);
        String payloadB64 = IOUtils.convertByteArrayToBase64(payloadJson.getBytes());

        kafkaProduceService.send(headerHistory, payloadB64);
    }

    @Override
    public void sendEventUpdateFiscalYear(UpdateBean pwb, String topic, String payloadToNotify) {
        Map<String, Object> headerHistory = KafkaUtils.buildCommonHeader(
                topic,
                String.valueOf(pwb.getFiscalYear()),
                EventNameProperties.UPDATE_FISCAL_YEAR,
                ProducerProperties.PRODUCER_NAME
        );
        headerHistory.put(CustomHeader.PAYLOAD_II, payloadToNotify);

        String payloadJson = JsonOperation.objectToJson(pwb);
        String payloadB64 = IOUtils.convertByteArrayToBase64(payloadJson.getBytes());

        kafkaProduceService.send(headerHistory, payloadB64);
    }

    @Override
    public void sendEventFinishedExpiredCommunications(String dpiCommandSideEvent, Long year) {

        Map<String, Object> headerHistory = KafkaUtils.buildCommonHeader(
                dpiCommandSideEvent,
                String.valueOf(year),
                EventNameProperties.FINISHED_EXPIRED_COMMUNICATIONS,
                ProducerProperties.PRODUCER_NAME
        );
        kafkaProduceService.send(headerHistory, CustomHeader.EMPTY_PAYLOAD);
    }

    @Override
    public void sendCommandFinalizeCommunication(String protocol, boolean finishedProcess) {
        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                TopicProperties.DPI_NAT_COMMAND,
                protocol,
                EventNameProperties.FINALIZE_COMMUNICATION,
                ProducerProperties.PRODUCER_NAME
        );
        header.put(CustomHeader.ENDED_PROCESS, finishedProcess);
        String payloadB64 = IOUtils.convertByteArrayToBase64(protocol.getBytes());
        kafkaProduceService.send(header, payloadB64);
    }

    @Override
    public void sendEventFinalizeCommunication(Long year, String dpiCommandSideEvent, String comUuid, Boolean finishedProcess, String senderCf, long time, String urlCallback, String protocol, Date reportingPeriod, String previousStatus) {
        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                dpiCommandSideEvent,
                String.valueOf(year),
                EventNameProperties.FINALIZE_COMMUNICATION,
                ProducerProperties.PRODUCER_NAME
        );
        header.put(CustomHeader.ENDED_PROCESS, finishedProcess);
        //la parte seguente serve per l'european commission
        header.put(CustomHeader.SENDING_FISCAL_CODE, senderCf);
        header.put(CustomHeader.DATE_RECEIVED_TIMESTAMP, time);
        header.put(CustomHeader.URL_CALLBACK, urlCallback);
        header.put(CustomHeader.PROTOCOL, protocol);
        //this key is used by sinottico
        header.put(CustomHeader.REPORTING_PERIOD, reportingPeriod.getTime());
        header.put(CustomHeader.PREVIOUS_STATUS, previousStatus);


        String payloadB64 = IOUtils.convertByteArrayToBase64(comUuid.getBytes());
        kafkaProduceService.send(header, payloadB64);
    }

    @Override
    public void sendEventLateCommunication(Long year, String payload, String senderCf, long time, String protocol, String comUuid, String urlComCommand) {
        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                TopicProperties.DPI_NAT_EVENT,
                String.valueOf(year),
                EventNameProperties.LATE_COMMUNICATION,
                ProducerProperties.PRODUCER_NAME
        );
        header.put(CustomHeader.SENDING_FISCAL_CODE, senderCf);
        header.put(CustomHeader.DATE_RECEIVED_TIMESTAMP, time);
        header.put(CustomHeader.PROTOCOL, protocol);
        header.put(CustomHeader.COM_UUID, comUuid);
        header.put(CustomHeader.URL_CALLBACK, urlComCommand);

        kafkaProduceService.send(header, payload);
    }
    @Override
    public void sendEventStartBuildingMessages(long year) {
        // TODO controllare
        Map<String, Object> header = KafkaUtils.buildCommonHeader(
//                TopicProperties.TOPIC_CRS_NAT_VIEW_SIDE_EVENT,
                TopicProperties.DPI_NR_EVENT,
                Long.toString(year),
//                EventNameProperties.START_BUILDING_MESSAGES_EXPIRED_FY,
                EventNameProperties.BUILD_MESSAGE,
                ProducerProperties.PRODUCER_NAME
        );
        kafkaProduceService.send(header, CustomHeader.EMPTY_PAYLOAD);
    }

    @Override
    public void sendCommandAddContributeToEffDest(String comUuid) {
        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                TopicProperties.DPI_NAT_COMMAND,
                comUuid,
                EventNameProperties.ADD_CONTRIBUTE_TO_EFFECTIVE_DEST,
                ProducerProperties.PRODUCER_NAME
        );
        String payloadB64 = IOUtils.convertByteArrayToBase64(comUuid.getBytes());
        kafkaProduceService.send(header, payloadB64);
    }

    @Override
    public void sendEventStartBuildExcel(String protocol) {
        // TODO Qual è il topic e l'evento per Excel?
        Map<String, Object> header = KafkaUtils.buildCommonHeader(
                TopicProperties.DPI_NAT_EVENT,
                protocol,
                EventNameProperties.START_EXCEL_DAC7_NATIONAL,
                ProducerProperties.PRODUCER_NAME
        );
        String payloadB64 = IOUtils.convertByteArrayToBase64(protocol.getBytes());
        kafkaProduceService.send(header, payloadB64);

    }

}
