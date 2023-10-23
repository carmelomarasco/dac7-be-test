package it.finanze.entrate.coopint.dboard.dpi.common.entity;

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
@Table (name = "GENERATION_IDENTIFIER")
public class GenerationIdentifier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENERATION_IDENTIFIER_SEQ")
    @SequenceGenerator (name = "GENERATION_IDENTIFIER_SEQ", sequenceName = "GENERATION_IDENTIFIER_SEQ", allocationSize = 1)
    @Column (name = "GENERATION_IDENTIFIER_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @Column (name = "GENERATION_IDENTIFIER", length = 4000)
    private String generationIdentifier;

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