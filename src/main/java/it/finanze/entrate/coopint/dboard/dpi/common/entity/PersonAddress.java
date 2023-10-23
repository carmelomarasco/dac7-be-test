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
@Table (name = "PERSON_ADDRESS")
public class PersonAddress {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "PERSON_ADDRESS_SEQ")
    @SequenceGenerator (name = "PERSON_ADDRESS_SEQ", sequenceName = "PERSON_ADDRESS_SEQ", allocationSize = 1)
    @Column (name = "PERSON_ADDRESS_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "PERSON_ID", nullable = false)
    private Person person;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "ADDRESS_ID", nullable = false)
    private Address address;

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