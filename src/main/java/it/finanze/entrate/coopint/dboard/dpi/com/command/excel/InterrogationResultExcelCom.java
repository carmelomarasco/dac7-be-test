package it.finanze.entrate.coopint.dboard.dpi.com.command.excel;

import it.finanze.entrate.coopint.dboard.dpi.utils.ExcelUtils;
import it.finanze.entrate.coopint.dboard.dpi.utils.ParamUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.ComClassEntity;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.CommunicationStatus;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.MessageTypeIndic;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.ComClassRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CommunicationStatusRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.CountryRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.repository.MessageTypeIndicRepository;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationCodesDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CommunicationSearchDto;
import it.finanze.entrate.coopint.dboard.dpi.com.command.rest.nat.dto.CountryDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class InterrogationResultExcelCom {

    @Autowired
    ExcelUtils excelUtils;

    @Autowired
    @Qualifier("countryRepositoryNazionale")
    CountryRepository countryRepository;

    @Autowired
    @Qualifier("comClassRepositoryNazionale")
    ComClassRepository comClassRepository;

    @Autowired
    @Qualifier("messageTypeIndicRepositoryNazionale")
    MessageTypeIndicRepository messageTypeIndicRepository;

    @Autowired
    @Qualifier("communicationStatusRepositoryNazionale")
    CommunicationStatusRepository statusRepository;

    // private List<CheckBoxBean> allCountries = new ArrayList<>();
    // private List<CheckBoxBean> allClasses = new ArrayList<>();
    // private List<CheckBoxBean> allStatus = new ArrayList<>();
    // private List<CheckBoxBean> allTypes = new ArrayList<>();
    private List<Country> allCountries = new ArrayList<>();
    private List<ComClassEntity> allClasses = new ArrayList<>();
    private List<CommunicationStatus> allStatus = new ArrayList<>();
    private List<MessageTypeIndic> allTypes = new ArrayList<>();

    public byte[] build(List<CommunicationDto> bean, CommunicationSearchDto parametri) throws IOException {

        setAllConstants();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XSSFWorkbook workbook = new XSSFWorkbook();

        try {
            buildParameterSheet(workbook, parametri);
            buildResultSheet(workbook, bean);

            workbook.write(baos);

            baos.flush();
            baos.close();
        } catch (IOException ex) {
            throw ex;
        }

        return baos.toByteArray();
    }

    private void buildParameterSheet(XSSFWorkbook workbook, CommunicationSearchDto parametri) {
        XSSFSheet sheetParametriRicerca = workbook.createSheet("Parametri di ricerca");

        AtomicInteger indexRow = new AtomicInteger();
        XSSFRow row = sheetParametriRicerca.createRow(indexRow.getAndIncrement());
        XSSFCellStyle style = excelUtils.getStyleWithBorder(workbook);
        XSSFCellStyle styleWithFontBold = excelUtils.boldFont(workbook);
        excelUtils.createCellAndSetValue(workbook, row, styleWithFontBold, 0, "Parametri - Interrogazione delle comunicazioni");

        row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());

        if (!ParamUtils.isNullOrEmpty(parametri.getMessageDateFrom())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data ricezione dal");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getMessageDateFrom());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }
        if (!ParamUtils.isNullOrEmpty(parametri.getMessageDateTo())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Data ricezione al");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getMessageDateTo());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }
        if (!ParamUtils.isNullOrEmpty(parametri.getProtocol())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Protocollo");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getProtocol());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }
        if (!ParamUtils.isNullOrEmpty(parametri.getDestinationCountryId())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Destinazione");
            List<Country> destCountry = this.allCountries.stream().filter(c -> c.getId().toString().equals(parametri.getDestinationCountryId())).collect(Collectors.toList());
            String res = destCountry.get(0).getValue() + " - " + destCountry.get(0).getDescription();
            excelUtils.createCellAndSetValue(workbook, row, style, 1, res);
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getCfUser())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "CF/TIN/IN");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getCfUser());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getMessageRefId())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Identificativo della comunicazione");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getMessageRefId());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getMessageType())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Tipologia Messaggio");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getMessageType());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getMessageTypeIndic())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Tipologia Comunicazione");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getMessageTypeIndic());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getStatoComunicazione())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Stato comunicazione");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getStatoComunicazione());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getAnnoFiscale())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Anno Fiscale");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getAnnoFiscale());
            row = excelUtils.rowIncrement(sheetParametriRicerca, indexRow.getAndIncrement());
        }

        if (!ParamUtils.isNullOrEmpty(parametri.getDenominazione())) {
            excelUtils.createCellAndSetValue(workbook, row, style, 0, "Denominazione");
            excelUtils.createCellAndSetValue(workbook, row, style, 1, parametri.getDenominazione());
        }
        excelUtils.autoSize(sheetParametriRicerca, 2);

    }

    private void buildResultSheet(XSSFWorkbook workbook, List<CommunicationDto> bean) {
        XSSFSheet sheet = workbook.createSheet("Result");
        AtomicInteger indexRow = new AtomicInteger();
        XSSFRow row = sheet.createRow(indexRow.getAndIncrement());
        XSSFCellStyle style = excelUtils.getStyleWithBorder(workbook);
        XSSFCellStyle styleWithFontBold = excelUtils.boldFont(workbook);
        excelUtils.createCellAndSetValue(workbook, row, styleWithFontBold, 0, "Interrogazioni delle comunicazioni");

        row = excelUtils.rowIncrement(sheet, indexRow.getAndIncrement());

        excelUtils.buildHeader(workbook, row,
                Arrays.asList(
                        "Protocollo Comunicazione",
                        "Esito Ricezione",
                        "CF",
                        "Identificativo Comunicazione",
                        "Tipologia Messaggio",
                        "Tipologia Comunicazione ",
                        "Stato Comunicazione",
                        "Destinazione",
                        "Data ricezione",
                        "Anno Fiscale",
                        "Denominazione"
                ),
                styleWithFontBold);

        row = excelUtils.rowIncrement(sheet, indexRow.getAndIncrement());

        AtomicReference<XSSFRow> rowAtomicReference = new AtomicReference<>();
        rowAtomicReference.set(row);
        AtomicInteger colIndex = new AtomicInteger(0);
        AtomicInteger totColIndex = new AtomicInteger();
        bean.forEach(rs -> {
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getProtocol());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getReceipt());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getSenderCf());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getComUuid());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getMessageTypeIndic() != null ? rs.getMessageTypeIndic().getDescription() : "--");
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getComClass() != null ? rs.getComClass().getDescription() : "--");
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getStatus() != null ? rs.getStatus().getDescription() : "--");
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), this.listEffectiveDestCountryDescription(rs.getEffectiveDestsCountries()));
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getMessageDate());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), rs.getFiscalYear());
            excelUtils.createCellAndSetValue(workbook, rowAtomicReference.get(), style, colIndex.getAndIncrement(), this.listDenominazioni(rs.getCodesDto()));
            totColIndex.set(colIndex.get());
            colIndex.set(0);
            rowAtomicReference.set(excelUtils.rowIncrement(sheet, indexRow.getAndIncrement()));
        });

        excelUtils.autoSize(sheet, totColIndex.get());


    }

    private List<String> getValuesListEffectiveDest(CommunicationDto rs) {
        List<String> valuesList = rs.getEffectiveDestsCountries().stream()
                .map(CountryDto::getValue)
                .collect(Collectors.toList());
        return valuesList;
    }

    private void setAllConstants() {
        this.allClasses = comClassRepository.findAll();
        this.allStatus = statusRepository.findAll();
        this.allCountries = countryRepository.findAll();
        this.allTypes = messageTypeIndicRepository.findAll();
    }

    private String getStatusDescription(String id) {
        return this.allStatus.stream().filter(s -> s.getId().equals(id)).map(CommunicationStatus::getStatusDescription).findFirst().orElse("--");
    }

    private String getTypeDescription(String id) {
        return this.allTypes.stream().filter(s -> s.getValue().equals(id)).map(MessageTypeIndic::getValue).findFirst().orElse("--");
    }

    private String getClassDescription(String id) {
        return this.allClasses.stream().filter(s -> s.getComClassId().equals(id)).map(ComClassEntity::getComClassDescription).findFirst().orElse("--");
    }

    private String getCountryDescription(String cc) {
        return this.allCountries.stream().filter(s -> s.getValue().equals(cc)).map(Country::getDescription).findFirst().orElse("--");
    }

    private String listStatusDescription(List<String> listStatus){
        List<String> listStatusDesc = listStatus.stream().map(this::getStatusDescription).collect(Collectors.toList());
        return String.join(" - ",listStatusDesc);
    }

    private String listClassesDescription(List<String> listStatus){
        List<String> listStatusDesc = listStatus.stream().map(this::getClassDescription).collect(Collectors.toList());
        return String.join(" - ",listStatusDesc);
    }
    private String listTypesDescription(List<String> listStatus){
        List<String> listStatusDesc = listStatus.stream().map(this::getTypeDescription).collect(Collectors.toList());
        return String.join(" - ",listStatusDesc);
    }
    private String listCountriesDescription(List<String> listStatus){
        List<String> listStatusDesc = listStatus.stream().map(this::getCountryDescription).collect(Collectors.toList());
        return String.join(" - ",listStatusDesc);
    }

    private String listEffectiveDestCountryDescription(List<CountryDto> list){
        List<String> listStatusDesc = list.stream()
            .map(countryDto -> { 
                String description = countryDto.getDescription();
                int separatorIndex = description.indexOf(" - ");
                return separatorIndex >= 0 ? description.substring(0, separatorIndex) : description;
            })
            .collect(Collectors.toList());
        return String.join(" , ",listStatusDesc);
    }

    private String listDenominazioni(List<CommunicationCodesDto> list){
        List<String> listDenom = list.stream().map(CommunicationCodesDto::getDenominazione).collect(Collectors.toList());
            return String.join(" , ", listDenom);
    }
}
