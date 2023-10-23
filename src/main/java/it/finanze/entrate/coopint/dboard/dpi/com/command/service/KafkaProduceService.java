package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

import org.springframework.messaging.MessageHeaders;

import java.util.Map;

public interface KafkaProduceService {
    void send(Map<String, Object> header, String payload);

    //void sendMessageToRecovery(String payload, MessageHeaders messageHeaders, String messageKey, String convert);
    
    void sendWithCallback(Map<String, Object> header, String payload);
    
    void sendErrorMessage(String messageKey, String errorMessage, String topic);

	void sendMessageToRecovery(String payload, String topic, MessageHeaders messageHeaders, String messageKey,
			String stackTrace);
}
