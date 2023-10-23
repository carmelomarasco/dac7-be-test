package it.finanze.entrate.coopint.dboard.dpi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Value;

@ConfigurationProperties(
        prefix = "validation"
)
@Data
public class ValidationProperties {

    public String EVM_DIR_PATH;

    public void setEVMDirPath(String eVMDirPath) {
        EVM_DIR_PATH = eVMDirPath;
    }

}