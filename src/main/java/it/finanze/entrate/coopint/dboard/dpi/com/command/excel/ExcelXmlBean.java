package it.finanze.entrate.coopint.dboard.dpi.com.command.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelXmlBean {
    private byte[] gZipExcel;
    private byte[] gZipXml;
    private String rfiTin;
    private Long fiscalYear;
    private String id;
    //private List<CheckBoxBean> countries;
}
