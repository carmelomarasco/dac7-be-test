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
@Table (name = "NAME_REPORTABLE_SELLER")
public class NameReportableSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NAME_REPORTABLE_SELLER_SEQ")
    @SequenceGenerator (name = "NAME_REPORTABLE_SELLER_SEQ", sequenceName = "NAME_REPORTABLE_SELLER_SEQ", allocationSize = 1)
    @Column (name = "NAME_REPORTABLE_SELLER_ID", nullable = false)
    private Long id;

    @Size (max = 200)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 200)
    private String value;

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