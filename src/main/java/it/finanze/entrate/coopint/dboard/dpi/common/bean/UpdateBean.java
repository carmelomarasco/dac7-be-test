package it.finanze.entrate.coopint.dboard.dpi.common.bean;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBean {
    private List<String> idCommunications;
    private String notes;
    private long fiscalYear;
    private String fyExpirationDate;
    private String fyExtendedExpirationDate;
}
