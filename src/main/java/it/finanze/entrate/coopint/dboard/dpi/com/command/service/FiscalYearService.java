package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

import org.springframework.messaging.MessageHeaders;

import it.finanze.entrate.coopint.dboard.dpi.common.bean.UpdateBean;

public interface FiscalYearService {

    boolean setExpiredFiscalYear(String payload, MessageHeaders messageHeaders) throws Exception;

    boolean checkIfCanUpdate(UpdateBean pwb) throws Exception;

    void updateFiscalYear(String payload, MessageHeaders messageHeaders) throws Exception;

}
