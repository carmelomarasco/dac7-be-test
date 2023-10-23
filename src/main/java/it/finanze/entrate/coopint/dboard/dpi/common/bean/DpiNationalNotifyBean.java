package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import java.util.Date;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.com.command.utility.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DpiNationalNotifyBean {
    private String protocol;
    private String arrivedDate;
    private String fiscalYear;
    private String reportingPeriod;
    private String idCom;
    private String comType;
    private String comStatus;
    private String comClass;

    private String expirationDate;
    private String endToleranceDate;

    public static DpiNationalNotifyBean buildBeanForReceivedComNotify(Communication com, String typeDesc, String statusDesc, String classDesc) {
        return DpiNationalNotifyBean
                .builder()
                .protocol(com.getProtocol())
                .arrivedDate(DateUtil.convertDateToString(Date.from(com.getMessageDate())))
                .reportingPeriod(com.getReportingPeriod().toString())
                .idCom(com.getMessageRefId())
                .comType(typeDesc)
                .comStatus(statusDesc)
                .comClass(classDesc)
                .build();
    }

    public static DpiNationalNotifyBean buildBeanForFiscalYear(FiscalYear fy) {
        return DpiNationalNotifyBean.builder()
                .fiscalYear(String.valueOf(fy.getYear()))
                .expirationDate(DateUtil.convertDateToString(fy.getExpirationDate()))
                .endToleranceDate(DateUtil.convertDateToString(fy.getExtendedExpDate()))
                .build();
    }

}
