package it.finanze.entrate.coopint.dboard.dpi.nr.command.service;


import java.util.Date;

public interface Dac7NRRestTemplateService {

    Date getDownloadedMessageDate(String msgUuid);

}
