package it.finanze.entrate.coopint.dboard.dpi.res.command.service.impl;

import com.google.common.collect.Lists;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.MessageBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.MessageForm;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.*;
import it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration.StatusMessageResult;
import it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration.StatusMessageStatusEnum;
import it.finanze.entrate.coopint.dboard.dpi.res.command.excel.InterrogationResultExcelRes;
import it.finanze.entrate.coopint.dboard.dpi.res.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.res.command.service.MessageService;
import it.finanze.entrate.coopint.dboard.dpi.res.command.utils.DateUtil;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.ParamUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.http.HttpClient;
import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@CommonsLog
public class MessageServiceImplRes implements MessageService {

    @Autowired
		ReceiveMessageRepository messageRepository;
    @Autowired
    ReceiveMessageLazyRepository messageLazyRepository;
    @Autowired
    InterrogationResultExcelRes interrogationResultExcel;
    @Autowired
    ExcelRepository excelRepository;
    @Autowired
		StatusMessageRepository statusMessageRepository;
    @Autowired
    ReceiveMessageTypeIndicRepository messageTypeIndicRepository;
    @Autowired
	ReceiveMessageStatusRepository messageStatusRepository;
//    @Autowired
//    QRfiRepository qRfiRepository;
//    @Autowired
//    QAeoiValidationRulesRepository aeoiValidationRulesRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    StatusMessageStatusRepository statusMessageStatusRepository;
    @Autowired
    ValidationErrorRepository validationErrorsRepository;
    @Autowired
    MsgDataRepository messageDataRepository;
    @Autowired
    FiscalYearRepository fisYearRepo;

//    private CrsJaxbHelper<CorrectableOrganisationPartyType> jaxbReportingFI = null;
//
//    private CrsJaxbHelper<oecd.ties.crsv1.crs.v1.CorrectableOrganisationPartyType> jaxbReportingFI_v1 = null;

