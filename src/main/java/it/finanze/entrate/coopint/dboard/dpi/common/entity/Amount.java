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
@Table (name = "AMOUNT")
public class Amount {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "AMOUNT_SEQ")
    @SequenceGenerator (name = "AMOUNT_SEQ", sequenceName = "AMOUNT_SEQ", allocationSize = 1)
    @Column (name = "MON_AMNT_ID", nullable = false)
    private Long id;

    @NotNull
    @Column (name = "VALUE", nullable = false)
    private Long value;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "CURR_CODE_ID", nullable = false)
    private Currency currCode;

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