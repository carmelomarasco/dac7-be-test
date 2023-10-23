package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.utils.ParamUtils;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageSearchBean {
    private String msgUuid;
    private String msgRef;
    private List<String> year;
    private String startDate;
    private String endDate;
    @Builder.Default
    private List<String> country = new ArrayList<>();
    @Builder.Default
    private List<String> context = new ArrayList<>();
    @Builder.Default
    private List<String> type = new ArrayList<>();
    private String poName;
    private String poTin;
    @Builder.Default
    private List<String> status = new ArrayList<>();
    @Builder.Default
    private List<String> statusStartFromTransmitted = new ArrayList<>();
    private String cfUser;
    private String typeSearchParam;
    private String startDateSinottico;
    private String endDateSinottico;

    public static boolean isValid(MessageSearchBean input) {
        return !ParamUtils.isNullOrEmpty(input.getStartDate()) ||
                !ParamUtils.isNullOrEmpty(input.getEndDate()) ||
                !ParamUtils.isNullOrEmpty(input.getMsgRef()) ||
                !ParamUtils.isNullOrEmpty(input.getYear()) ||
                !ParamUtils.isNullOrEmpty(input.getPoName()) ||
                !ParamUtils.isNullOrEmpty(input.getPoTin()) ||
                !ParamUtils.isNullOrEmpty(input.getCountry()) ||
                !ParamUtils.isNullOrEmpty(input.getContext()) ||
                !ParamUtils.isNullOrEmpty(input.getType()) ||
                !ParamUtils.isNullOrEmpty(input.getStatus());
    }

    public static boolean isValidSinottico(MessageSearchBean input) {
        return !ParamUtils.isNullOrEmpty(input.getTypeSearchParam());
    }

    public static boolean isScambiNonConclusi(MessageSearchBean input) {
        return input.getTypeSearchParam().equalsIgnoreCase("SCAMBI_NON_CONCLUSI");
    }

}
