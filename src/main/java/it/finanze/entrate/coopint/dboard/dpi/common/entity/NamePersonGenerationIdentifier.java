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
@Table (name = "NM_PERSON_GEN_IDENTIFIER")
public class NamePersonGenerationIdentifier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NM_PERSON_GEN_IDENTIFIER_SEQ")
    @SequenceGenerator (name = "NM_PERSON_GEN_IDENTIFIER_SEQ", sequenceName = "NM_PERSON_GEN_IDENTIFIER_SEQ", allocationSize = 1)
    @Column (name = "NM_PERSON_GEN_IDENTIFIER_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "NAME_PERSON_ID", nullable = false)
    private NamePerson namePerson;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "GENERATION_IDENTIFIER_ID", nullable = false)
    private GenerationIdentifier generationIdentifier;

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