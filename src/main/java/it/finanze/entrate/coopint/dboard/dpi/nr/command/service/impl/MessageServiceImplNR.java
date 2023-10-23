package it.finanze.entrate.coopint.dboard.dpi.nr.command.service.impl;

import com.google.common.collect.Lists;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.*;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.StatusMessageIn;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.MessageOutLazy;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.enumeration.MsgStatusEnum;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.excel.InterrogationResultExcelNR;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy.MessageOutLazyRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy.NewDataBuildingCountryLazyRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.lazy.NewDataBuildingReportLazyRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.service.Dac7NRRestTemplateService;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.service.MessageService;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.utils.MessageNRUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.ParamUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
@CommonsLog
public class MessageServiceImplNR implements MessageService {

    @Autowired
    MessageOutRepository messageOutRepository;
    @Autowired
    MessageOutLazyRepository messageOutLazyRepository;
    @Autowired
    MsgStatusRepository msgStatusRepository;
    @Autowired
    CommunicationRepository communicationRepository;
    @Autowired
    MessageOutCodeRepository messageOutCodeRepository;
//    @Autowired
//    QCommunicationRepositoryLazy communicationRepositoryLazy;
    @Autowired
    NewDataBuildingReportRepository buildingReportRepository;
    @Autowired
    NewDataBuildingReportLazyRepository buildingReportLazyRepository;
    @Autowired
    NewDataBuildingCountryRepository buildingCountriesRepository;
    @Autowired
    NewDataBuildingCountryLazyRepository buildingCountryLazyRepository;
    @Autowired
    InterrogationResultExcelNR interrogationResultExcel;
    @Autowired
    StatusMessageInRepository statusMessageInRepository;
    @Autowired
    Dac7NRRestTemplateService restTemplateService;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    MessageTypeIndicRepository messageTypeIndicRepository;
    @Autowired
    MsgClassRepository msgClassRepository;
    @Autowired
    NewDataBuildingReportRepository newDataBuildingReportRepository;
    @Autowired
    FiscalYearRepository fisYearRepo;

