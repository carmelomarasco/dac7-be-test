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
@Table (name = "ADDITIONAL_INFO")
public class AdditionalInfo {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ADDITIONAL_INFO_SEQ")
    @SequenceGenerator (name = "ADDITIONAL_INFO_SEQ", sequenceName = "ADDITIONAL_INFO_SEQ", allocationSize = 1)
    @Column (name = "ADDITIONAL_INFO_ID", nullable = false)
    private Long id;

    @Size (max = 1000)
    @Column (name = "DOC_TYPE_INDIC", length = 1000)
    private String docTypeIndic;

    @Size (max = 200)
    @NotNull
    @Column (name = "DOC_REF_ID", nullable = false, length = 200)
    private String docRefId;

    @Size (max = 200)
    @Column (name = "CORR_DOC_REF_ID", length = 200)
    private String corrDocRefId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "RES_COUNTRY_CODE_ID")
    private Country resCountryCode;

    @Size (max = 4000)
    @Column (name = "VALUE", length = 4000)
    private String value;

    @NotNull
    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "LANGUAGE_CODE_ID", nullable = false)
    private LanguageCode languageCode;

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