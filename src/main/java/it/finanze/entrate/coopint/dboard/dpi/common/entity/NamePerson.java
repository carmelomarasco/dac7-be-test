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
@Table (name = "NAME_PERSON")
public class NamePerson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NAME_PERSON_SEQ")
    @SequenceGenerator (name = "NAME_PERSON_SEQ", sequenceName = "NAME_PERSON_SEQ", allocationSize = 1)
    @Column (name = "NAME_PERSON_ID", nullable = false)
    private Long id;

    @Size (max = 200)
    @Column (name = "PRECEDING_TITLE", length = 200)
    private String precedingTitle;

    @Size (max = 4000)
    @NotNull
    @Column (name = "FIRST_NAME", nullable = false, length = 4000)
    private String firstName;

    @Size (max = 4000)
    @Column (name = "FIRST_NAME_TYPE", length = 4000)
    private String firstNameType;

    @Size (max = 4000)
    @Column (name = "MIDDLE_NAME", length = 4000)
    private String middleName;

    @Size (max = 4000)
    @Column (name = "MIDDLE_NAME_TYPE", length = 4000)
    private String middleNameType;

    @Size (max = 4000)
    @Column (name = "NAME_PREFIX", length = 4000)
    private String namePrefix;

    @Size (max = 4000)
    @Column (name = "NAME_PREFIX_TYPE", length = 4000)
    private String namePrefixType;

    @Size (max = 4000)
    @NotNull
    @Column (name = "LAST_NAME", nullable = false, length = 4000)
    private String lastName;

    @Size (max = 4000)
    @Column (name = "LAST_NAME_TYPE", length = 4000)
    private String lastNameType;

    @Size (max = 4000)
    @Column (name = "SUFFIX", length = 4000)
    private String suffix;

    @Size (max = 200)
    @Column (name = "GENERAL_SUFFIX", length = 200)
    private String generalSuffix;

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