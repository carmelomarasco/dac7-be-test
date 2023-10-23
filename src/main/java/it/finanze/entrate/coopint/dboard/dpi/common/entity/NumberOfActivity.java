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
@Table (name = "NUMBER_OF_ACTIVITIES")
public class NumberOfActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NUMBER_OF_ACTIVITIES_SEQ")
    @SequenceGenerator (name = "NUMBER_OF_ACTIVITIES_SEQ", sequenceName = "NUMBER_OF_ACTIVITIES_SEQ", allocationSize = 1)
    @Column (name = "NUMBER_OF_ACTIVITIES_ID", nullable = false)
    private Long id;

    @NotNull
    @Column (name = "NUMB_Q1", nullable = false)
    private Long numbQ1;

    @NotNull
    @Column (name = "NUMB_Q2", nullable = false)
    private Long numbQ2;

    @NotNull
    @Column (name = "NUMB_Q3", nullable = false)
    private Long numbQ3;

    @NotNull
    @Column (name = "NUMB_Q4", nullable = false)
    private Long numbQ4;

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