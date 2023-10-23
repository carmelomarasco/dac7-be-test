package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import it.finanze.entrate.coopint.dboard.dpi.com.command.kafka.CustomHeader;
import it.finanze.entrate.coopint.dboard.dpi.com.command.kafka.KafkaUtils;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.KafkaProduceService;
import it.finanze.entrate.coopint.dboard.dpi.utils.JsonOperation;
import it.finanze.entrate.coopint.dboard.dpi.common.config.ProducerProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.enumeration.Context;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class KafkaProducer implements KafkaProduceService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public void send(Map<String, Object> header, String payload) {

        log.info("Event --> " + header.get(CustomHeader.EVENT_NAME));
        sendWithCallback(header, payload);
    }

    @Override
    public void sendMessageToRecovery(String payload, String topic, MessageHeaders messageHeaders, String messageKey, String stackTrace) {
    	Map<String, Object> headers = new HashMap<>();

        headers.put(KafkaHeaders.TOPIC, topic);
        headers.put(KafkaHeaders.MESSAGE_KEY, messageKey);
        headers.put(CustomHeader.PRODUCER_NAME, ProducerProperties.PRODUCER_NAME);
        headers.put(CustomHeader.STACKTRACE, stackTrace);
        headers.put(CustomHeader.CONTEXT, Context.DPI.getDescription());
        Map<String, Object> cleanMap = new HashMap<>();
        messageHeaders.forEach((k, v) -> {
            if (!k.startsWith("kafka_")) cleanMap.put(k, v);
        });
        cleanMap.put(KafkaHeaders.RECEIVED_MESSAGE_KEY, messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY));
        cleanMap.put(KafkaHeaders.RECEIVED_TOPIC, messageHeaders.get(KafkaHeaders.RECEIVED_TOPIC));
        cleanMap.put(KafkaHeaders.RECEIVED_TIMESTAMP, messageHeaders.get(KafkaHeaders.RECEIVED_TIMESTAMP));
        cleanMap.put(KafkaHeaders.RECEIVED_PARTITION_ID, messageHeaders.get(KafkaHeaders.RECEIVED_PARTITION_ID));

        headers.put(CustomHeader.PARENT_HEADER, JsonOperation.objectToJson(cleanMap));

        send(headers, payload);

    }
       
    
    @Override
    public void sendWithCallback(Map<String, Object> header, String payload) {
        log.info("sendWithCallback...");
        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.executeInTransaction(operations -> operations.send(buildMessage(header, payload)));
        
        KafkaUtils.printKafkaHeaders(header);
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Messaggio inviato correttamente sul Kafka.");
            }

            @Override
            public void onFailure(Throwable ex) {

                log.error("ListenableFutureCallback --> ", ex);
                String errorMessage = "E' stato ricevuto un messaggio di dimensioni superiori al limite consentito dal topic Kafka.";
                log.error(errorMessage);
                sendErrorMessage((String) header.get(KafkaHeaders.MESSAGE_KEY), errorMessage + "\n", (String) header.get(KafkaHeaders.TOPIC));
            }
        });
    }
    
    @Override
    public void sendErrorMessage(String messageKey, String errorMessage, String topic) {
        Map<String, Object> headers = new HashMap<>();
       
        headers.put(KafkaHeaders.TOPIC, topic);
        headers.put(KafkaHeaders.MESSAGE_KEY, messageKey);
        headers.put(CustomHeader.PRODUCER_NAME, ProducerProperties.PRODUCER_NAME);
        headers.put(CustomHeader.STACKTRACE, errorMessage);
        
        send(headers, CustomHeader.EMPTY_PAYLOAD);

    }
    
    private Message<String> buildMessage(Map<String, Object> header, String payload) {
        MessageBuilder<String> builder = MessageBuilder.withPayload(payload);
        header.forEach(builder::setHeader);
        return builder.build();
    }

	
}
