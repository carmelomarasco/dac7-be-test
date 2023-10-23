package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MESSAGE_OUT_CODES", schema = "DBOARD_DPI_NR")
public class MessageOutCode {
    @Id
    @NotNull
    @Column (name = "CODES_ID", nullable = false)
    private Long codesId;

    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @Size (max = 3)
    @NotNull
    @Column (name = "TIPO", nullable = false, length = 3)
    private String tipo;

    @Size (max = 4000)
    @Column (name = "TIN", length = 4000)
    private String tin;

    @Size (max = 4000)
    @Column (name = "IDENTIFICATION_NUMBER", length = 4000)
    private String identificationNumber;

    @Size (max = 4000)
    @Column (name = "DENOMINAZIONE", length = 4000)
    private String denominazione;

}