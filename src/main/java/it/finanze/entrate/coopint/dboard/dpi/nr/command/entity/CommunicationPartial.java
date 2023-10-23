package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "COMMUNICATION_PARTIALS", schema = "DBOARD_DPI_NR")
public class CommunicationPartial {
    @EmbeddedId
    private CommunicationPartialId id;

    @NotNull
    @Column (name = "COM_PARTIAL_BLOB", nullable = false)
    private byte[] comPartialBlob;

}