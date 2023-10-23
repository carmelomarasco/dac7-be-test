package it.finanze.entrate.coopint.dboard.dpi.res.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "MSG_DATA", schema = "DBOARD_DPI_R")
public class MsgData {
    @Id
    @Size (max = 57)
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @Column (name = "MSG_BLOB")
    private byte[] msgBlob;

}