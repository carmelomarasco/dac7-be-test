package it.finanze.entrate.coopint.dboard.dpi.com.command.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CommunicationDto {

    private String comUuid;

    private String msgUuid;

    private String messageRef;

    private Long totalAccountForCountry;

    private String rfiName;

    private String rfiTin;

    private String rfiRole;

}
