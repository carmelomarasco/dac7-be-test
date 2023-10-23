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
@Table (name = "CORRECTABLE_ELEM_TYPE", schema = "DBOARD_DPI_R")
public class CorrectableElemType {
    @Id
    @Column (name = "TYPE_ID", nullable = false)
    private Short id;

    @Size (max = 255)
    @NotNull
    @Column (name = "TYPE_DESCRIPTION", nullable = false)
    private String typeDescription;

}