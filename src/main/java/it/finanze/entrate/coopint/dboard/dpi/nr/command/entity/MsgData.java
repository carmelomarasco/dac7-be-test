package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MSG_DATA", schema = "DBOARD_DPI_NR")
public class MsgData {
    @Id
    @Size (max = 57)
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @NotNull
    @Column (name = "MSG_BLOB", nullable = false)
    private byte[] msgBlob;

}