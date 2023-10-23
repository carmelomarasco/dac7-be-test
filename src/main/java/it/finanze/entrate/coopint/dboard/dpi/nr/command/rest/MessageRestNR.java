package it.finanze.entrate.coopint.dboard.dpi.nr.command.rest;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.*;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.MsgData;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.MsgExcel;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.exceptions.Dac7NRException;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.exceptions.message.MessageError;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.service.MessageService;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
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
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@CommonsLog
@RequestMapping("/dac7-nr-view")
public class MessageRestNR {

    @Autowired
    MessageService messageService;
    @Autowired
    MsgDataRepository msgDataRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    MsgExcelRepository excelRepository;
//    @Autowired
//    CrsNrViewService crsNrViewService;
    @Autowired
    StatusMessageInRepository statusMessageInRepository;
    @Autowired
    MessageOutRepository messageRepository;
//    @Autowired
//    QMessageLazyRepository messageLazyRepository;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/search-message")
    public ResponseEntity<List<MessageBean>> searchMessage(MessageSearchBean searchBean) throws ParseException {
        log.info("Init method - searchMessage");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        if (!MessageSearchBean.isValid(searchBean) && !MessageSearchBean.isScambiNonConclusi(searchBean)) {
            log.error("Not acceptable request ---> empty search params");
            throw new Dac7NRException(MessageError.notValidRequest());
        }
        return ResponseEntity.ok(messageService.findMessageBySearchForm(searchBean));
    }
/*
    @GetMapping("/sinottico/search-message")
    public ResponseEntity<List<MessageBean>> searchSinotticoMessage(MessageSearchBean searchBean) throws Exception {
        log.info("Init method - searchSinotticoMessage");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_CRS_Invio.getValue(),
                Role.EUE_DAC2_Invio.getValue(),
                Role.EUE_CRS_Consultazione.getValue(),
                Role.EUE_DAC2_Consultazione.getValue());

        if (!MessageSearchBean.isValidSinottico(searchBean) ) {
            log.error("Not acceptable request ---> empty search params");
            throw new CrsNRViewException(MessageError.notValidRequest());
        }
        return ResponseEntity.ok(messageService.searchForSinottico(searchBean));
    }
*/

    @GetMapping("/message-log")
    public ResponseEntity<List<MessageLogBean>> getAllMessageLog() {
        log.info("Init method - getAllMessageLog");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(messageService.getAllMessageLog());
    }

    @GetMapping("/communication")
    public ResponseEntity<List<CommunicationBean>> getComUuid(String uuid) {
        log.info("Init method - getComUuid");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(messageService.getComUuidForMessage(uuid));
    }

    @GetMapping("/message-log-detail")
    public ResponseEntity<List<CountryDestBean>> getMessageLogDetail(MessageLogBean messageLog) {
        log.info("Init method - getMessageLogDetail");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(messageService.getCountriesForMessageLogDetail(messageLog));
    }

    @GetMapping("/country-detail")
    public ResponseEntity<List<MessageBean>> getCountryDetail(CountryDestBean country) {
        log.info("Init method - getCountryDetail");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(messageService.getMessageBeanForCountryDetail(country));
    }

    @GetMapping("/status-message")
    public ResponseEntity<StatusMessageInWebBean> getStatusMessage(String uuid) throws Exception {
        log.info("Init method - getStatusMessage");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(StatusMessageInWebBean.getFromEntity(statusMessageInRepository.findFirstByMsgUuid(uuid)));
    }

    @GetMapping(value = "/downloadXml")
    public ResponseEntity downloadXml(String uuid) throws IOException {
        log.info("Init method - downloadXml");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        String msgRef = messageRepository.getMsgRefByMsgUuid(uuid);
        MsgData msgDataEntity = msgDataRepository.findByMsgUuid(uuid);
        byte[] xmlGzip = msgDataEntity.getMsgBlob();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=MESSAGGIO_DAC7_NR" + msgRef + ".xml.gz")
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

        MsgExcel excelEntity = excelRepository.findByMsgUuid(uuid);
        byte[] excelGzip = excelEntity.getMsgBlob();
        String msgRef = messageRepository.getMsgRefByMsgUuid(uuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=MESSAGGIO_DAC7_NR_" + msgRef + ".xlsx.gz")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayResource(excelGzip));

    }

    @GetMapping(value = "/tableExcel")
    public ResponseEntity downloadTableExcel(MessageSearchBean searchBean) throws Exception {
        log.info("Init method - downloadTableExcel");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        byte[] excel = messageService.getTableExcel(searchBean);
        byte[] zip = it.finanze.entrate.coopint.xml.utils.IOUtils.zip(excel, "MESSAGGI_DAC7_NR.xlsx");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "EXCEL_MESSAGGI_DAC7_NR_" + DateUtils.convertDateToString(new Date()).replace(" ", "_") + ".zip")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new ByteArrayResource(zip));

    }

    /**
     * EXCEL SERVICE
     */
    /*
    @GetMapping(value = "/getXml/{messageUuid}")
    public ResponseEntity getXmlForExcelService(@PathVariable(value = "messageUuid") String messageUuid) {

        log.info("messageUuid ricevuto ---> " + messageUuid);
        QMsgDataEntity msgDataEntity = msgDataRepository.findByMsgUuid(messageUuid);
        List<ValueDescriptionBean> listCountries = ValueDescriptionBean.getListFromCountry(countryRepository.findAll());
        ExcelXmlBean excelXmlBean = new ExcelXmlBean();
        excelXmlBean.setCountries(listCountries);
        excelXmlBean.setGZipXml(msgDataEntity.getMsgBlob());
        String msgRef = messageRepository.getMsgRefByMsgUuid(messageUuid);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "XML_Messaggio_CRS_NR_" + msgRef + "_" + DateUtils.convertDateToString(new Date()).replace(" ", "_") + ".gzip")
                .contentType(MediaType.parseMediaType("application/xml"))
                .body(JsonOperation.objectToJson(excelXmlBean));
    }
*/
    /**
     * EXCEL SERVICE
     */
    /*
    @PostMapping(value = "/insertExcel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> insertExcel(@RequestBody ExcelXmlBean bean) {
        log.info("Init method insertExcel");
        crsNrViewService.insertExcelFromExcelService(bean);
        return ResponseEntity.ok(true);
    }
*/


    /**
     * EndPoint for ExchangeService
     * EUROPEAN COMMISSION
     */
    /*
    @GetMapping(value = "/getXmlGzipBase64/{messageUuid}")
    public ResponseEntity<String> getXmlGzip(@PathVariable(value = "messageUuid") String messageUuid) {

        log.info("receive message uuid ---> " + messageUuid);
        QMsgDataEntity message = msgDataRepository.findByMsgUuid(messageUuid);
        String xmlBase64 = IOUtils.convertByteArrayToBase64(message.getMsgBlob());
        return ResponseEntity.ok(xmlBase64);
    }
*/
    /**
     * SINOTTICO
     */
/*
    @GetMapping(value = "/msgNotConcluded")
    public ResponseEntity<Integer> countMessageNotConcluded() {
        log.info("Init method countMessageNotConcluded");
        List<String> listStatus = Arrays.asList(MsgStatus.DELIVERED.getValue(), MsgStatus.EXPIRED.getValue(), MsgStatus.TRANSMITTED.getValue());
        return ResponseEntity.ok(messageLazyRepository.countMsgByStatus(listStatus));
    }
*/
}
