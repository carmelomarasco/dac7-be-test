package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "ORIGINAL_MESSAGE", schema = "DBOARD_DPI_NATIONAL")
public class OriginalMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORIGINAL_MESSAGE_SEQ")
    @SequenceGenerator (name = "ORIGINAL_MESSAGE_SEQ", sequenceName = "ORIGINAL_MESSAGE_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "ORIGINAL_MESSAGE_ID", nullable = false)
    private Long id;

    @Size (max = 170)
    @Column (name = "ORIGINAL_MESSAGE_REF_ID", length = 170)
    private String originalMessageRefId;

    @Size (max = 100)
    @NotNull
    @Column (name = "CREATED_BY", nullable = false, length = 100)
    private String createdBy;

    @NotNull
    @Column (name = "CREATED_ON", nullable = false)
    private Instant createdOn;

    @Size (max = 100)
    @Column (name = "MODIFIED_BY", length = 100)
    private String modifiedBy;

    @Column (name = "MODIFIED_ON")
    private Instant modifiedOn;

}