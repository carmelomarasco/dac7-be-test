package it.finanze.entrate.coopint.dboard.dpi.com.command.utility;

import java.util.Date;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.UpdateBean;

public class CheckCondition {

    public static boolean isEmpty(String input) {
        return input == null || "".equals(input);
    }

    public static boolean dateAlreadyPassed(Date input) {
        Date toDay = new Date();
        return toDay.after(input);
    }

    public static boolean dateRespectTheRules(UpdateBean pwb, FiscalYear fy) throws Exception {

        if (CheckCondition.dateAlreadyPassed(fy.getExtendedExpDate())) {
            return false;
        }
        boolean expiredDate1 = CheckCondition.dateAlreadyPassed(fy.getExpirationDate());

        Date newDate1;
        Date newDate2 = DateUtil.htmlDateStringToDate(pwb.getFyExtendedExpirationDate());

        if (expiredDate1) {
            pwb.setFyExpirationDate("");
            newDate1 = fy.getExpirationDate();
        } else {

            newDate1 = DateUtil.htmlDateStringToDate(pwb.getFyExpirationDate());
        }

        return newDate2.after(newDate1) && !dateAlreadyPassed(newDate2) && (!dateAlreadyPassed(newDate1) || expiredDate1);
    }
}