    @Autowired
    HttpClient httpClient;
    @Override
    @Transactional(readOnly = true, transactionManager = "chainedTransactionManager")
    public List<MessageBean> findMessageBySearchForm(MessageForm messageForm) {

        if (MessageForm.isScambiNonConclusi(messageForm)) {

            log.info("findAllClosedExchanges");
//            List<String> listStatus = Arrays.asList(StatusMessageStatus.CREATED.getValue(), StatusMessageStatus.SUSPENDED.getValue());
            List<String> listStatus = Arrays.asList(StatusMessageStatusEnum.CREATED.getValue(), StatusMessageStatusEnum.SUSPENDED.getValue(), StatusMessageStatusEnum.TRANSMITTED.getValue());
            List<Short> listStatusId = statusMessageStatusRepository.findByStatusIn(listStatus).stream().map(StatusMessageStatus::getId).collect(Collectors.toList());
            return MessageBean.listFromEntities(messageLazyRepository.findMessageByStatusMessageStatusIdIn(listStatusId));

        }

        List<ReceiveMessageLazy> listMessageEntity = messageLazyRepository.findAll((Specification<ReceiveMessageLazy>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            /**msgUuid*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getMsgUuid())) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("msgUuid")), "%" + messageForm.getMsgUuid().toUpperCase() + "%")));
            }
            /**data ricezione da*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getStartDate())) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("receivedOn"), DateUtils.toTimestampFromHTMLStartDay(messageForm.getStartDate()).toInstant())));
                // predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("sendDate"), DateUtils.toTimestampFromHTMLStartDay(searchBean.getStartDate()).toInstant())));
            }
            /**data ricezione a*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getEndDate())) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("receivedOn"), DateUtils.toTimestampFromHTMLEndDay(messageForm.getEndDate()).toInstant())));
                // predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("sendDate"), DateUtils.toTimestampFromHTMLEndDay(searchBean.getEndDate()).toInstant())));
            }
            /**anno fiscale*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getYear())) {

                List<FiscalYear> listY = new ArrayList<>();
                for (String yearStr : messageForm.getYear()) {
                    Long year = Long.parseLong(yearStr);
                    FiscalYear fiscalYear = fisYearRepo.findByYear(year);
                    if (fiscalYear != null) {
                        listY.add(fiscalYear);
                    }
                }
                // predicates.add(root.get("fiscalYear").in(listY));
                predicates.add(root.get("fiscalYear").in(listY.stream().map(FiscalYear::getYear).collect(Collectors.toList())));
            }
            /**tipologia*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getType())) {
                predicates.add(root.get("messageTypeIndicId").in(messageForm.getType()));
            }
            /**Contesto*/
            // if (!ParamUtils.isNullOrEmpty(messageForm.getContext())) {
            //     predicates.add(root.get("context").in(messageForm.getContext()));
            // }
            /**Stato status message*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getStatusSM())) {
                List<String> listMsgUuid = statusMessageRepository.getMsgUuidByListIdStatusSMLast(messageForm.getStatusSM());
                List<Predicate> predicatesStatusSM = new ArrayList<>();
                if (listMsgUuid.isEmpty()) {
                    listMsgUuid.add("");
                }
                List<List<String>> listToAdd = Lists.partition(listMsgUuid, 900);
                listToAdd.forEach(partialList -> {
                    predicatesStatusSM.add(root.get("msgUuid").in(partialList));
                });
                predicates.add(criteriaBuilder.or(predicatesStatusSM.toArray(new Predicate[0])));
            }
            /**Esito acquisizione*/
            if (!ParamUtils.isNullOrEmpty(messageForm.getOutcome())) {
                predicates.add(root.get("status").get("id").in(
                		messageForm.getOutcome()
                		));
            }
            /**Paesi */
            if (!ParamUtils.isNullOrEmpty(messageForm.getCountry())) {
                List<String> listMsgUuid = messageRepository.getUuidMessageByListCountryCode(messageForm.getCountry());
                List<Predicate> predicatesCountry = new ArrayList<>();

                if (listMsgUuid.isEmpty()) {
                    listMsgUuid.add("");
                }
                List<List<String>> listToAdd = Lists.partition(listMsgUuid, 900);
                listToAdd.forEach(partialList -> {
                    predicatesCountry.add(root.get("msgUuid").in(partialList));
                });
                predicates.add(criteriaBuilder.or(predicatesCountry.toArray(new Predicate[0])));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return MessageBean.listFromEntities(listMessageEntity);
    }

    @Override
    public List<MessageBean> searchForSinottico(MessageForm messageForm) throws Exception {
        List<MessageBean> result = new ArrayList<>();

        Date startDate = DateUtil.getDateFromString(messageForm.getStartDateSinottico(), DateUtil.PATTERN_ITA_WITHOUT_HOUR);
        Date endDate = filterDateForSinottico(DateUtil.getDateFromString(messageForm.getEndDateSinottico(), DateUtil.PATTERN_ITA_WITHOUT_HOUR));
        List<String> listStatus = new ArrayList<>();

        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.setTime(startDate);
        Integer startYear = calendarStartDate.get(Calendar.YEAR);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);
        Integer endYear = calendarEndDate.get(Calendar.YEAR);

        if ("EXCHANGE_NOT_CLOSED".equals(messageForm.getTypeSearchParam())) {

            log.info("findAllClosedMessages");
            listStatus = Arrays.asList(StatusMessageStatusEnum.CREATED.getValue(), StatusMessageStatusEnum.SUSPENDED.getValue(), StatusMessageStatusEnum.TRANSMITTED.getValue());
            List<Short> listStatusId = statusMessageStatusRepository.findByStatusIn(listStatus).stream().map(StatusMessageStatus::getId).collect(Collectors.toList());
            return MessageBean.listFromEntities(messageLazyRepository.findMessageByStatusMessageStatusId(listStatusId));

        }

        if ("TARDIVI".equals(messageForm.getTypeSearchParam())) {

            List<ReceiveMessageLazy> listMsg = messageLazyRepository.getListMsgByDateFromTo(startYear, endYear);
            for (ReceiveMessageLazy msg : listMsg) {

                long dayPassed = DateUtil.getDaysPassed(Date.from(msg.getMessageDate()));
                if (dayPassed > 15) {
                    listStatus.add(StatusMessageStatusEnum.TRANSMITTED.getValue());
                    listStatus.add(StatusMessageStatusEnum.DELIVERED.getValue());
                    listStatus.add(StatusMessageStatusEnum.DOWNLOADED.getValue());
                    List<StatusMessage> listSm = statusMessageRepository.getListSmByMsgUuidAndListStatus(msg.getId().getMsgUuid(), listStatus);

                    if (listSm.isEmpty()) {
                        result.add(MessageBean.fromEntity(msg));
                    }
                }

            }

        }

        return result;
    }

    @Override
    public byte[] getTableExcel(MessageForm messageForm) throws Exception {

        List<MessageBean> communicationBean;

        if (messageForm.getTypeSearchParam().equals("EXCHANGE_NOT_CLOSED") || messageForm.getTypeSearchParam().equals("TARDIVI")) {
            communicationBean = searchForSinottico(messageForm);
        } else {
            communicationBean = findMessageBySearchForm(messageForm);
        }
        return interrogationResultExcel.build(communicationBean, messageForm);
    }
/*
    @Override
    public void insertExcelFromExcelService(ExcelXmlBean bean) {
        QExcelEntity excelEntity = new QExcelEntity();
        excelEntity.setMsgUuid(bean.getId());
        excelEntity.setExcelBlob(bean.getGZipExcel());
        excelRepository.saveAndFlush(excelEntity);


    }
*/
    @Override
    public boolean checkIfMsgIsAccepted(String msgUuid) {
        List<String> listOutcome = statusMessageRepository.getListOutcomeMsg(msgUuid);
        return listOutcome.contains(StatusMessageResult.ACCEPTED.getValue());
    }

////
////    @Transactional(propagation = Propagation.REQUIRES_NEW)
////    @Override
////    public String saveMessageAndGetMsgKeyIfCanSendCorrectableAndExcel(String messageUuid, String payload, String transmittingCountry, String statusMessageXmlGzipBase64, String statusMessageUuid, String mwMsgId, MessageStatusEnum messageStatus, String context, boolean isUploaded, boolean isRework, String messageGatewayId, Date receivedOn, Long nAccountReport, boolean needToPopulateCorrectable, String urlCallBack) throws Exception {
////        String msgKey = "";
////
////
////        String xmlBase64Gzip;
////
////        if(payload.equalsIgnoreCase(CustomHeader.EMPTY_PAYLOAD)
////                || payload.equalsIgnoreCase(Base64.getEncoder().encodeToString(CustomHeader.EMPTY_PAYLOAD.getBytes(StandardCharsets.UTF_8)))){
////
////            ResponseEntity<String> response = httpClient.doCall(urlCallBack + "/crs-res-command/message/getXml/" + messageUuid, HttpMethod.GET, String.class);
////
////            if (response.getBody() == null) {
////                throw new Exception("Response is null -->" + messageUuid);
////            }
////
////            xmlBase64Gzip = response.getBody();
////
////        }else{
////            xmlBase64Gzip= payload;
////        }
////
////
////        byte[] statusMessageXml = IOUtils.gUnzipper(Base64.getDecoder().decode(statusMessageXmlGzipBase64));
////        log.info("    method saveMessageAndGetMsgKeyIfCanSendCorrectableAndExcel -> status message is: " + new String(statusMessageXml, StandardCharsets.UTF_8));
////       CrsStatusMessageOECDWrapper statusMessageCRS = JaxbHelper.unmarshallAndGetStatusMessageOECDWrapper(statusMessageXml);
////
////        String xmlGzippedBase64FileName = "/tmp/crs-" + System.currentTimeMillis() + ".xml.gz.b64";
////        File xmlGzippedBase64File = new File(xmlGzippedBase64FileName);
////        FileOutputStream xmlGzippedBase64FileOutputStream = new FileOutputStream(xmlGzippedBase64File);
////        StreamUtils.copy(new ByteArrayInputStream(xmlBase64Gzip.getBytes(StandardCharsets.UTF_8)), xmlGzippedBase64FileOutputStream);
////        xmlGzippedBase64FileOutputStream.flush();
////        xmlGzippedBase64FileOutputStream.close();
////
////        log.info("   saved file " + xmlGzippedBase64FileName);
////
////        List<String> listBlockError = aeoiValidationRulesRepository.findByAeoiValRuleBlockIsTrue().stream().map(e -> String.valueOf(e.getAeoiValRuleCode())).collect(Collectors.toList());
////
////        // total account number
////        InputStream xmlInputStream = StreamUtils.getXmlInputStreamFromXmlGzipBase64(new FileInputStream(xmlGzippedBase64File));
////        MessageOperationUtils.MessageSpecValueObject messageSpecValueObject = MessageOperationUtils.getMessageSpecFromXmlStream(xmlInputStream);
////        xmlInputStream.close();
////        log.info("    messageSpecValueObject: " + messageSpecValueObject.toString());
////
////
////        //create message entity
////        QMessageEntity messageEntity = QMessageEntity.
////                buildEntity(
////                        messageUuid,
////                        messageStatus,
////                        transmittingCountry,
////                        mwMsgId,
////                        this,
////                        context,
////                        messageSpecValueObject,
////                        receivedOn,
////                        nAccountReport,
////                        statusMessageCRS);
////
////        //create statusMessage
////
////        StatusMessage statusMessageEntity = StatusMessage.buildEntity(messageUuid, statusMessageUuid, statusMessageXml, mwMsgId, statusMessageStatusRepository, isUploaded, listBlockError, nAccountReport);
////
////        if (!(MessageStatusEnum.XML_NOT_VALIDATED.equals(messageStatus) || MessageStatusEnum.FAILED_DECRYPTION.equals(messageStatus))) {
////            log.info("    message is readable, parsing it...");
////            //creating AND SAVE reporting FI
////            if (!qRfiRepository.existsByMessages_MsgUuid(messageUuid)) {
////
////                /* controllo, per ognuno dei body, se :
////                 * - la repEntity è nuova -> OECD1 -> (CRS701-NUOVI DATI) --> la creo
////                 * - altrimenti è presente per riferire i dati da integrare -> OECD0 -> (CRS701-INTEGRATIVA) --> NON la creo
////                 * - può essere presente o meno per i dati da correggere -> ( se c'è è OECD0) -> (CRS702-CORRETTIVA) --> NON la creo
////                 */
////                messageEntity.setReportingEntities(new HashSet<>());
////                int fy = Integer.parseInt(messageSpecValueObject.getReportingPeriod().substring(0, 4));
////
////                // check crsoecd version
////				if (messageSpecValueObject.getVersion().startsWith("2")) {
////					saveRfi_v2(xmlGzippedBase64File, messageEntity, fy);
////				} else {
////					saveRfi_v1(xmlGzippedBase64File, messageEntity, fy);
////				}
////			}
////        } else {
////        	// in case of completely unreadable message we use the gateway id as message identifier
////            if (messageEntity.getMessageRef() == null || messageEntity.getMessageRef().isEmpty()) {
////                messageEntity.setMessageRef("GTW_ID:" + messageGatewayId);
////            }
////        }
////
////        QMessageEntity messageEntityExist = messageRepository.findById(messageUuid).orElse(null);
////
////        if (messageEntityExist != null) {
////            QMessageEntity.buildEntityRework(messageEntityExist, messageStatus, transmittingCountry, mwMsgId, this, context, messageSpecValueObject, receivedOn, nAccountReport);
////            messageRepository.saveAndFlush(messageEntityExist);
////        } else {
////            //save message
////            messageRepository.saveAndFlush(messageEntity);
////        }
////
////        //save statusMessage
////        statusMessageRepository.save(statusMessageEntity);
////
////        boolean hasFileErrors = !statusMessageCRS.getCrsStatusMessage().getValidationErrors().getFileError().isEmpty();
////        if (statusMessageCRS.getCrsStatusMessage().getValidationErrors() != null) {
////            List<QValidationErrorsEntity> validationErrorsEntities = QValidationErrorsEntity.create(statusMessageCRS.getCrsStatusMessage().getValidationErrors(), statusMessageEntity, listBlockError);
////            validationErrorsRepository.saveAll(validationErrorsEntities);
////        }
////        //save message data
////        messageDataRepository.saveAndFlush(QMessageDataEntity.buildEntity(messageEntity.getMsgUuid(), Base64.getDecoder().decode(xmlBase64Gzip)));
////        log.info("    Message received inserted.");
////
////        //send event to populate Correctable elements only if the event is INCOMING_MESSAGE_BUSINESS_AND_SEND_STATUS_MESSAGE
////        if (needToPopulateCorrectable && statusMessageEntity.getBlockingErrorPercentage() == 0 && !hasFileErrors) {
////
////            int fy = Integer.parseInt(messageSpecValueObject.getReportingPeriod().substring(0, 4));
////            msgKey = transmittingCountry + "-" + fy;
////
////        }
////
////        return msgKey;
////    }
//
//	private void saveRfi_v1(File xmlGzippedBase64File, ReceiveMessage messageEntity, int fy) throws Exception,
//			FileNotFoundException, XMLStreamException, FactoryConfigurationError, JAXBException, IOException {
//		InputStream xmlInputStream;
//		jaxbReportingFI_v1 = new CrsJaxbHelper<>(oecd.ties.crsv1.crs.v1.CorrectableOrganisationPartyType.class);
//
//		xmlInputStream = StreamUtils
//				.getXmlInputStreamFromXmlGzipBase64(new FileInputStream(xmlGzippedBase64File));
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance()
//				.createXMLStreamReader(xmlInputStream);
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals("CrsBody")) {
//
//				log.info(" reading body...");
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				// until body ends...
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals("CrsBody"))) {
//					// get rfi
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals("ReportingFI")) {
//						log.info(" adding rfi...");
//						oecd.ties.crsv1.crs.v1.CorrectableOrganisationPartyType rfi = jaxbReportingFI_v1
//								.partialUnmarshal(xmlStreamReader);
//						if (!rfi.getDocSpec().getDocTypeIndic().equals(oecd.ties.crsv1.stf.v4.OECDDocTypeIndicEnumType.OECD_0)) {
//							QRfiEntity reportingFiEntity = QRfiEntity.create(rfi, fy);
//							qRfiRepository.saveAndFlush(reportingFiEntity);
//							messageEntity.getReportingEntities().add(reportingFiEntity);
//						}
//					}
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//
//			xmlStreamReader.next();
//		}
//		xmlInputStream.close();
//	}
//
//	private void saveRfi_v2(File xmlGzippedBase64File, ReceiveMessage messageEntity, int fy) throws Exception,
//			FileNotFoundException, XMLStreamException, FactoryConfigurationError, JAXBException, IOException {
//		InputStream xmlInputStream;
//		jaxbReportingFI = new CrsJaxbHelper<>(CorrectableOrganisationPartyType.class);
//
//		xmlInputStream = StreamUtils
//				.getXmlInputStreamFromXmlGzipBase64(new FileInputStream(xmlGzippedBase64File));
//		XMLStreamReader xmlStreamReader = XMLInputFactory.newInstance()
//				.createXMLStreamReader(xmlInputStream);
//		while (xmlStreamReader.hasNext()) {
//			int sc = xmlStreamReader.getEventType();
//			if (sc == XMLStreamConstants.START_ELEMENT
//					&& xmlStreamReader.getName().getLocalPart().equals("CrsBody")) {
//
//				log.info(" reading body...");
//				xmlStreamReader.next();
//				sc = xmlStreamReader.getEventType();
//				// until body ends...
//				while (!(sc == XMLStreamConstants.END_ELEMENT
//						&& xmlStreamReader.getName().getLocalPart().equals("CrsBody"))) {
//					// get rfi
//					if (sc == XMLStreamConstants.START_ELEMENT
//							&& xmlStreamReader.getName().getLocalPart().equals("ReportingFI")) {
//						log.info(" adding rfi...");
//						CorrectableOrganisationPartyType rfi = jaxbReportingFI
//								.partialUnmarshal(xmlStreamReader);
//						if (!rfi.getDocSpec().getDocTypeIndic().equals(OECDDocTypeIndicEnumType.OECD_0)) {
//							QRfiEntity reportingFiEntity = QRfiEntity.create(rfi, fy);
//							qRfiRepository.saveAndFlush(reportingFiEntity);
//							messageEntity.getReportingEntities().add(reportingFiEntity);
//						}
//					}
//					xmlStreamReader.next();
//					sc = xmlStreamReader.getEventType();
//				}
//			}
//
//			xmlStreamReader.next();
//		}
//		xmlInputStream.close();
//	}

    private static Date filterDateForSinottico(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (month == 11 && day == 31){
            return date;
        } else {
            calendar.clear();
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.YEAR, year - 1);
            return calendar.getTime();
        }
    }

}
