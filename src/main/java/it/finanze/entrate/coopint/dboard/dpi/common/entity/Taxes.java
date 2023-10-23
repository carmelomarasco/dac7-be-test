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
@Table (name = "TAXES")
public class Taxes {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "TAXES_SEQ")
    @SequenceGenerator (name = "TAXES_SEQ", sequenceName = "TAXES_SEQ", allocationSize = 1)
    @Column (name = "TAXES_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TAX_Q1_ID", nullable = false)
    private Amount taxQ1;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TAX_Q2_ID", nullable = false)
    private Amount taxQ2;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TAX_Q3_ID", nullable = false)
    private Amount taxQ3;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TAX_Q4_ID", nullable = false)
    private Amount taxQ4;

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