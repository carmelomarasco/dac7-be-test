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
@Table (name = "LANGUAGE_CODE")
public class LanguageCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LANGUAGE_CODE_SEQ")
    @SequenceGenerator (name = "LANGUAGE_CODE_SEQ", sequenceName = "LANGUAGE_CODE_SEQ", allocationSize = 1)
    @Column (name = "LANGUAGE_CODE_ID", nullable = false)
    private Long id;

    @Size (max = 4000)
    @NotNull
    @Column (name = "VALUE", nullable = false, length = 4000)
    private String value;

    @Size (max = 4000)
    @NotNull
    @Column (name = "DESCRIPTION", nullable = false, length = 4000)
    private String description;

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