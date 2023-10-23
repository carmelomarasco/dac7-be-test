package it.finanze.entrate.coopint.dboard.dpi.nr.command.service;


import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.*;

import java.text.ParseException;
import java.util.List;

public interface MessageService {

    List<MessageBean> findMessageBySearchForm(MessageSearchBean searchBean) throws ParseException;

    List<MessageBean> searchForSinottico(MessageSearchBean searchBean) throws Exception;

    List<MessageLogBean> getAllMessageLog();

    List<CommunicationBean> getComUuidForMessage(String msgUuid);

    List<CountryDestBean> getCountriesForMessageLogDetail(MessageLogBean messageLog);

    List<MessageBean> getMessageBeanForCountryDetail(CountryDestBean countryDestBean);

    byte[] getTableExcel(MessageSearchBean messageSearch) throws Exception;

    //void saveMessage(MessageCommunicationBean messageCommunicationBean);

    //void updateStatus(UpdateStatusBean updateStatusBean);

    //void saveResendMessage(MessageDto messageDto, byte[] xmlGzip);
}
