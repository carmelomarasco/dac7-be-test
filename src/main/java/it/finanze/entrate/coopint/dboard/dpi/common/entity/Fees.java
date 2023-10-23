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
@Table (name = "FEES")
public class Fees {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "FEES_SEQ")
    @SequenceGenerator (name = "FEES_SEQ", sequenceName = "FEES_SEQ", allocationSize = 1)
    @Column (name = "FEES_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FEES_Q1_ID", nullable = false)
    private Amount feesQ1;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FEES_Q2_ID", nullable = false)
    private Amount feesQ2;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FEES_Q3_ID", nullable = false)
    private Amount feesQ3;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FEES_Q4_ID", nullable = false)
    private Amount feesQ4;

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