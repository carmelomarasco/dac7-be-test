package it.finanze.entrate.coopint.dboard.dpi.res.command.excel;

import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.MessageBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.MessageForm;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.StatusMessageBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.ValueDescriptionBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.res.command.utils.DateUtil;
import it.finanze.entrate.coopint.dboard.dpi.utils.ExcelUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.ParamUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
@Component
public class InterrogationResultExcelRes {

    @Autowired
    ExcelUtils excelUtils;

    @Autowired
    DboardDpiCountryRepository countryRepository;

    @Autowired
    ReceiveMessageTypeIndicRepository messageTypeIndicRepository;

    @Autowired
    StatusMessageStatusRepository statusMessageStatusRepository;

    @Autowired
    StatusMessageRepository statusMessageRepository;

    @Autowired
    ReceiveMessageStatusRepository messageStatusRepository;

    private List<ValueDescriptionBean> allCountries = new ArrayList<>();
    private List<ValueDescriptionBean> allTypes = new ArrayList<>();
    private List<ValueDescriptionBean> allStatusSM = new ArrayList<>();
    private List<ValueDescriptionBean> allStatus = new ArrayList<>();


    public byte[] build(List<MessageBean> listMessage, MessageForm parametri) throws IOException {

        setConstants();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();

        boolean isNotClosedExchanges = parametri.getTypeSearchParam().equalsIgnoreCase("SCAMBI_NON_CONCLUSI")
                || parametri.getTypeSearchParam().equalsIgnoreCase("EXCHANGE_NOT_CLOSED");

        buildParameterSheet(workbook, parametri, isNotClosedExchanges);
        buildResultSheet(workbook, listMessage, isNotClosedExchanges);

        workbook.write(baos);

        baos.flush();
        baos.close();

        return baos.toByteArray();
    }

    private void buildParameterSheet(XSSFWorkbook workbook, MessageForm parametri, boolean isNotClosedMessages) {
        XSSFSheet sheetParametriRicerca = workbook.createSheet("Parametri di ricerca");

        AtomicInteger indexRow = new AtomicInteger();
        XSSFRow row = sheetParametriRicerca.createRow(indexRow.getAndIncrement());
        XSSFCellStyle style = excelUtils.getStyleWithBorder(workbook);
        XSSFCellStyle styleWithFontBold = excelUtils.boldFont(workbook);
        excelUtils.createCellAndSetValue(workbook, row, styleWithFontBold, 0,
                !isNotClosedMessages?"Parametri - Interrogazione dei messaggi":"Parametri - Scambi non conclusi");

        row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());

