package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.lazy.NewDataBuildingReportLazy;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageLogBean {
    private String reportId;
    private Short fiscalYear;
    private Integer totalReportableSellers;
    private String status;
    private String percentage;
    private String startDate;
    private String endDate;

    public static MessageLogBean getFromEntity(NewDataBuildingReportLazy input) {
        return MessageLogBean.builder()
                .reportId(input.getBuildingReportId())
                .fiscalYear(input.getFiscalYear())
                .totalReportableSellers(input.getTotalReportableSellers())
                .status(input.getBuildingStatusId().toString())
                .percentage(input.getProgressPercentual().toString())
                .startDate(input.getBuildStartDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getBuildStartDate())))
                .endDate(input.getBuildEndDate() == null ? "" : DateUtils.convertDateToString(Date.from(input.getBuildEndDate())))
                .build();
    }

    public static List<MessageLogBean> getListFromEntity(List<NewDataBuildingReportLazy> listInput) {
        List<MessageLogBean> listResult = new ArrayList<>();
        listInput = listInput.stream().sorted(Comparator.comparing(NewDataBuildingReportLazy::getFiscalYear).reversed()).collect(Collectors.toList());
        listInput.forEach(data -> listResult.add(MessageLogBean.getFromEntity(data)));
        return listResult;
    }
}
