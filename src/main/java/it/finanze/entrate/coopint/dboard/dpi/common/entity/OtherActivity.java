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
@Table (name = "OTHER_ACTIVITIES")
public class OtherActivity {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "OTHER_ACTIVITIES_SEQ")
    @SequenceGenerator (name = "OTHER_ACTIVITIES_SEQ", sequenceName = "OTHER_ACTIVITIES_SEQ", allocationSize = 1)
    @Column (name = "OTHER_ACTIVITIES_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CONSIDERATION_ID", nullable = false)
    private Consideration consideration;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "NUMBER_OF_ACTIVITIES_ID", nullable = false)
    private NumberOfActivity numberOfActivities;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "FEES_ID", nullable = false)
    private Fees fees;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "TAXES_ID", nullable = false)
    private Taxes taxes;

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