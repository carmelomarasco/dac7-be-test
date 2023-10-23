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
@Table (name = "SM_FILE_ACCEPTANCE_STATUS", schema = "DBOARD_DPI_NATIONAL")
public class SmFileAcceptanceStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SM_FILE_ACCEPTANCE_STATUS_SEQ")
    @SequenceGenerator (name = "SM_FILE_ACCEPTANCE_STATUS_SEQ", sequenceName = "SM_FILE_ACCEPTANCE_STATUS_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "FILE_ACCEPTANCE_STATUS_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 4000)
    private String value;

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