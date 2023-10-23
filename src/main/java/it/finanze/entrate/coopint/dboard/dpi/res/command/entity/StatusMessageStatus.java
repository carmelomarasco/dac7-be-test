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
@Table (name = "STATUS_MESSAGE_STATUS", schema = "DBOARD_DPI_R")
public class StatusMessageStatus {
    @Id
    @Column (name = "SM_STATUS_ID", nullable = false)
    private Short id;

    @Size (max = 255)
    @NotNull
    @Column (name = "SM_STATUS_DESCRIPTION", nullable = false)
    private String smStatusDescription;

    @Size (max = 255)
    @Column (name = "STATUS")
    private String status;

}