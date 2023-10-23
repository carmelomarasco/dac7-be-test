package it.finanze.entrate.coopint.dboard.dpi.res.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.utils.ParamUtils;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageForm {
    private String msgUuid;
    private String startDate;
    private String endDate;
    private List<String> year;
    private List<String> country;
    private List<String> context;
    private List<String> type;
    private List<String> statusSM;
    private List<String> outcome;
    private String cfUser;
    private String typeSearchParam;
    private String startDateSinottico;
    private String endDateSinottico;


    public static boolean isValid(MessageForm input) {
        return !ParamUtils.isNullOrEmpty(input.getStartDate()) ||
                !ParamUtils.isNullOrEmpty(input.getEndDate()) ||
                !ParamUtils.isNullOrEmpty(input.getYear()) ||
                !ParamUtils.isNullOrEmpty(input.getCountry()) ||
                !ParamUtils.isNullOrEmpty(input.getContext()) ||
                !ParamUtils.isNullOrEmpty(input.getType()) ||
                !ParamUtils.isNullOrEmpty(input.getStatusSM()) ||
                !ParamUtils.isNullOrEmpty(input.getOutcome()) ||
                !ParamUtils.isNullOrEmpty(input.getMsgUuid());
    }

    public static boolean isValidSinottico(MessageForm input) {
        return !ParamUtils.isNullOrEmpty(input.getTypeSearchParam());
    }

    public static boolean isScambiNonConclusi(MessageForm input){
       return input.getTypeSearchParam().equalsIgnoreCase("SCAMBI_NON_CONCLUSI");
    }
}
