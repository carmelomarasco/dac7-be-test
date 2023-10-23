package it.finanze.entrate.coopint.dboard.dpi.res.command.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "MSG_EXCEL", schema = "DBOARD_DPI_R")
public class Excel {
    @Id
    @Size (max = 57)
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @NotNull
    @Column (name = "MSG_BLOB", nullable = false)
    private byte[] msgBlob;

}