package it.finanze.entrate.coopint.dboard.dpi.com.command.rest;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiCommunicationService;
import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiEventSender;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.UpdateBean;

@RestController
@RequestMapping(value = "/dpi-com-command")
@Deprecated
public class ApplicationServiceRest {

    @Autowired
    DpiEventSender dpiEventSender;
    @Autowired
    DpiCommunicationService dpiCommunicationService;
    @Autowired
    HttpServletRequest request;


    @PutMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStatus(@RequestBody UpdateBean pwb) {
        //TODO UserUtils.canDo(request, Role.EUE_DBOARD_AEOI_ADMIN.getValue(), Role.EUE_DPI_Invio.getValue(), Role.EUE_DAC2_Invio.getValue());

        // TODO verificare
//        StatusEntity status = dpiCommunicationService.checkIfItsPossibileToChangeStatus(pwb);
//        dpiEventSender.sendEventUpdateStatuses(pwb, status.getStatus());
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/delete-communications")
    public ResponseEntity<Void> deleteCommunications(UpdateBean pwb) {
        // TODO UserUtils.canDo(request, Role.EUE_DBOARD_AEOI_ADMIN.getValue(), Role.EUE_DPI_Invio.getValue(), Role.EUE_DAC2_Invio.getValue());
     // TODO verificare
//        dpiCommunicationService.checkIfItsPossibileToDeleteCommunications(pwb);
//        dpiEventSender.sendEventDeleteCommunications(pwb);
        return ResponseEntity.ok().build();

    }
}
