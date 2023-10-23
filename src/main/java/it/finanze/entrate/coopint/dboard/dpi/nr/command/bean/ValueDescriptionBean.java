package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.BuildingStatus;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.DboardDpiCountry;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.MsgStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValueDescriptionBean {
    private String value;
    private String description;

    public static List<ValueDescriptionBean> getListFromYears(List<FiscalYear> input){
        List<ValueDescriptionBean> result = new ArrayList<>();
        input.forEach(e->{
            result.add(ValueDescriptionBean.builder().value(e.getYear().toString()).description(e.getYear().toString()).build());
        });
        return result;
    }

    public static ValueDescriptionBean getFromCountry(DboardDpiCountry input) {
        return ValueDescriptionBean.builder()
                .value(input.getCountryCode())
                .description(input.getDescription())
                .build();
    }

    public static List<ValueDescriptionBean> getListFromCountry(List<DboardDpiCountry> listInput) {
        List<ValueDescriptionBean> listResult = new ArrayList<>();

        listInput.forEach(data -> listResult.add(ValueDescriptionBean.getFromCountry(data)));

        return listResult;
    }

    public static ValueDescriptionBean getFromMessageStatus(MsgStatus input) {
        return ValueDescriptionBean.builder()
                .value(input.getId().toString())
                .description(input.getStatusDescription())
                .build();
    }

    public static List<ValueDescriptionBean> getListFromMessageStatus(List<MsgStatus> listInput) {
        List<ValueDescriptionBean> listResult = new ArrayList<>();

        listInput.forEach(data -> listResult.add(ValueDescriptionBean.getFromMessageStatus(data)));

        return listResult;
    }

    public static ValueDescriptionBean getFromMessageType(MessageTypeIndic input) {
        return ValueDescriptionBean.builder()
                .value(input.getId().toString())
                .description(input.getValue())
                .build();
    }

    public static List<ValueDescriptionBean> getListFromMessageType(List<MessageTypeIndic> listInput) {
        List<ValueDescriptionBean> listResult = new ArrayList<>();

        listInput.forEach(data -> listResult.add(ValueDescriptionBean.getFromMessageType(data)));

        return listResult;
    }

    public static ValueDescriptionBean getFromMessageBuildingStatus(BuildingStatus input) {
        return ValueDescriptionBean.builder()
                .value(input.getId().toString())
                .description(input.getStatusDescription())
                .build();
    }

    public static List<ValueDescriptionBean> getListFromMessageBuildingStatus(List<BuildingStatus> listInput) {
        List<ValueDescriptionBean> listResult = new ArrayList<>();

        listInput.forEach(data -> listResult.add(ValueDescriptionBean.getFromMessageBuildingStatus(data)));

        return listResult;
    }
}
