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
@Table (name = "RECEIVE_MESSAGE_STATUS", schema = "DBOARD_DPI_R")
public class ReceiveMessageStatus {
    @Id
    @Column (name = "STATUS_ID", nullable = false)
    private Short id;

    @Size (max = 255)
    @NotNull
    @Column (name = "STATUS_DESCRIPTION", nullable = false)
    private String statusDescription;

    @Size (max = 255)
    @Column (name = "STATUS")
    private String status;

}