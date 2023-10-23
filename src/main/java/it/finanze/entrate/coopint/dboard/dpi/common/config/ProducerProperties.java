package it.finanze.entrate.coopint.dboard.dpi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties(
        prefix = "producer"
)
public class ProducerProperties {

    public static String PRODUCER_NAME;

    public void setProducerName(String producerName){
        PRODUCER_NAME = producerName;
    }
}
