package it.finanze.entrate.coopint.dboard.dpi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.finanze.entrate.coopint.dboard.dpi.common.service.Dac7VMService;

import javax.annotation.PostConstruct;

@Component
public class Dac7VMRunner {

    @Autowired
    private Dac7VMService dac7VMService;
    
    public Dac7VMRunner(Dac7VMService dac7VMService) {
        this.dac7VMService = (Dac7VMService) dac7VMService;
    }

    // @PostConstruct
    // public void runValidation() {
    //     boolean vStatus = dac7VMService.getValidationStatus("./resources/example/Valid-Send-InitialMsg-AssumedPO-010.xml");
    //     String message = dac7VMService.getStatusMessage("./resources/example/Valid-Send-InitialMsg-AssumedPO-010.xml");
    //     System.out.println("Validation status: " + vStatus);
    //     System.out.println("Validation message: " + message);
    // }
}