package it.finanze.entrate.coopint.dboard.dpi.res.command.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelXmlBean {
    private byte[] gZipExcel;
    private byte[] gZipXml;
    private String rfiTin;
    private Long fiscalYear;
    private String id;
    private List<ValueDescriptionBean> countries;
}
