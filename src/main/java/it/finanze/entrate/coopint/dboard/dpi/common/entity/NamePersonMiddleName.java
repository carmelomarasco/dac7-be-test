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
@Table (name = "NAME_PERSON_MIDDLE_NAME")
public class NamePersonMiddleName {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NAME_PERSON_MIDDLE_NAME_SEQ")
    @SequenceGenerator (name = "NAME_PERSON_MIDDLE_NAME_SEQ", sequenceName = "NAME_PERSON_MIDDLE_NAME_SEQ", allocationSize = 1)
    @Column (name = "NAME_PERSON_MIDDLE_NAME_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "NAME_PERSON_ID", nullable = false)
    private NamePerson namePerson;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "MIDDLE_NAME_ID", nullable = false)
    private MiddleName middleName;

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