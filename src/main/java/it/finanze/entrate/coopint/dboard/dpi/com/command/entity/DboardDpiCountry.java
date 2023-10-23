package it.finanze.entrate.coopint.dboard.dpi.com.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "DBOARD_DPI_COUNTRIES")
public class DboardDpiCountry {
    @Id
    @Column (name = "COUNTRY_DESTINATION_ID")
    private Long countryDestinationId;

    @Column (name = "COUNTRY_ID")
    private Long countryId;

    @Size (max = 2)
    @Column (name = "COUNTRY_CODE", length = 2)
    private String countryCode;

    @Size (max = 4)
    @Column (name = "APPL", length = 4)
    private String appl;

    @Column (name = "ACCOUNTS_THRESHOLD")
    private Long accountsThreshold;

    @Column (name = "APPL_DATE_START")
    private LocalDate applDateStart;

    @Size (max = 100)
    @Column (name = "CREATED_BY", length = 100)
    private String createdBy;

    @Column (name = "CREATED_ON")
    private Instant createdOn;

    @Size (max = 100)
    @Column (name = "MODIFIED_BY", length = 100)
    private String modifiedBy;

    @Column (name = "MODIFIED_ON")
    private Instant modifiedOn;

    @Size (max = 255)
    @Column (name = "DESCRIPTION")
    private String description;

    @Column (name = "APPL_DATE_END")
    private LocalDate applDateEnd;

    @Size (max = 100)
    @Column (name = "CCN_CSI_GATEWAY_NAME", length = 100)
    private String ccnCsiGatewayName;

    @Size (max = 100)
    @Column (name = "CTS_PREFERRED_FOLDER", length = 100)
    private String ctsPreferredFolder;

}