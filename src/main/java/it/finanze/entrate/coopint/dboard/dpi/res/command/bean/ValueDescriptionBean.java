package it.finanze.entrate.coopint.dboard.dpi.res.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.DboardDpiCountry;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.ReceiveMessageStatus;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.ReceiveMessageTypeIndic;
import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.StatusMessageStatus;
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

    public static List<ValueDescriptionBean> listFromYears(List<FiscalYear> input){
        List<ValueDescriptionBean> result = new ArrayList<>();
            input.forEach(e->{
                result.add(ValueDescriptionBean.builder().value(e.getYear().toString()).description(e.getYear().toString()).build());
            });
        return result;
    }

    public static List<ValueDescriptionBean> listFromCountries(List<DboardDpiCountry> input){
        List<ValueDescriptionBean> result = new ArrayList<>();
            input.forEach(e->{
                result.add(ValueDescriptionBean.builder().value(e.getCountryCode()).description(e.getDescription()).build());
            });
        return result;
    }

    public static List<ValueDescriptionBean> listFromMsgType(List<ReceiveMessageTypeIndic> input){
        List<ValueDescriptionBean> result = new ArrayList<>();
        input.forEach(e->{
            result.add(ValueDescriptionBean.builder().value(e.getId().toString()).description(e.getMtiDescription()).build());
        });
        return result;
    }

    public static List<ValueDescriptionBean> listFromStatusMS(List<StatusMessageStatus> input){
        List<ValueDescriptionBean> result = new ArrayList<>();
            input.forEach(e->{
                result.add(ValueDescriptionBean.builder().value(e.getId().toString()).description(e.getSmStatusDescription()).build());
            });
        return result;
    }
    public static List<ValueDescriptionBean> listFromStatus(List<ReceiveMessageStatus> input){
        List<ValueDescriptionBean> result = new ArrayList<>();
        input.forEach(e->{
            result.add(ValueDescriptionBean.builder().value(e.getId().toString()).description(e.getStatusDescription()).build());
        });
        return result;
    }
    
}
