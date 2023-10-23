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
@Table (name = "IMMOVABLE_PROPERTY")
public class ImmovableProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMMOVABLE_PROPERTY_SEQ")
    @SequenceGenerator (name = "IMMOVABLE_PROPERTY_SEQ", sequenceName = "IMMOVABLE_PROPERTY_SEQ", allocationSize = 1)
    @Column (name = "IMMOVABLE_PROPERTY_ID", nullable = false)
    private Long id;

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