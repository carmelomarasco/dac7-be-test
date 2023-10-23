package it.finanze.entrate.coopint.dboard.dpi.nr.command.rest;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.ValueDescriptionBean;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.service.ConstantService;
import it.finanze.security.enumeration.Role;
import it.finanze.security.util.UserUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CommonsLog
@RequestMapping("/dac7-nr-view/constant")
public class ConstantRestNR {

    @Autowired
    ConstantService constantService;
    @Autowired
    HttpServletRequest request;

    

    @GetMapping("/message-status")
    public ResponseEntity<List<ValueDescriptionBean>> getAllMessageStatus() {
        log.info("Init method - getAllMessageStatus");
       UserUtils.canDo(request,
               Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
               Role.EUE_DAC7_Invio.getValue(),
               Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllMessageStatusConstants());
    }

    @GetMapping("/message-building-status")
    public ResponseEntity<List<ValueDescriptionBean>> getAllMessageBuildingStatus() {
        log.info("Init method - getAllMessageBuildingStatus");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllMessageBuildingStatusConstants());
    }

    @GetMapping("/message-type")
    public ResponseEntity<List<ValueDescriptionBean>> getAllMessageType() {
        log.info("Init method - getAllMessageType");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllMessageTypeConstants());
    }

    @GetMapping("/country")
    public ResponseEntity<List<ValueDescriptionBean>> getAllCountry() {
        log.info("Init method - getAllCountry");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllCountryConstants());
    }

    @GetMapping("/year")
    public ResponseEntity<List<ValueDescriptionBean>> getAllYear() {
        log.info("Init method - getAllYear");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllYearConstants());
    }
}
