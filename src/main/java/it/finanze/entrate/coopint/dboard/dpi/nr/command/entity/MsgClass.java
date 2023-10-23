package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MSG_CLASS", schema = "DBOARD_DPI_NR")
public class MsgClass {
    @Id
    @Column (name = "MSG_CLASS_ID", nullable = false)
    private Short id;

    @Size (max = 255)
    @NotNull
    @Column (name = "MSG_CLASS_DESCRIPTION", nullable = false)
    private String msgClassDescription;

}