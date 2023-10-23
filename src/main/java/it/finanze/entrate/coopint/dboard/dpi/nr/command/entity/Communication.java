package it.finanze.entrate.coopint.dboard.dpi.nr.command.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table (name = "COMMUNICATION", schema = "DBOARD_DPI_NR")
public class Communication {
    @EmbeddedId
    private CommunicationId id;

    @Size (max = 255)
    @NotNull
    @Column (name = "MESSAGE_REF", nullable = false)
    private String messageRef;

    @NotNull
    @Column (name = "REPORTABLE_SELLERS_COUNT", nullable = false)
    private Long reportableSellersCount;

    @NotNull
    @Column (name = "IMMOVABLE_PROPERTIES_COUNT", nullable = false)
    private Long immovablePropertiesCount;

    @Size (max = 4000)
    @Column (name = "PLATFORM_OPERATOR_NAME", length = 4000)
    private String platformOperatorName;

    @Size (max = 16)
    @Column (name = "PLATFORM_OPERATOR_TIN", length = 16)
    private String platformOperatorTin;
}