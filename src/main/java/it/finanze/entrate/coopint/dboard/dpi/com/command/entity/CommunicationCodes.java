package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "COMMUNICATION_CODES", schema = "DBOARD_DPI_NATIONAL")
public class CommunicationCodes {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "COMMUNICATION_CODES_SEQ")
    @SequenceGenerator (name = "COMMUNICATION_CODES_SEQ", sequenceName = "COMMUNICATION_CODES_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "CODES_ID", nullable = false)
    private Long id;

    @Size (max = 57)
    @NotNull
    @Column (name = "COM_UUID", nullable = false, length = 57)
    private String comUuid;

    @Size (max = 3)
    @NotNull
    @Column (name = "TIPO", nullable = false, length = 3)
    // PLATFORM_OPERATOR o OTHER_PLATFORM_OPERATOR
    private String type;

    @Size (max = 4000)
    @Column (name = "TIN", length = 4000)
    private String tin;

    @Size (max = 4000)
    @Column (name = "IDENTIFICATION_NUMBER", length = 4000)
    private String in;

    @Size (max = 4000)
    @Column (name = "DENOMINAZIONE", length = 4000)
    private String denominazione;

}