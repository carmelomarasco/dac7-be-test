package it.finanze.entrate.coopint.dboard.dpi.com.command.kafka;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@CommonsLog
public class KafkaUtils {

    private static Map<String, Object> commonMapPopulate(String topic, String messageKey, String eventName, String producerName) {
        log.info("Prepare header to send to: " + topic);
        Map<String, Object> header = new HashMap<>();
        header.put(KafkaHeaders.TOPIC, topic);
        header.put(KafkaHeaders.MESSAGE_KEY, messageKey);
        header.put(CustomHeader.EVENT_NAME, eventName);
        header.put(CustomHeader.PRODUCER_NAME, producerName);
        return header;
    }

    public static Map<String, Object> buildCommonHeader(String topic, String messageKey, String eventName, String producerName) {
        return commonMapPopulate(topic, messageKey, eventName, producerName);
    }

    public static Map<String, Object> buildHeaderForUpdateEffectiveDest(String topic, String messageKey, String eventName, String producerName) {
        return commonMapPopulate(topic, messageKey, eventName, producerName);
    }
    
    public static void printKafkaHeaders(MessageHeaders headers) {
    	log.info("************************** KAFKA HEADERS **************************");
    	headers.forEach((key, value1) -> {
            String value = value1 + "";
            log.info("    Key: [" + key + "], Value[" + (value.length() > 500 ? value.substring(0, 500) + "..." : value) + "]");
        });
    	log.info("*******************************************************************");
    }
    
    public static void printKafkaHeaders(Map<String, Object> headers) {
    	log.info("************************** KAFKA HEADERS **************************");
    	for (Entry<String, Object> entry : headers.entrySet()) {
    		// the key
    		String key = entry.getKey();
    		// limit value to LOG_MAX_LENGTH in case we pass byte arrays
    		String value = ("" + entry.getValue()).length() > LOG_MAX_LENGTH ? ("" + entry.getValue()).substring(0, LOG_MAX_LENGTH) : ("" + entry.getValue());
    		log.info(TAB + key + ": [" + value + "]");
    	}
    	log.info("*******************************************************************");
    }
    
    private static final String TAB = "    "; // 4 spaces
    private static final int LOG_MAX_LENGTH = 200;
}
