package it.finanze.entrate.coopint.dboard.dpi.com.command.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.client.ResourceAccessException;

import it.finanze.entrate.coopint.dboard.dpi.com.command.service.KafkaProduceService;
import it.finanze.entrate.coopint.dboard.dpi.common.config.ProducerProperties;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Service
public class KafkaEventHandler {

    private static final long SLEEP_TIME = 5000;

    @Autowired
    KafkaProduceService kafkaProduceService;


    public boolean isEventForMe(MessageHeaders messageHeaders) {
        String receiverName = (String) messageHeaders.get(CustomHeader.RECEIVER_NAME);
        return ProducerProperties.PRODUCER_NAME.equals(receiverName) || receiverName == null;
    }

    private boolean mustTryAgain(Throwable throwable) {
        return (throwable instanceof CannotCreateTransactionException ||
                throwable instanceof ResourceAccessException);
    }

    public boolean handleThrowable(String payload, String topic, @Headers MessageHeaders messageHeaders, Acknowledgment ack, Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        if (mustTryAgain(throwable)) {
            ack.nack(SLEEP_TIME);
            return true;
        }
        String messageKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
        kafkaProduceService.sendMessageToRecovery(payload, topic, messageHeaders, messageKey, StackTraceToString.convert(throwable));
        return false;
    }
    
    public void handleThrowable2(String payload, String topic, @Headers MessageHeaders messageHeaders, Acknowledgment ack, Throwable throwable) {
        if (mustTryAgain(throwable)) {
            ack.nack(SLEEP_TIME);
        } else {
            ack.acknowledge();
            String messageKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
            kafkaProduceService.sendMessageToRecovery(payload, topic, messageHeaders, messageKey, StackTraceToString.convert(throwable));
        }
    }
}
