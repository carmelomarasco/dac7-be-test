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
@Table (name = "IDENTIFIER")
public class Identifier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IDENTIFIER_SEQ")
    @SequenceGenerator (name = "IDENTIFIER_SEQ", sequenceName = "IDENTIFIER_SEQ", allocationSize = 1)
    @Column (name = "IDENTIFIER_ID", nullable = false)
    private Long id;
//    @Id
//    @Size (max = 4000)
//    @Column (name = "IDENTIFIER_ID", nullable = false, length = 4000)
//    private String identifierId;

    @Size (max = 4000)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 4000)
    private String value;

    @Size (max = 200)
    @NotNull
    @Column (name = "ACCOUNT_NUMBER_TYPE", nullable = false, length = 200)
    private String accountNumberType;

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