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
@Table (name = "CONSIDERATION")
public class Consideration {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "CONSIDERATION_SEQ")
    @SequenceGenerator (name = "CONSIDERATION_SEQ", sequenceName = "CONSIDERATION_SEQ", allocationSize = 1)
    @Column (name = "CONSIDERATION_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CONS_Q1_ID", nullable = false)
    private Amount consQ1;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CONS_Q2_ID", nullable = false)
    private Amount consQ2;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CONS_Q3_ID", nullable = false)
    private Amount consQ3;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CONS_Q4_ID", nullable = false)
    private Amount consQ4;

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