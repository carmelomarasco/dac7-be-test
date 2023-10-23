package it.finanze.entrate.coopint.dboard.dpi.res.command.rest;

import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.ValueDescriptionBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.service.ConstantService;
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
@RequestMapping("/dac7-res-view/constant")
public class ConstantRestRes {

    @Autowired
    ConstantService constantService;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/fy")
    public ResponseEntity<List<ValueDescriptionBean>> getAllFiscalYear() {
        log.info("Init method - getAllFiscalYear");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllFiscalYear());
    }

    @GetMapping("/countries")
    public ResponseEntity<List<ValueDescriptionBean>> getAllCountries() {
        log.info("Init method - getAllCountries");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllCountries());
    }

    @GetMapping("/msg-type")
    public ResponseEntity<List<ValueDescriptionBean>> getAllMsgType() {
        log.info("Init method - getAllMsgType");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllMsgType());
    }

    @GetMapping("/status-sm")
    public ResponseEntity<List<ValueDescriptionBean>> getAllStatusSM() {
        log.info("Init method - getAllStatusSM");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllStatusSM());
    }

    @GetMapping("/status")
    public ResponseEntity<List<ValueDescriptionBean>> getAllStatus() {
        log.info("Init method - getAllStatus");
        UserUtils.canDo(request,
                Role.EUE_DBOARD_AEOI_ADMIN.getValue(),
                Role.EUE_DAC7_Invio.getValue(),
                Role.EUE_DAC7_Consultazione.getValue());

        return ResponseEntity.ok(constantService.getAllStatus());
    }

}
