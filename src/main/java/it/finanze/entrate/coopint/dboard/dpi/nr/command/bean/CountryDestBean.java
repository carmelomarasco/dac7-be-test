package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;


import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.NewDataBuildingCountryLazy;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDestBean {
    private String reportId;
    private String country;
    private String totalPOCount;
    private String totalSellerCount;
    private String totalImmovablePropertiesCount;
    private String status;
    private String startDate;
    private String endDate;
    private String percentage;
    private boolean alreadyRebuilt;
    private Long fiscalYear;

    public static CountryDestBean getFromEntity(NewDataBuildingCountryLazy input, Short fiscalYear) {
        return CountryDestBean.builder()
                .reportId(input.getBuildingReport().getBuildingReportId())
                .country(input.getCountry().getValue())
                .totalSellerCount(input.getTotalReportableSellers().toString())
                .totalImmovablePropertiesCount(input.getTotalImmovableProperties().toString())
               // .totalPoCount(totalPOCount)
                .status(input.getBuildingStatus().getId().toString())
                .startDate(input.getBuildStartDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getBuildStartDate())))
                .endDate(input.getBuildEndDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getBuildEndDate())))
                .percentage(input.getProgressPercentual().toString())
                .fiscalYear((long)fiscalYear)
                .build();
    }
    
    

    public static List<CountryDestBean> getListFromEntity(List<NewDataBuildingCountryLazy> listInput, Short fiscalYear) {
        List<CountryDestBean> listResult = new ArrayList<>();

        listInput.forEach(data -> listResult.add(CountryDestBean.getFromEntity(data, fiscalYear)));

        return listResult;
    }
}
