package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.FiscalYearRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiEventSender;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiSaveService;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.FiscalYearService;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.CheckCondition;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.DateUtil;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.IOUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.JsonOperation;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.DpiNationalNotifyBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.UpdateBean;
import it.finanze.entrate.coopint.dboard.dpi.common.config.TopicProperties;
import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class FiscalYearServiceImpl implements FiscalYearService {

    @Autowired
    @Qualifier("fiscalYearRepositoryNazionale")
    FiscalYearRepository fiscalYearRepository;
    @Autowired
    DpiEventSender dpiEventSender;
    @Autowired
    DpiSaveService dpiSaveService;


    // alle 03:00 di ogni giorno
    @Scheduled(cron = "0 0 3 * * *")
    public void checkFiscalYearExpired() {
        List<FiscalYear> listNotExpired = fiscalYearRepository.findByExpired(false);
        Date oggi = Date.from(
                LocalDate.now()
                        .minusDays(1)// perchè oggi non è passato ancora
                        .atTime(0,0,1) // perchè uso after senza l'uguale
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        for( FiscalYear fiscalYear : listNotExpired ){
            if( oggi.after(fiscalYear.getExpirationDate())){
                fiscalYear.setExpired(true);
                // TODO Perchè usare un servizio per il salvataggio di FiscalYear?
                dpiSaveService.saveFiscalYear(fiscalYear);
                long year = fiscalYear.getYear();
                log.info("Fiscal year [" + year + "] setted to EXPIRED");
                dpiEventSender.sendEventExpiredFiscalYear(year, TopicProperties.DPI_NAT_COMMAND);
            }
        }
    }


    @Override
    public boolean setExpiredFiscalYear(String payload, MessageHeaders messageHeaders) {

        AtomicBoolean fiscalYearAlreadyExpired = new AtomicBoolean(true);
        Long year = Long.parseLong((String) messageHeaders.get(KafkaHeaders.RECEIVED_MESSAGE_KEY));
        try {
            //setting expired fiscal year to EXPIRED
            fiscalYearRepository.findById(year).ifPresent(fiscalYearEntity -> {

                if (!fiscalYearEntity.isExpired()) {
                    fiscalYearEntity.setExpired(true);
                    dpiSaveService.saveFiscalYear(fiscalYearEntity);
                    log.info("Fiscal year [" + year + "] setted to EXPIRED");
                    dpiEventSender.sendEventExpiredFiscalYear(year, TopicProperties.DPI_NAT_EVENT);
                    fiscalYearAlreadyExpired.set(false);
                } else {
                    fiscalYearAlreadyExpired.set(true);
                    log.info("The fiscal year [" + year + "] is already expired. No messages construction will be start");
                }
            });
            return fiscalYearAlreadyExpired.get();
        } catch (Exception e) {
            log.info("Error during setting fiscal year expired", e);
            throw e;
        }
    }

    @Override
    public boolean checkIfCanUpdate(UpdateBean pwb) throws Exception {

        if (CheckCondition.isEmpty(pwb.getFyExpirationDate())
                || CheckCondition.isEmpty(pwb.getFyExtendedExpirationDate())) {
            return false;
        }

        FiscalYear fy = fiscalYearRepository.findByYear(pwb.getFiscalYear());

        return CheckCondition.dateRespectTheRules(pwb, fy);
    }

    @Override
    public void updateFiscalYear(String payloadB64, MessageHeaders messageHeaders) throws Exception {

        try {
            log.info("Decoding payload...");
            String payload = new String(IOUtils.convertBase64ToByteArray(payloadB64));
            UpdateBean pwb = JsonOperation.jsonToObject(payload, UpdateBean.class);

            FiscalYear fiscalYearEntity = fiscalYearRepository.findByYear(pwb.getFiscalYear());
            if (!CheckCondition.isEmpty(pwb.getFyExpirationDate())) {
                fiscalYearEntity.setExpirationDate(DateUtil.htmlDateStringToDate(pwb.getFyExpirationDate()));
            }
            if (!CheckCondition.isEmpty(pwb.getFyExtendedExpirationDate())) {
                fiscalYearEntity.setExtendedExpDate(DateUtil.htmlDateStringToDate(pwb.getFyExtendedExpirationDate()));
            }
            dpiSaveService.saveFiscalYear(fiscalYearEntity);

            //build payload to notify
            DpiNationalNotifyBean beanToNotify = DpiNationalNotifyBean.buildBeanForFiscalYear(fiscalYearEntity);

            dpiEventSender.sendEventUpdateFiscalYear(pwb, TopicProperties.DPI_NAT_EVENT, JsonOperation.objectToJson(beanToNotify));

        } catch (Exception e) {
            log.error("Error during update fiscal year's date", e);
            throw e;
        }
    }

}
