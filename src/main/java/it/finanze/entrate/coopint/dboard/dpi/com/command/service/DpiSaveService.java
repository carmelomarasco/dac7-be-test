package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.Communication;
import it.finanze.entrate.coopint.dboard.dpi.common.entity.Country;
import it.finanze.entrate.coopint.dboard.dpi.com.command.entity.FiscalYear;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.AllInfoAcquiredBean;
import it.finanze.entrate.coopint.dboard.dpi.common.bean.DpiCommunicationPayload;
import oecd.ties.dpi.v1.DPIOECD;

import java.util.List;

public interface DpiSaveService {
    Communication saveAcquiredCommunication(
            DpiCommunicationPayload dpiCommunication,
            String xmlGzipBase64,
            DPIOECD dpioecd,
            List<Country> countriesList,
            AllInfoAcquiredBean allInfoAcquiredBean
    ) throws Exception;

    void saveCommunication(Communication clone);
    
    void saveFiscalYear(FiscalYear fiscalYearEntity);
}
