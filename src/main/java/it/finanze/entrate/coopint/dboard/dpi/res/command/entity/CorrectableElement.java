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
@Table (name = "CORRECTABLE_ELEMENT", schema = "DBOARD_DPI_R")
public class CorrectableElement {
    @Id
    @Size (max = 57)
    @Column (name = "CORRECTABLE_ELEMENT_UUID", nullable = false, length = 57)
    private String correctableElementUuid;

    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "STATUS_ID", nullable = false)
    private CorrectableElemStatus status;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TYPE_ID", nullable = false)
    private CorrectableElemType type;

    @Size (max = 4000)
    @NotNull
    @Column (name = "DOCREFID", nullable = false, length = 4000)
    private String docrefid;

    @Size (max = 4000)
    @Column (name = "CORRDOCREFID", length = 4000)
    private String corrdocrefid;

    @Size (max = 255)
    @NotNull
    @Column (name = "DOCTYPEINDIC", nullable = false)
    private String doctypeindic;

    @Column (name = "CORR_ELEM_BLOB")
    private byte[] corrElemBlob;

}