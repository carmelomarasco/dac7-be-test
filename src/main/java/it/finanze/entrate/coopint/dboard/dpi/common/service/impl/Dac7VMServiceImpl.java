package it.finanze.entrate.coopint.dboard.dpi.common.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.europa.ec.taxud.dac7.validation.Constants;
import eu.europa.ec.taxud.instantiation.StatusMessageManager;
import eu.europa.ec.taxud.validation.ValidationContext;
import eu.europa.ec.taxud.validation.ValidationManager;
import eu.europa.ec.taxud.validation.input.InputFormatType;
import eu.europa.ec.taxud.validation.input.ValidationInput;
import eu.europa.ec.taxud.dac7.instantiation.StatusMessageHandlerDAC7;
import it.finanze.entrate.coopint.dboard.dpi.common.service.Dac7VMService;
import it.finanze.entrate.coopint.dboard.dpi.config.ValidationProperties;

@Service
public class Dac7VMServiceImpl implements Dac7VMService {


    @Autowired
    private ValidationProperties validationProperties;


    @Override
    public String getStatusMessage(String xmlPath) {
        ValidationManager manager = new ValidationManager();
        ValidationContext context = new ValidationContext();
        
        context.setProperty(Constants.APPLICATION_DOMAIN, "DAC7");
        InputStream fin = null;

        try{    
            fin = new FileInputStream(xmlPath);   
        } catch (Exception e){System.out.println(e);}  

        ValidationInput input = new ValidationInput(fin, InputFormatType.XML);
        System.setProperty("external.module.location", validationProperties.EVM_DIR_PATH);
        manager.process(input, context);

        StatusMessageManager statusMessageManager = new StatusMessageManager();
        // statusMessageManager.process(context);
        // System.out.println(statusMessageManager.toString());
        return statusMessageManager.process(context);
    }

    @Override
    public boolean getValidationStatus(String xmlPath) {
        ValidationManager manager = new ValidationManager();
        ValidationContext context = new ValidationContext();
        
        context.setProperty(Constants.APPLICATION_DOMAIN, "DAC7");
        InputStream fin = null;

        try{    
            fin = new FileInputStream(xmlPath);   
        } catch (Exception e){System.out.println(e);}  

        ValidationInput input = new ValidationInput(fin, InputFormatType.XML);
        System.setProperty("external.module.location", validationProperties.EVM_DIR_PATH);
        return (manager.process(input, context).length() > 0 ? false : true);
    }
}
