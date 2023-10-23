package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import it.finanze.entrate.coopint.dboard.dpi.com.command.dto.MessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCommunicationBean {

    String xmlGzipBase64;
    MessageDto messageDto;

}
