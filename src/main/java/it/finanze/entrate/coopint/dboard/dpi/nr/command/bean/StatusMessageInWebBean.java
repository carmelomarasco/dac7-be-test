package it.finanze.entrate.coopint.dboard.dpi.nr.command.bean;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.entity.StatusMessageIn;
import it.finanze.entrate.coopint.dboard.dpi.utils.JaxbUtils;
import it.finanze.entrate.coopint.xml.utils.IOUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CommonsLog
@Builder
public class StatusMessageInWebBean {
    private String uuid;
    private String msgUuid;
    private String status;
    private String msgRef;
    private String validationResult;
    private String validateBy;
    private String statusMessage;

    public static StatusMessageInWebBean getFromEntity(StatusMessageIn input) throws Exception {
        //TODO set statusMsg
        String statusMsg = "Non valido";
        try {
            byte[] xmlStatusMsg = IOUtils.gUnzipper(input.getSmBlob());
            statusMsg = JaxbUtils.prettyFormat(new String(xmlStatusMsg), 4);

        } catch (Exception ex) {
            log.error("error " + ex);
        }
        return StatusMessageInWebBean
                .builder()
                .uuid(input.getStatusMessageUuid())
                .msgUuid(input.getMsgUuid())
                .status(input.getStatus().getStatus())
                .msgRef(input.getMessageRef())
                .validationResult(input.getValidationResult())
                .validateBy(input.getValidatedBy())
                .statusMessage(statusMsg)
                .build();
    }
}
