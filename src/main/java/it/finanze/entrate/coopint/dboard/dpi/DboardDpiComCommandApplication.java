package it.finanze.entrate.coopint.dboard.dpi;


import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import eu.europa.ec.taxud.dac7.validation.Constants;
import eu.europa.ec.taxud.validation.ValidationContext;
import eu.europa.ec.taxud.validation.ValidationManager;
import eu.europa.ec.taxud.validation.input.InputFormatType;
import eu.europa.ec.taxud.validation.input.ValidationInput;
import it.finanze.entrate.coopint.dboard.dpi.common.config.EventNameProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.config.ProducerProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.config.TopicProperties;
import it.finanze.entrate.coopint.dboard.dpi.config.ValidationProperties;
import it.finanze.entrate.coopint.dboard.dpi.utils.Dac7VM;
import it.finanze.entrate.coopint.dboard.dpi.utils.Dac7VMRunner;
import it.sogei.generateuuid.GenerateUUID;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@SpringBootApplication
@EnableAsync
// @ComponentScan(basePackages = "package it.finanze.entrate.coopint.dboard.dpi.*")
@ComponentScan(basePackageClasses  = {CorsConfig.class})
public class DboardDpiComCommandApplication implements CommandLineRunner {

    @Autowired
    KafkaProperties kafkaProperties;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) throws UnknownHostException {

        System.setProperty("MY_ADDRESS", InetAddress.getLocalHost().getHostName().concat("-"));
        SpringApplication.run(DboardDpiComCommandApplication.class, args);

    }

    // public void readFile() {
    //     File inputFile = new File("resources/corretto.xml");
    //     InputStream inputStream = getClass().getClassLoader().getResourceAsStream("resources/corretto.xml");
    //     try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
    //         String firstLine = reader.readLine();
    //         System.out.println(firstLine);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }


    @Override
    public void run(String... args) throws Exception {
        log.info("####################################################");
        System.setProperty(GenerateUUID.PROPERTY_NAME, UUID.randomUUID().toString());
        log.info("Uuid setting in jvm was successful");
        log.info("####################################################");

//        log.info("########## KAFKA PROPERTIES CONSUMER ###########");
//        kafkaProperties.buildConsumerProperties()
//                .forEach((k, v) -> log.info("Key: ".concat(k).concat("   ").concat("Value: ").concat(v.toString())));
//        log.info("##########################################");
//        log.info("########## KAFKA PROPERTIES PRODUCER ###########");
//        kafkaProperties.buildProducerProperties()
//                .forEach((k, v) -> log.info("Key: ".concat(k).concat("   ").concat("Value: ").concat(v.toString())));
//        log.info("##########################################");
//
//        String user = System.getenv("KAFKA_USER-ENT-SA-0439_FU_001");
//        log.info("KAFKA_USER = '" + user + "'");
//        String psw = System.getenv("KAFKA_PASSWORD-ENT-SA-0439_FU_001");
//        log.info("KAFKA_PSW = '" + psw + "'");
//        log.info("########## KAFKA PROPERTIES ###########");

    }
}
