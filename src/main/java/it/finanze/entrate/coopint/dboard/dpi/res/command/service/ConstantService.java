package it.finanze.entrate.coopint.dboard.dpi.res.command.service;


import it.finanze.entrate.coopint.dboard.dpi.res.command.bean.ValueDescriptionBean;

import java.util.List;

public interface ConstantService {
    List<ValueDescriptionBean> getAllFiscalYear();
    List<ValueDescriptionBean> getAllCountries();
    List<ValueDescriptionBean> getAllMsgType();
    List<ValueDescriptionBean> getAllStatusSM();
    List<ValueDescriptionBean> getAllStatus();
}
