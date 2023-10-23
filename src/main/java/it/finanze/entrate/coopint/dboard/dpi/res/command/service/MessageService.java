package it.finanze.entrate.coopint.dboard.dpi.res.command.service;


import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.ExcelXmlBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.MessageBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.MessageForm;
import it.finanze.entrate.coopint.dboard.dpi.res.command.enumeration.MessageStatusEnum;

import java.util.Date;
import java.util.List;

public interface MessageService {
    List<MessageBean> findMessageBySearchForm(MessageForm messageForm);

    List<MessageBean> searchForSinottico(MessageForm messageForm) throws Exception;

    byte[] getTableExcel(MessageForm messageForm) throws Exception;

    //void insertExcelFromExcelService(ExcelXmlBean bean);

    boolean checkIfMsgIsAccepted(String msgUUid);

    //String saveMessageAndGetMsgKeyIfCanSendCorrectableAndExcel(String messageUuid, String payload, String transmittingCountry, String statusMessageXmlGzipBase64, String statusMessageUuid, String mwMsgId, MessageStatusEnum messageStatus, String context, boolean isUploaded, boolean isRework, String messageGatewayId, Date receivedOn, Long nAccountReport, boolean needToPopulateCorrectable, String urlCallBack) throws Exception;
}
