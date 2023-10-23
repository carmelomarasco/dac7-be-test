package it.finanze.entrate.coopint.dboard.dpi.res.command.service.impl;

import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.ValueDescriptionBean;
import it.finanze.entrate.coopint.dboard.dpi.res.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.res.command.service.ConstantService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CommonsLog
public class ConstantServiceImplRes implements ConstantService {

    @Autowired
    FiscalYearRepository fiscalYearRepository;
    @Autowired
    DboardDpiCountryRepository countryRepository;
    @Autowired
    ReceiveMessageTypeIndicRepository messageTypeIndicRepository;
    @Autowired
    StatusMessageStatusRepository statusMessageStatusRepository;
    @Autowired
    ReceiveMessageStatusRepository messageStatusRepository;

    @Override
    public List<ValueDescriptionBean> getAllFiscalYear() {
        return ValueDescriptionBean.listFromYears(fiscalYearRepository.getAllOrdered());
    }

    @Override
    public List<ValueDescriptionBean> getAllCountries() {
        return ValueDescriptionBean.listFromCountries(countryRepository.getAllOrderByDesc());
    }

    @Override
    public List<ValueDescriptionBean> getAllMsgType() {
        return ValueDescriptionBean.listFromMsgType(messageTypeIndicRepository.getAllOrdered());
    }

    @Override
    public List<ValueDescriptionBean> getAllStatusSM() {
        return ValueDescriptionBean.listFromStatusMS(statusMessageStatusRepository.getAllOrdered());
    }

    @Override
    public List<ValueDescriptionBean> getAllStatus() {
        return ValueDescriptionBean.listFromStatus(messageStatusRepository.getAllOrdered());
    }

}
