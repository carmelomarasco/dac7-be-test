package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBean {
    @Builder.Default
    private List<String> listMessageId = new ArrayList<>();
    @Builder.Default
    private List<String> listCountryCodeAndYear = new ArrayList<>();
    private String countryCode;
    private String fiscalYear;
    private String notes;
    private String statusTo;
    private String cf;
}
