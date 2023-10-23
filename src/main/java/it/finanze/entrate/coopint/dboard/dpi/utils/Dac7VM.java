package it.finanze.entrate.coopint.dboard.dpi.utils;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.europa.ec.taxud.validation.ValidationContext;
import eu.europa.ec.taxud.validation.ValidationManager;
import eu.europa.ec.taxud.validation.input.InputFormatType;
import eu.europa.ec.taxud.validation.input.ValidationInput;
import eu.europa.ec.taxud.dac7.validation.Constants;
import eu.europa.ec.taxud.dac7.validation.XSDValidatorDAC7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import eu.europa.ec.taxud.validation.intf.ValidateMessage;
import it.finanze.entrate.coopint.dboard.dpi.config.ValidationProperties; 

@Component
public class Dac7VM {

    @Autowired
    private ValidationProperties validationProperties;

    

    public void validation(String xmlPath) {
        ValidationManager manager = new ValidationManager();
        ValidationContext context = new ValidationContext();
        //todo passare percorso drool nel context
        context.setProperty(Constants.INPUT_SS, "BE");
    
        // context.setProperty("resources-dir-path", validationProperties.RESOURCES_DIR_PATH);
        // context.setProperty("drl.location", "/DAC7-RESOURCES-FOLDER/dac7/DATA/rules");
        // context.setProperty(Constants.INPUT_RR, "LU");
        context.setProperty(Constants.APPLICATION_DOMAIN, "DAC7");
        File file = new File("./resources/example/Valid-Send-InitialMsg-AssumedPO-010.xml");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine();
            System.out.println("First line of file: " + firstLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{    
            FileInputStream fin = new FileInputStream(xmlPath);    
            int i=fin.read();  
            System.out.print((char)i);    
  
            fin.close();    
          } catch (Exception e){System.out.println(e);}    
         

        ValidationInput input = new ValidationInput(file, InputFormatType.XML);
        System.setProperty("external.module.location", validationProperties.EVM_DIR_PATH);
        String validationMessage = manager.process(input, context);
        System.out.println(validationMessage);
        System.out.println("Validation completed");
    }

}
