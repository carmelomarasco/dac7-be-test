package it.finanze.entrate.coopint.dboard.dpi.nr.command.excel;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.MessageBean;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.MessageSearchBean;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.ValueDescriptionBean;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.CountryRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.DboardDpiCountryRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.MessageTypeIndicRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.MsgStatusRepository;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.StatusMessageInRepository;
import it.finanze.entrate.coopint.dboard.dpi.utils.DateUtils;
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
public class InterrogationResultExcelNR {

    @Autowired
    ExcelUtils excelUtils;

    @Autowired
    DboardDpiCountryRepository countryRepository;

    @Autowired
    MessageTypeIndicRepository messageTypeIndicRepository;

    @Autowired
    MsgStatusRepository messageStatusRepository;

    @Autowired
    StatusMessageInRepository statusMessageInRepository;

    private List<ValueDescriptionBean> allCountries = new ArrayList<>();
    private List<ValueDescriptionBean> allTypes = new ArrayList<>();
    private List<ValueDescriptionBean> allStatus = new ArrayList<>();


    public byte[] build(List<MessageBean> listMessage, MessageSearchBean parametri) throws IOException {

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

    private void buildParameterSheet(XSSFWorkbook workbook, MessageSearchBean parametri, boolean isNotClosedMessages) {
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
        excelUtils.createCellAndSetValue(workbook, row, style, 1, DateUtils.convertDateToString(new Date()));
        row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());

        if (!ParamUtils.isNullOrEmpty(parametri.getMsgRef())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Identificativo messaggio:");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, String.join("; ", parametri.getMsgRef()));
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if(!isNotClosedMessages) {

            if (!ParamUtils.isNullOrEmpty(parametri.getYear())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Anno imposta:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, String.join("; ", parametri.getYear()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!(ParamUtils.isNullOrEmpty(parametri.getStartDate()))) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data trasmissione dal:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getStartDate());
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getEndDate())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data trasmissione al:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getEndDate());
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }

            if (!ParamUtils.isNullOrEmpty(parametri.getCountry())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Paese destinatario:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListCountryDesc(parametri.getCountry()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }

            if (!ParamUtils.isNullOrEmpty(parametri.getContext())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Contesto:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, String.join("; ", parametri.getContext()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getType())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Tipologia messaggio:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListTypeDesc(parametri.getType()));
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getPoName())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Nome della RFI:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getPoName());
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getPoTin())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "TIN della RFI:");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getPoTin());
                row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
            }
            if (!ParamUtils.isNullOrEmpty(parametri.getStatus())) {
                excelUtils.createCellAndSetValue(workbook, row, style, 0, "Stato del messaggio");
                excelUtils.createCellAndSetValue(workbook, row, style, 1, getListStatusDesc(parametri.getStatus()));
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
        excelUtils.createCellAndSetValue(workbook, row, styleWithFontBold, 0, !isNotClosedMessages?"Interrogazione dei messaggi":"Scambi non conclusi");

        row = excelUtils.rowIncrement(sheet, indexRow.getAndIncrement());

        excelUtils.buildHeader(workbook, row,
                Arrays.asList(
                        "Identificativo messaggio",
                        "Paese di destinazione",
                        "Tipologia messaggio",
                        "Data trasmissione",
                        "Elenco RFI",
                        "Numero totale RFI",
                        "Numero totale conti",
                        "Stato del messaggio",
                        "Esito"
                ),
                styleWithFontBold);

        row = excelUtils.rowIncrement(sheet, indexRow.getAndIncrement());

        AtomicReference<XSSFRow> rowAtomicReference = new AtomicReference<>();
        rowAtomicReference.set(row);
        AtomicInteger colIndex = new AtomicInteger(0);
        AtomicInteger totColIndex = new AtomicInteger();
        listMessage.forEach(rs -> {
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getMsgRef());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getCountryDescription(rs.getCountry()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getTypeDescription(rs.getType()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getDate());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getListPO());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getPoCount().toString());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getSellerCount().toString());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), getStatusDescription(rs.getStatus()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getValidationResult());
            totColIndex.set(colIndex.get());
            colIndex.set(0);
            rowAtomicReference.set(excelUtils.rowIncrement(sheet, indexRow.getAndIncrement()));
        });

        excelUtils.autoSize(sheet, totColIndex.get());

    }

    private void setConstants() {
        allCountries = ValueDescriptionBean.getListFromCountry(countryRepository.findAllEuropean());
        allTypes = ValueDescriptionBean.getListFromMessageType(messageTypeIndicRepository.findAll());
        allStatus = ValueDescriptionBean.getListFromMessageStatus(messageStatusRepository.findAll());
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
            String desc = getStatusDescription(e);
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

    private String getStatusDescription(String value) {
        return allStatus.stream()
                .filter(e -> value.equals(e.getValue()))
                .findFirst()
                .orElse(ValueDescriptionBean.builder().description(value).build())
                .getDescription();
    }

}
