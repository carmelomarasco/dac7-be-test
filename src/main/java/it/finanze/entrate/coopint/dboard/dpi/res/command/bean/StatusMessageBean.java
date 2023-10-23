package it.finanze.entrate.coopint.dboard.dpi.res.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.res.command.entity.StatusMessage;
import it.finanze.entrate.coopint.dboard.dpi.res.command.utils.DateUtil;
import it.finanze.entrate.coopint.dboard.dpi.utils.JaxbUtils;
import it.finanze.entrate.coopint.xml.utils.IOUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CommonsLog
@Builder
public class StatusMessageBean {
    private String uuid;
    private String statusMsgRef;
    private String status;
    private String date;
    private String validateBy;
    private String validationResult;
    private String xmlSm;


    public static StatusMessageBean beanForExcel(StatusMessage input) {
        if (input == null) {
            return new StatusMessageBean();
        }
        return StatusMessageBean.builder()
                .uuid(input.getId().getSmUuid())
                .status(input.getSmStatus().getId().toString())
                .date(DateUtil.convertDateToString(Date.from(input.getId().getSmChangeDate())))
                .validationResult(input.getValidationResult())
                .build();
    }

    public static StatusMessageBean fromEntity(StatusMessage input) {
        String statusMsg = "Non valido";
        try {
            byte[] xmlStatusMsg = IOUtils.gUnzipper(input.getSmBlob());
            statusMsg = JaxbUtils.prettyFormat(new String(xmlStatusMsg), 4);

        } catch (Exception ex) {
            log.error("error " + ex);
        }
        return StatusMessageBean.builder()
                .uuid(input.getId().getSmUuid())
                .statusMsgRef(input.getSmMessageRef())
                .status(input.getSmStatus().getId().toString())
                .date(DateUtil.convertDateToString(Date.from(input.getId().getSmChangeDate())))
                .validateBy(input.getValidatedBy())
                .validationResult(input.getValidationResult())
                .xmlSm(statusMsg)
                .build();
    }

    public static List<StatusMessageBean> listFromEntities(List<StatusMessage> listInput) {
        return listInput.stream().map(StatusMessageBean::fromEntity).collect(Collectors.toList());

    }

}
