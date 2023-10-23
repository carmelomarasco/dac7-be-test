package it.finanze.entrate.coopint.dboard.dpi.com.command.kafka;

import java.util.Date;

import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.SmValidationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiCommunicationService;
import it.finanze.entrate.coopint.dboard.dpi.common.config.EventNameProperties;
import it.finanze.entrate.coopint.dboard.dpi.common.config.TopicProperties;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.util.concurrent.FailureCallback;

//@Service
//@CommonsLog
public class KafkaConsumer {
//	@Autowired
//	private SmValidationResultRepository smValidationResultRepository;
//
//	private final KafkaEventHandler kafkaEventHandler;
//
//	private final DpiCommunicationService communicationService;
//
//	@Autowired
//	private AsyncListenableTaskExecutor executor;
//	@Autowired
//	private KafkaContainerSupportMethods containerSupportMethods;
//
//	@Value("${kafka.container.id}")
//	private String containerId;
//
//	@Autowired
//	public KafkaConsumer(KafkaEventHandler kafkaEventHandler, DpiCommunicationService communicationService
//						 ) {
//		this.kafkaEventHandler = kafkaEventHandler;
//		this.communicationService = communicationService;
//	}
//
//	@KafkaListener(topics = "#{'${topic.boundaryService}'}", clientIdPrefix = "DboardDpiCommmand"
//			+ "#{'${topic.boundaryService}'}",  id = "#{'${kafka.container.id}'}" + "1", idIsGroup = false
//			, groupId = "Dpi-common-command" + "_BOUNDARY_GROUP")
//	public void getCommunicationFromBoundaryService(@Payload String payload, @Headers MessageHeaders messageHeaders,
//			Acknowledgment ack) {
//		if (!kafkaEventHandler.isEventForMe(messageHeaders)) {
//			ack.acknowledge();
//			return;
//		}
//
//		KafkaUtils.printKafkaHeaders(messageHeaders);
//
//		long eventId = new Date().getTime();
//		String eventReceived = (String) messageHeaders.get(CustomHeader.EVENT_NAME);
//		String messageKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
//		String logRequest = (String) messageHeaders.get(CustomHeader.LOG_REQUEST);
//		log.info(logRequest);
//
//
//		log.info("*********** START event --> " + eventReceived + " || msgKey --> " + messageKey + " || idFlow --> "
//				+ eventId + " *************");
//		String _containerId = containerId + "1";
//
//		FailureCallback failureCallback = ex ->{
//			// perform retry mechanism like a dead letter queue here
//			// alternatively nack the event with acknowledgement.nack(timeout)
//			kafkaEventHandler.handleThrowable2(payload, TopicProperties.DPI_NAT_COMMAND, messageHeaders, ack, ex);
//			containerSupportMethods.resumeConsumer(_containerId);
//			log.info("Resumed consumer " + _containerId);
//			log.warn("Error callback");
//		};
//
//		containerSupportMethods.pauseConsume(_containerId);
//		log.info("Pause consumer " + _containerId);
//		executor.submitListenable(() ->communicationService.processReceivedCommunication(payload, messageHeaders)).addCallback(result -> {
//			ack.acknowledge();
//			containerSupportMethods.resumeConsumer(_containerId);
//			log.info("Resumed consumer " + _containerId);
//			log.info("Success callback");
//		}, failureCallback );
//
//		log.info("*********** END event --> " + eventReceived + " || msgKey --> " + messageKey + " || idFlow --> "
//				+ eventId + " *************");
//
//	}
//
//	@KafkaListener(topics = "#{'${topic.dpiNatCommand}'}", clientIdPrefix = "DboardDpiCommmand"
//			+ "#{'${topic.dpiNatCommand}'}", groupId = "Dpi-common-command"
//					+ "_SELF_COMMAND_GROUP", id = "#{'${kafka.container.id}'}" + "3", idIsGroup = false)
//	public void getEventFromItself(@Payload String payload, @Headers MessageHeaders messageHeaders,
//			Acknowledgment ack) throws Exception {
//
//		if (!kafkaEventHandler.isEventForMe(messageHeaders)) {
//			ack.acknowledge();
//			return;
//		}
//		long eventId = new Date().getTime();
//		String eventReceived = (String) messageHeaders.get(CustomHeader.EVENT_NAME);
//		String messageKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
//		String _containerId = containerId + "3";
//
//		log.info("*********** START event --> " + eventReceived + " || msgKey --> " + messageKey + " || idFlow --> "
//				+ eventId + " *************");
//		FailureCallback failureCallback = ex ->{
//			// perform retry mechanism like a dead letter queue here
//			// alternatively nack the event with acknowledgement.nack(timeout)
//			kafkaEventHandler.handleThrowable2(payload, TopicProperties.DPI_NAT_COMMAND ,messageHeaders, ack, ex);
//			containerSupportMethods.resumeConsumer(_containerId);
//			log.info("Resumed consumer " + _containerId);
//			log.warn("Error callback");
//		};
//
//		if (EventNameProperties.ADD_CONTRIBUTE_TO_EFFECTIVE_DEST.equals(eventReceived)) {
//			containerSupportMethods.pauseConsume(_containerId);
//			log.info("Pause consumer " + _containerId);
//			String idCommunication = new String(it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils
//					.convertBase64ToByteArray(payload));
//			executor.submitListenable(() ->communicationService.addContributeToEffectiveDest(idCommunication)).addCallback(result -> {
//				ack.acknowledge();
//				containerSupportMethods.resumeConsumer(_containerId);
//				log.info("Resumed consumer " + _containerId);
//				log.info("Success callback");
//			},failureCallback);
//		}
//
//		if (EventNameProperties.UPDATE_STATUS.equals(eventReceived)) {
//			containerSupportMethods.pauseConsume(_containerId);
//			log.info("Pause consumer " + _containerId);
//			executor.submitListenable(() ->communicationService.updateStatus(payload, messageHeaders)).addCallback(result -> {
//				ack.acknowledge();
//				containerSupportMethods.resumeConsumer(_containerId);
//				log.info("Resumed consumer " + _containerId);
//				log.info("Success callback");
//			}, failureCallback);
//
//		}
//		if (EventNameProperties.FINALIZE_COMMUNICATION.equals(eventReceived)) {
//			containerSupportMethods.pauseConsume(_containerId);
//			log.info("Pause consumer " + _containerId);
//			String protocol = new String(it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils
//					.convertBase64ToByteArray(payload));
//
//			executor.submitListenable(() ->communicationService.finalizeCommunication(protocol)).addCallback(result -> {
//				ack.acknowledge();
//				containerSupportMethods.resumeConsumer(_containerId);
//				log.info("Resumed consumer " + _containerId);
//				log.info("Success callback");
//			},failureCallback);
//		}
//
//		if (EventNameProperties.EXPIRED_FISCAL_YEAR.equals(eventReceived)) {
//			containerSupportMethods.pauseConsume(_containerId);
//			log.info("Pause consumer " + _containerId);
//			executor.submitListenable(
//					() ->communicationService.finalizeAllCommunicationsOfYear(Long.parseLong(messageKey)))
//					.addCallback(result -> {
//				ack.acknowledge();
//				containerSupportMethods.resumeConsumer(_containerId);
//				log.info("Resumed consumer " + _containerId);
//				log.info("Success callback");
//			}, failureCallback);
//
//		}
//
//		log.info("*********** END event --> " + eventReceived + " || msgKey --> " + messageKey + " || idFlow --> "
//				+ eventId + " *************");
//	}
//
//	/*@KafkaListener(topics = "#{'${topic.dueDateService}'}", clientIdPrefix = "DboardDpiCommmand"
//			+ "#{'${topic.dueDateService}'}", groupId = "Dpi-common-command"
//					+ "_DUE_DATE_GROUP", id = "#{'${kafka.container.id}'}" + "4", idIsGroup = false)
//	public void getEventFromDueDate(@Payload String payload, @Headers MessageHeaders messageHeaders,
//			Acknowledgment ack) {
//
//		if (!kafkaEventHandler.isEventForMe(messageHeaders)) {
//			ack.acknowledge();
//			return;
//		}
//		long eventId = new Date().getTime();
//		String eventReceived = (String) messageHeaders.get(CustomHeader.EVENT_NAME);
//		String messageKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
//		String _containerId = containerId + "4";
//
//		log.info("*********** START event --> " + eventReceived + " || msgKey --> " + messageKey + " || idFlow --> "
//				+ eventId + " *************");
//
//		if (EventNameProperties.EXPIRED_FISCAL_YEAR.equals(eventReceived)) {
//			boolean isAlreadyExpired;
//			try {
//				// TODO verificare
//				//isAlreadyExpired = false;
//				isAlreadyExpired = fiscalYearService.setExpiredFiscalYear(payload, messageHeaders);
//				if (!isAlreadyExpired) {
//					containerSupportMethods.pauseConsume(_containerId);
//					log.info("Pause consumer " + _containerId);
//					executor.submitListenable(() ->communicationService.notifyExpiredCommunications(messageHeaders)).addCallback(result -> {
//						ack.acknowledge();
//						containerSupportMethods.resumeConsumer(_containerId);
//						log.info("Resumed consumer " + _containerId);
//						log.info("Success callback");
//					}, ex -> {
//						// perform retry mechanism like a dead letter queue here
//						// alternatively nack the event with acknowledgement.nack(timeout)
//						kafkaEventHandler.handleThrowable2(payload, TopicProperties.DPI_NAT_COMMAND ,messageHeaders, ack, ex);
//						containerSupportMethods.resumeConsumer(_containerId);
//						log.info("Resumed consumer " + _containerId);
//						log.warn("Error callback");
//					});
//				}
//			} catch (Throwable e) {
//				if (kafkaEventHandler.handleThrowable(payload, TopicProperties.DPI_NAT_COMMAND,messageHeaders, ack, e))
//					return;
//			}
//		}
//
//		log.info("*********** END event --> " + eventReceived + " || msgKey --> " + messageKey + " || idFlow --> "
//				+ eventId + " *************");
//	}*/
///*
//	@KafkaListener(topics = "#{'${topic.dpiNrCommand}'}", clientIdPrefix = "DboardDpiCommmand"
//			+ "#{'${topic.dpiNrCommand}'}", groupId = "Dpi-common-command"
//					+ "_NR_COMMAND_EVENT_GROUP", id = "#{'${kafka.container.id}'}" + "5", idIsGroup = false)
//	public void getCommunicationFromDpiNRCommand(@Payload String payload, @Headers MessageHeaders messageHeaders,
//			Acknowledgment ack) {
//		if (!kafkaEventHandler.isEventForMe(messageHeaders)) {
//			ack.acknowledge();
//			return;
//		}
//
//		long eventId = new Date().getTime();
//		String eventReceived = (String) messageHeaders.get(CustomHeader.EVENT_NAME);
//		String messageKey = (String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY);
//		String _containerId = containerId + "5";
//
//		log.info("*********** START event --> " + eventReceived + " || msgKey --> " + messageKey
//				+ " || idFlow --> " + eventId + " *************");
//
//		if (EventNameProperties.BUILD_MESSAGE.equals(eventReceived)) {
//			containerSupportMethods.pauseConsume(_containerId);
//			log.info("Pause consumer " + _containerId);
//			executor.submitListenable(() ->communicationService.updateEffectiveDestByComUuid(payload, messageHeaders)).addCallback(result -> {
//				ack.acknowledge();
//				containerSupportMethods.resumeConsumer(_containerId);
//				log.info("Resumed consumer " + _containerId);
//				log.info("Success callback");
//			}, ex -> {
//				// perform retry mechanism like a dead letter queue here
//				// alternatively nack the event with acknowledgement.nack(timeout)
//				kafkaEventHandler.handleThrowable2(payload, TopicProperties.DPI_NAT_COMMAND, messageHeaders, ack, ex);
//				containerSupportMethods.resumeConsumer(_containerId);
//				log.info("Resumed consumer " + _containerId);
//				log.warn("Error callback");
//			});
//		}
//
//		log.info("*********** END event --> " + eventReceived + " || msgKey --> " + messageKey
//				+ " || idFlow --> " + eventId + " *************");
//	}
//*/
}
