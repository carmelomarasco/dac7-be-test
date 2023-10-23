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
@Table (name = "RECEIVE_MESSAGE_TYPE_INDIC", schema = "DBOARD_DPI_R")
public class ReceiveMessageTypeIndic {
    @Id
    @Column (name = "MESSAGE_TYPE_INDIC_ID", nullable = false)
    private Short id;

    @Size (max = 255)
    @Column (name = "MTI_DESCRIPTION")
    private String mtiDescription;

}