package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "CORRECTABLE_ELEMENT")
public class CorrectableElement {
    @Id
    @Size (max = 57)
    @Column (name = "CORRECTABLE_ELEMENT_UUID", nullable = false, length = 57)
    private String correctableElementUuid;

    @Size (max = 57)
    @NotNull
    @Column (name = "MSG_UUID", nullable = false, length = 57)
    private String msgUuid;

    @Size (max = 1000)
    @NotNull
    @Column (name = "DOCREFID", nullable = false, length = 1000)
    private String docrefid;

    @Size (max = 1000)
    @Column (name = "CORRDOCREFID", length = 1000)
    private String corrdocrefid;

    @Size (max = 255)
    @NotNull
    @Column (name = "DOCTYPEINDIC", nullable = false)
    private String doctypeindic;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TYPE_ID", nullable = false)
    private CorrectableElemType type;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "STATUS_ID", nullable = false)
    private CorrectableElemStatus status;

    @NotNull
    @Column (name = "CORRECTABLE_ELEM", nullable = false)
    private byte[] correctableElem;

}