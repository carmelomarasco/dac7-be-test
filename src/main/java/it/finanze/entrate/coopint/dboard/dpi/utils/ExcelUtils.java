package it.finanze.entrate.coopint.dboard.dpi.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ExcelUtils {


    public void buildHeader(XSSFWorkbook workbook, XSSFRow xssfRow, List<String> value, XSSFCellStyle style) {
        AtomicInteger i = new AtomicInteger(0);
        value.forEach(rs -> createCellAndSetValue(workbook, xssfRow, style, i.getAndIncrement(), rs));
    }

    public void createCellAndSetValue(XSSFWorkbook workbook, XSSFRow row, XSSFCellStyle style, int colNum, Object value) {
        XSSFCell cell = row.createCell(colNum);

//        if (style != null) {
//            cell.setCellStyle(style);
//        }
//        XSSFCellStyle rightAlignStyle = getStyleWithBorder(workbook);
//        rightAlignStyle.setAlignment(HorizontalAlignment.RIGHT);

        if (value instanceof Double) {
            cell.setCellValue((Double) value);
//            cell.setCellStyle(rightAlignStyle);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
//            cell.setCellStyle(rightAlignStyle);
        } else if (value instanceof String) {
//            cell.setCellStyle(DateUtil.isValidDate(String.valueOf(value)) ? rightAlignStyle : style);
            cell.setCellValue((String) value);
        }


    }

    public void autoSize(XSSFSheet sheet, int numCol) {
        for (int i = 0; i < numCol; i++)
            sheet.autoSizeColumn(i);
    }

    public XSSFCellStyle getStyleWithBorder(XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    public XSSFCellStyle boldFont(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = getStyleWithBorder(workbook);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    public XSSFRow rowIncrement(XSSFSheet sheet, int index) {
        return sheet.createRow(index++);
    }

}
