package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "STATUS_MESSAGE_IN_STATUS", schema = "DBOARD_DPI_NR")
public class StatusMessageInStatus {
    @Id
    @Column (name = "STATUS_ID", nullable = false)
    private Short id;

    @Size (max = 255)
    @NotNull
    @Column (name = "STATUS_DESCRIPTION", nullable = false)
    private String statusDescription;

    @Size (max = 255)
    @NotNull
    @Column (name = "STATUS", nullable = false)
    private String status;

}