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
@Table (name = "COMMUNICATION_STATUS", schema = "DBOARD_DPI_NATIONAL")
public class CommunicationStatus {
    @Id
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMUNICATION_STATUS_SEQ")
    //@SequenceGenerator (name = "COMMUNICATION_STATUS_SEQ", sequenceName = "COMMUNICATION_STATUS_SEQ", allocationSize = 1, schema = "DBOARD_DPI_NATIONAL")
    @Column (name = "STATUS_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @Column (name = "STATUS", length = 4000)
    private String status;

    @Size (max = 4000)
    @Column (name = "STATUS_DESCRIPTION", length = 4000)
    private String statusDescription;

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