    @Override
    public List<MessageBean> findMessageBySearchForm(MessageSearchBean searchBean) {
            List<MessageOutLazy> listMessageEntity = messageOutLazyRepository.findAll((Specification<MessageOutLazy>) (root, criteriaQuery, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                /**msgUuid*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getMsgUuid())) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("id").get("msgUuid")), "%" + searchBean.getMsgUuid().toUpperCase() + "%")));
                }
                /**msgRef*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getMsgRef())) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(criteriaBuilder.upper(root.get("messageRef")), searchBean.getMsgRef().toUpperCase()+ "%")));
                }

                /**data trasmissione da*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getStartDate())) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("sendDate"), DateUtils.toTimestampFromHTMLStartDay(searchBean.getStartDate()).toInstant())));
                }
                /**data trasmissione a*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getEndDate())) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("sendDate"), DateUtils.toTimestampFromHTMLEndDay(searchBean.getEndDate()).toInstant())));
                }
                /**anno fiscale*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getYear())) {
                	
                	List<FiscalYear> listY=new ArrayList<FiscalYear>();
                	for(String ys:searchBean.getYear()){
                		Long l=new Long(ys) ;
                		listY.add(fisYearRepo.findByYear(l));
                	}
                	
                    predicates.add(root.get("fiscalYear").in(listY));
                }
                /**TODO: TIN PO fare su out_codes copiare da nazionale CommunicationCodesRepository*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getPoTin())) {
                    List<String> listMsgUuid = messageOutCodeRepository.getUuidMessageByPlatformOperatorTin("%" + searchBean.getPoTin() + "%");
                    List<Predicate> predicatesTin = new ArrayList<>();
                    if (listMsgUuid.isEmpty()) {
                        listMsgUuid.add("");
                    }
                    List<List<String>> listToAdd = Lists.partition(listMsgUuid, 900);
                    if(!listToAdd.isEmpty()){
                    	listToAdd.forEach(partialList -> {
                    		predicatesTin.add(root.get("id").get("msgUuid").in(partialList));
                    	});
                    }
                    if(!predicatesTin.isEmpty()){
                    	predicates.add(criteriaBuilder.or(predicatesTin.toArray(new Predicate[0])));
                    }
                }
                /**TODO: Name PO fare su out_codes copiare da nazionale CommunicationCodesRepository*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getPoName())) {
                    List<String> listMsgUuid = messageOutCodeRepository.getUuidMessageByPlatformOperatorName("%" + searchBean.getPoName() + "%");
                    List<Predicate> predicatesRfiName = new ArrayList<>();
                    if (listMsgUuid.isEmpty()) {
                        listMsgUuid.add("");
                    }
                    List<List<String>> listToAdd = Lists.partition(listMsgUuid, 900);
                    if(!listToAdd.isEmpty()){
                    	listToAdd.forEach(partialList -> {
                    		predicatesRfiName.add(root.get("id").get("msgUuid").in(partialList));
                    	});
                    }
                    if(!predicatesRfiName.isEmpty()){
                    	predicates.add(criteriaBuilder.or(predicatesRfiName.toArray(new Predicate[0])));
                    }
                }
                /**tipologia*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getType())) {
                    predicates.add(root.get("messageTypeIndic").get("id").in(searchBean.getType()));
                }
                /**Stato*/
                if (!ParamUtils.isNullOrEmpty(searchBean.getStatus())) {
                    predicates.add(root.get("status").get("id").in(searchBean.getStatus()));
                }
//                /**Contesto*/
//                if (!ParamUtils.isNullOrEmpty(searchBean.getContext())) {
//                    predicates.add(root.get("context").in(searchBean.getContext()));
//                }
                /**Paesi */
                if (!ParamUtils.isNullOrEmpty(searchBean.getCountry())) {
                    List<String> listMsgUuid = messageOutRepository.getUuidMessageByListCountryCode(searchBean.getCountry());
                    List<Predicate> predicatesCountry = new ArrayList<>();

                    if (listMsgUuid.isEmpty()) {
                        listMsgUuid.add("");
                    }
                    List<List<String>> listToAdd = Lists.partition(listMsgUuid, 900);
                    listToAdd.forEach(partialList -> {
                        predicatesCountry.add(root.get("id").get("msgUuid").in(partialList));
                    });
                    predicates.add(criteriaBuilder.or(predicatesCountry.toArray(new Predicate[0])));

                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });

            return MessageBean.getListFromLazyEntity(listMessageEntity,  statusMessageInRepository);
        }

/*
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveMessage(MessageCommunicationBean messageCommunicationBean) {

        QNewDataBuildingReportEntity buildingReport = newDataBuildingReportRepository.findFirstByFiscalYear(messageCommunicationBean.getMessageDto().getFiscalYear());
        byte[] xmlGzip = it.finanze.entrate.coopint.crs.nonresidenti.view.utility.IOUtils.convertBase64ToByteArray(messageCommunicationBean.getXmlGzipBase64());

        if (buildingReport != null) {
            messageCommunicationBean.getMessageDto().setBuildingReportId(buildingReport.getBuildingReportId());
        }
        QCountryEntity country = countryRepository.findByCountryCode(messageCommunicationBean.getMessageDto().getCountryCode());
        QMessageTypeIndicEntity messageType = messageTypeIndicRepository.findByMtiDescription(messageCommunicationBean.getMessageDto().getMessageTypeIndic());
        QMsgClassEntity messageClass = msgClassRepository.findByMsgClassDescription(messageCommunicationBean.getMessageDto().getMsgClass());
        QMessageStatusEntity messageStatus = messageStatusRepository.findByStatus(messageCommunicationBean.getMessageDto().getStatus());
        QMessageEntity message = QMessageEntity.create(messageCommunicationBean.getMessageDto(), country.getCountryId(), messageType.getMessageTypeIndicId(), messageClass.getMsgClassId(), messageStatus.getStatusId());
        log.info(messageCommunicationBean.getMessageDto().getCountryCode());


        message.setQCommunicationsList(messageCommunicationBean.getMessageDto().getQCommunicationsList().stream().map(QCommunicationEntity::create).collect(Collectors.toList()));

        message.setTotalRfi(messageCommunicationBean.getMessageDto().getTotalRfi());
        message.setTotalAccount(messageCommunicationBean.getMessageDto().getTotalAccount());
        message.setBuildEndDate(new Date());

        QMsgDataEntity msgData = QMsgDataEntity.builder()
                .msgUuid(message.getMsgUuid())
                .msgBlob(xmlGzip)
                .build();

        message.setQMsgDataEntity(msgData);

        messageOutRepository.saveAndFlush(message);
        log.info("messaggio salvato con uuid--->" + message.getMsgUuid());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void saveResendMessage(MessageDto messageDto, byte[] xmlGzip) {

        QCountryEntity country = countryRepository.findByCountryCode(messageDto.getCountryCode());
        QMessageTypeIndicEntity messageType = messageTypeIndicRepository.findByMtiDescription(messageDto.getMessageTypeIndic());
        QMsgClassEntity messageClass = msgClassRepository.findByMsgClassDescription(messageDto.getMsgClass());
        QMessageStatusEntity messageStatus = messageStatusRepository.findByStatus(messageDto.getStatus());
        QMessageEntity message = QMessageEntity.create(messageDto, country.getCountryId(), messageType.getMessageTypeIndicId(), messageClass.getMsgClassId(), messageStatus.getStatusId());

        message.setQCommunicationsList(messageDto.getQCommunicationsList().stream().map(QCommunicationEntity::create).collect(Collectors.toList()));
        message.setTotalRfi(messageDto.getTotalRfi());
        message.setTotalAccount(messageDto.getTotalAccount());
        message.setBuildEndDate(new Date());
        QMsgDataEntity msgData = QMsgDataEntity.builder()
                .msgUuid(message.getMsgUuid())
                .msgBlob(xmlGzip)
                .build();
        message.setQMsgDataEntity(msgData);
        messageOutRepository.saveAndFlush(message);
        log.info("messaggio salvato con uuid--->" + message.getMsgUuid());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void updateStatus(UpdateStatusBean updateStatusBean) {

        QMessageStatusEntity status = messageStatusRepository.findByStatus(updateStatusBean.getStatusTo());
        QMessageEntity msgEntity = messageOutRepository.findQMessageEntityByMsgUuid(updateStatusBean.getMessageId());

        msgEntity.setStatusId(status.getStatusId());

        messageOutRepository.saveAndFlush(msgEntity);
        log.info("Updated status for messages " + updateStatusBean.getMessageId() + " to " + status.getStatus());
    }
*/
    @Override
    public List<MessageBean> searchForSinottico(MessageSearchBean searchBean) throws Exception {
        List<MessageBean> result = new ArrayList<>();

        Date startDate = DateUtils.getDateFromString(searchBean.getStartDateSinottico(), DateUtils.PATTERN_ITA_WITHOUT_HOUR);
        Date endDate = filterDateForSinottico(DateUtils.getDateFromString(searchBean.getEndDateSinottico(), DateUtils.PATTERN_ITA_WITHOUT_HOUR));

        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.setTime(startDate);
        Integer startYear = calendarStartDate.get(Calendar.YEAR);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.setTime(endDate);
        Integer endYear = calendarEndDate.get(Calendar.YEAR);

        List<String> listStatus = new ArrayList<>();

        if ("EXCHANGE_NOT_CLOSED".equals(searchBean.getTypeSearchParam())) {
            listStatus.addAll(Arrays.asList(MsgStatusEnum.DELIVERED.getValue(), MsgStatusEnum.EXPIRED.getValue(), MsgStatusEnum.TRANSMITTED.getValue()));
            result = MessageBean.getListFromLazyEntity(messageOutLazyRepository.getListMsgByStatus(listStatus), statusMessageInRepository);
        }

        if ("PRONTI_PER_INVIO".equals(searchBean.getTypeSearchParam())) {
            listStatus.add(MsgStatusEnum.PRONTO_PER_INVIO.getValue());
            result = MessageBean.getListFromLazyEntity(
                    messageOutLazyRepository.getListMsgByDateFromToAndStatus(startYear, endYear, listStatus),
                statusMessageInRepository);
        }

        if ("SCARTATI".equals(searchBean.getTypeSearchParam())) {
            listStatus.add(MsgStatusEnum.REJECTED.getValue());
            result = MessageBean.getListFromLazyEntity(
                    messageOutLazyRepository.getListMsgByDateFromToAndStatus(startYear, endYear, listStatus),
                    statusMessageInRepository);
        }

        if ("ERRATI".equals(searchBean.getTypeSearchParam())) {
            result = MessageBean.getListFromLazyEntity(
                    messageOutLazyRepository.getListMsgByDateFromToWithError(startDate, endDate),
                    statusMessageInRepository);
        }

        if ("TARDIVI".equals(searchBean.getTypeSearchParam())) {
            listStatus.add(MsgStatusEnum.DOWNLOADED.getValue());
            List<MessageOutLazy> listDownloaded = messageOutLazyRepository.getListMsgByDateFromToAndStatus(startYear, endYear, listStatus);
            for (MessageOutLazy m : listDownloaded) {
                Optional<StatusMessageIn> opSm = statusMessageInRepository.findFirstStatusMessageInByMsgUuidOrderByDataInsDesc(m.getId().getMsgUuid());
                if (!opSm.isPresent()) {

                    Date downloadDate = restTemplateService.getDownloadedMessageDate(m.getId().getMsgUuid());

                    long dayPassed = DateUtils.getDaysPassed(downloadDate);
                    if (dayPassed > 15) {
                        result.addAll(MessageBean.getListFromLazyEntity(Lists.newArrayList(m),statusMessageInRepository));
                    }
                }
            }
        }

        return result;
    }

    @Override
    public List<MessageLogBean> getAllMessageLog() {
        return MessageLogBean.getListFromEntity(buildingReportLazyRepository.findAll());
    }

    @Override
    public List<CommunicationBean> getComUuidForMessage(String msgUuid) {
        return CommunicationBean.getListFromEntity(communicationRepository.findCommunicationById_MsgUuidOrderByPlatformOperatorName(msgUuid));
    }

    @Override
    public List<CountryDestBean> getCountriesForMessageLogDetail(MessageLogBean messageLog) {
        List<CountryDestBean> listResult = CountryDestBean.getListFromEntity(buildingCountryLazyRepository.findCountiesConstructionByReportIdOrderByDescription(messageLog.getReportId()), messageLog.getFiscalYear());
        listResult.forEach(e -> {
            Integer totalPo = messageOutRepository.getTotalPo("%" + e.getReportId() + "%", e.getCountry());
            e.setTotalPOCount(String.valueOf(totalPo == null ? 0 : totalPo));
        });
        return listResult;
    }

    @Override
    public List<MessageBean> getMessageBeanForCountryDetail(CountryDestBean countryDestBean) {
        return MessageBean.getListFromLazyEntity(
                messageOutLazyRepository.findListMessageForCountryDetail("%" + countryDestBean.getReportId() + "%", countryDestBean.getCountry()),
                statusMessageInRepository);
    }

    @Override
    public byte[] getTableExcel(MessageSearchBean searchFormBean) throws Exception {
        List<MessageBean> communicationBean;
        if (MessageNRUtils.checkFromSinottico(searchFormBean)) {
            communicationBean = searchForSinottico(searchFormBean);
        } else {
            communicationBean = findMessageBySearchForm(searchFormBean);
        }
        return interrogationResultExcel.build(communicationBean, searchFormBean);

    }

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