        excelUtils.createCellAndSetValue(workbook, row, style, 0, "CF del funzionario: ");
        excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getCfUser());
        row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());

        excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data: ");
        excelUtils.createCellAndSetValue(workbook, row, style, 1, DateUtil.convertDateToString(new Date()));
        row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        if(!isNotClosedMessages) {

            if (!ParamUtils.isNullOrEmpty(parametri.getYear())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Anno imposta:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, String.join("; ", parametri.getYear()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!(ParamUtils.isNullOrEmpty(parametri.getStartDate()))) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data ricezione dal:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getStartDate());
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getEndDate())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data ricezione al:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getEndDate());
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }

            if (!ParamUtils.isNullOrEmpty(parametri.getCountry())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Paese inviante:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListCountryDesc(parametri.getCountry()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }

            // if (!ParamUtils.isNullOrEmpty(parametri.getContext())) {
            //     excelUtils.createCellAndSetValue(workbook, row, style, 0, "Contesto:");
            //     excelUtils.createCellAndSetValue(workbook, row, style, 1, String.join("; ", parametri.getContext()));
            //     row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            // }
            if (!ParamUtils.isNullOrEmpty(parametri.getType())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Tipologia messaggio:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListTypeDesc(parametri.getType()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getOutcome())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Esito acquisizione:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListOutcomeDesc(parametri.getOutcome()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getStatusSM())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Stato dello status message:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListStatusDesc(parametri.getStatusSM()));
            }
        }
        excelUtils.autoSize(sheetParametriRicerca, 2);

    }

    private void buildResultSheet(XSSFWorkbook workbook, List<MessageBean> listMessage, boolean isNotClosedMessages) {
        XSSFSheet sheet = workbook.createSheet("Result");
        AtomicInteger indexRow = new AtomicInteger();
        XSSFRow row = sheet.createRow(indexRow.getAndIncrement());
        XSSFCellStyle style = excelUtils.getStyleWithBorder(workbook);
        XSSFCellStyle styleWithFontBold = excelUtils.boldFont(workbook);
        excelUtils.createCellAndSetValue(workbook, row, styleWithFontBold, 0,
                !isNotClosedMessages?"Interrogazioni dei messaggi":"Scambi non conclusi");

        row = excelUtils.rowIncrement(sheet, indexRow.getAndIncrement());

        excelUtils.buildHeader(workbook, row,
                Arrays.asList(
                        "Identificativo messaggio",
                        "Paese inviante",
                        "Tipologia messaggio",
                        "Data ricezione",
                        "Elenco RFI",
                        "Numero totale conti",
                        "Esito elaborazione",
                        "Stato dello status message",
                        "Data stato dello status message"
                ),
                styleWithFontBold);

        row = excelUtils.rowIncrement(sheet, indexRow.getAndIncrement());

        AtomicReference<XSSFRow> rowAtomicReference = new AtomicReference<>();
        rowAtomicReference.set(row);
        AtomicInteger colIndex = new AtomicInteger(0);
        AtomicInteger totColIndex = new AtomicInteger();
        listMessage.forEach(rs -> {
            if(rs.getListPO()!= null && rs.getListPO().length()> 1000){
                rs.setListPO(rs.getListPO().substring(0,1000)+ "...");
            }
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getMsgRef());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getCountryDescription(rs.getCountry()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getTypeDescription(rs.getType()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getDate());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getListPO());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getNumSeller());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getOutcomeDescription(rs.getOutcome()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getStatusSMDescription(rs.getStatusSM()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getSmDate());
            totColIndex.set(colIndex.get());
            colIndex.set(0);
            rowAtomicReference.set(excelUtils.rowIncrement(sheet, indexRow.getAndIncrement()));
        });

        excelUtils.autoSize(sheet, totColIndex.get());

    }

    private void setConstants() {
        allCountries = ValueDescriptionBean.listFromCountries(countryRepository.getAllOrderByDesc());
        allTypes = ValueDescriptionBean.listFromMsgType(messageTypeIndicRepository.findAll());
        allStatusSM = ValueDescriptionBean.listFromStatusMS(statusMessageStatusRepository.findAll());
        allStatus = ValueDescriptionBean.listFromStatus(messageStatusRepository.findAll());
    }

    private String getListCountryDesc(List<String> listCountry) {
        StringBuilder result = new StringBuilder();
        listCountry.forEach(e -> {
            String desc = getCountryDescription(e);
            result.append(result.toString().isEmpty() ? desc : "; " + desc);
        });
        return result.toString();
    }

    private String getListTypeDesc(List<String> listType) {
        StringBuilder result = new StringBuilder();
        listType.forEach(e -> {
            String desc = getTypeDescription(e);
            result.append(result.toString().isEmpty() ? desc : "; " + desc);
        });
        return result.toString();
    }

    private String getListStatusDesc(List<String> listStatus) {
        StringBuilder result = new StringBuilder();
        listStatus.forEach(e -> {
            String desc = getStatusSMDescription(e);
            result.append(result.toString().isEmpty() ? desc : "; " + desc);
        });
        return result.toString();
    }

    public String getListOutcomeDesc(List<String> listOutcome) {
        StringBuilder result = new StringBuilder();
        listOutcome.forEach(e -> {
            String desc = getOutcomeDescription(e);
            result.append(result.toString().isEmpty() ? desc : "; " + desc);
        });
        return result.toString();
    }

    private String getCountryDescription(String code) {
        return allCountries.stream()
                .filter(e -> code.equals(e.getValue()))
                .findFirst()
                .orElse(ValueDescriptionBean.builder().description(code).build())
                .getDescription();
    }

    private String getTypeDescription(String value) {
        return allTypes.stream()
                .filter(e -> value.equals(e.getValue()))
                .findFirst()
                .orElse(ValueDescriptionBean.builder().description(value).build())
                .getDescription();
    }

    private String getOutcomeDescription(String value) {
        return allStatus.stream()
                .filter(e -> value.equals(e.getValue()))
                .findFirst()
                .orElse(ValueDescriptionBean.builder().description(value).build())
                .getDescription();
    }

    private StatusMessageBean getStatusMsg(String smUuid) {
        return StatusMessageBean.beanForExcel(statusMessageRepository.findStatusMessageById_smUuid(smUuid));
    }

    private String getStatusSMDescription(String value) {
        return allStatusSM.stream()
                .filter(e -> value.equals(e.getValue()))
                .findFirst()
                .orElse(ValueDescriptionBean.builder().description(value).build())
                .getDescription();
    }

}
