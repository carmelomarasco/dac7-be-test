package it.finanze.entrate.coopint.dboard.dpi.res.command.rest;

import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.*;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.Excel;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.MsgData;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.StatusMessageStatus;
import it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration.StatusMessageStatusEnum;
import it.finanze.entrate.coopint.dboard.dpi.res.command.exception.Dac7ResException;
import it.finanze.entrate.coopint.dboard.dpi.res.command.exception.message.MessageError;
import it.finanze.entrate.coopint.dboard.dpi.res.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.res.command.service.MessageService;
import it.finanze.entrate.coopint.dboard.dpi.res.command.utils.DateUtil;
import it.finanze.entrate.coopint.dboard.dpi.utils.JsonOperation;
import it.finanze.security.enumeration.Role;
import it.finanze.security.util.UserUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CommonsLog
@RequestMapping("/dac7-res-view/message")
public class MessageRestRes {

    @Autowired
    MessageService messageService;
    @Autowired
    MsgDataRepository messageDataRepository;
    @Autowired
    ExcelRepository excelRepository;
    @Autowired
    DboardDpiCountryRepository countryRepository;
    @Autowired
    StatusMessageRepository statusMessageRepository;
    @Autowired
    ReceiveMessageRepository messageRepository;
    @Autowired
    ReceiveMessageLazyRepository messageLazyRepository;
    @Autowired
    StatusMessageStatusRepository statusMessageStatusRepository;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/search")
    public ResponseEntity<List<MessageBean>> searchMessage(MessageForm messageForm) {
        log.info("Init method - searchMessage");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        if (!MessageForm.isValid(messageForm) && !MessageForm.isScambiNonConclusi(messageForm)) {
            log.error("Not acceptable request ---> empty search params");
            throw new Dac7ResException(MessageError.notValidRequest());
        }
        return ResponseEntity.ok(messageService.findMessageBySearchForm(messageForm));
    }

    @GetMapping("/sinottico/search")
    public ResponseEntity<List<MessageBean>> searchSinotticoMessage(MessageForm messageForm) throws Exception {
        log.info("Init method - searchSinotticoMessage");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        if (!MessageForm.isValidSinottico(messageForm)) {
            log.error("Not acceptable request ---> empty search params");
            throw new Dac7ResException(MessageError.notValidRequest());
        }
        return ResponseEntity.ok(messageService.searchForSinottico(messageForm));
    }

    @GetMapping("/listStatusMsg")
    public ResponseEntity<List<StatusMessageBean>> getListStatusMessage(String msgUuid) {
        log.info("Init method - getListStatusMessage");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(StatusMessageBean.listFromEntities(statusMessageRepository.findStatusMessagesByMsgUuidOrderById_smChangeDate(msgUuid)));
    }

    @GetMapping(value = "/tableExcel")
    public ResponseEntity downloadTableExcel(MessageForm messageForm) throws Exception {
        log.info("Init method - downloadTableExcel");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        byte[] excel = messageService.getTableExcel(messageForm);
        byte[] zip = it.finanze.entrate.coopint.xml.utils.IOUtils.zip(excel, "MESSAGGI_CRS_RES.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "EXCEL_MESSAGES_CRS_RES_" + DateUtil.convertDateToString(new Date()).replace(" ", "_") + ".zip")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayResource(zip));

    }

    @GetMapping(value = "/downloadXml")
    public ResponseEntity downloadXml(String uuid) throws IOException {
        log.info("Init method - downloadXml");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        String msgRef = messageRepository.getMsgRefByMsgUuid(uuid);
        MsgData msgDataEntity = messageDataRepository.findByMsgUuid(uuid);
        byte[] xmlGzip = msgDataEntity.getMsgBlob();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=MESSAGGIO_CRS_RES_" + msgRef + ".xml.gz")
                .contentType(MediaType.parseMediaType("application/xml"))
                .body(new ByteArrayResource(xmlGzip));

    }

    @GetMapping(value = "/downloadExcel")
    public ResponseEntity downloadExcel(String uuid) throws IOException {
        log.info("Init method - downloadExcel");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        if (!messageService.checkIfMsgIsAccepted(uuid)) {
            return ResponseEntity.status(406).build();
        }

        Excel excelEntity = excelRepository.findByMsgUuid(uuid);
        String msgRef = messageRepository.getMsgRefByMsgUuid(uuid);
        byte[] excelGzip = excelEntity.getMsgBlob();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=MESSAGGIO_CRS_RES_" + msgRef + ".xlsx.gz")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayResource(excelGzip));

    }

    /**
     * EXCEL SERVICE
     */
    @GetMapping(value = "/getXml/{messageUuid}")
    public ResponseEntity getXmlForExcelService(@PathVariable(value = "messageUuid") String messageUuid) {

        log.info("messageUuid ricevuto ---> " + messageUuid);
        MsgData msgDataEntity = messageDataRepository.findByMsgUuid(messageUuid);
        List<ValueDescriptionBean> listCountries = ValueDescriptionBean.listFromCountries(countryRepository.getAllOrderByDesc());
        ExcelXmlBean excelXmlBean = new ExcelXmlBean();
        excelXmlBean.setCountries(listCountries);
        excelXmlBean.setGZipXml(msgDataEntity.getMsgBlob());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "xml" + DateUtil.convertDateToString(new Date()).replace(" ", "_") + ".gzip")
                .contentType(MediaType.parseMediaType("application/xml"))
                .body(JsonOperation.objectToJson(excelXmlBean));
    }


    /**
     * EXCEL SERVICE
     */
//    @PostMapping(value = "/insertExcel", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Boolean> insertExcel(@RequestBody ExcelXmlBean bean) {
//        log.info("Init method insertExcel");
//        messageService.insertExcelFromExcelService(bean);
//        return ResponseEntity.ok(true);
//    }

    /**
     * SINOTTICO
     */
    @GetMapping(value = "/msgNotConcluded")
    public ResponseEntity<Integer> countMessageNotConcluded() {
        log.info("Init method countMessageNotConcluded");
        List<String> listStatus = Arrays.asList(StatusMessageStatusEnum.CREATED.getValue(), StatusMessageStatusEnum.SUSPENDED.getValue(), StatusMessageStatusEnum.TRANSMITTED.getValue());
        List<Short> listStatusId = statusMessageStatusRepository.findByStatusIn(listStatus).stream().map(StatusMessageStatus::getId).collect(Collectors.toList());
        return ResponseEntity.ok(messageLazyRepository.countMessageByStatusMessageStatusId(listStatusId));
    }
}

