package it.finanze.entrate.coopint.dboard.dpi.nr.command.service.impl;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.ValueDescriptionBean;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.repository.*;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.service.ConstantService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CommonsLog
public class ConstantServiceImplNR implements ConstantService {

    @Autowired
    @Qualifier("dboardDpiCountryRepositoryNR")
    DboardDpiCountryRepository countryRepository;
    @Autowired
    @Qualifier("msgStatusRepositoryNR")
    MsgStatusRepository messageStatusRepository;
    @Autowired
    @Qualifier("buildingStatusRepositoryNR")
    BuildingStatusRepository buildingStatusRepository;
    @Autowired
    @Qualifier("messageTypeIndicRepositoryNR")
    MessageTypeIndicRepository messageTypeIndicRepository;
    @Autowired
    @Qualifier("fiscalYearRepositoryNR")
    FiscalYearRepository fiscalYearRepository;

    @Override
    public List<ValueDescriptionBean> getAllYearConstants() {
        return ValueDescriptionBean.getListFromYears(fiscalYearRepository.getAllOrdered());
    }

    @Override
    public List<ValueDescriptionBean> getAllCountryConstants() {
        return ValueDescriptionBean.getListFromCountry(countryRepository.findAllEuropean());
    }

    @Override
    public List<ValueDescriptionBean> getAllMessageStatusConstants() {
        return ValueDescriptionBean.getListFromMessageStatus(messageStatusRepository.getAllOrdered());
    }

    @Override
    public List<ValueDescriptionBean> getAllMessageBuildingStatusConstants() {
        return ValueDescriptionBean.getListFromMessageBuildingStatus(buildingStatusRepository.getAllOrdered());
    }

    @Override
    public List<ValueDescriptionBean> getAllMessageTypeConstants() {
        return ValueDescriptionBean.getListFromMessageType(messageTypeIndicRepository.getAllOrdered());
    }
}
