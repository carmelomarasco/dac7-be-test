package it.finanze.entrate.coopint.dboard.dpi.nr.command.service;

import it.finanze.entrate.coopint.dboard.dpi.nr.command.bean.ValueDescriptionBean;

import java.util.List;

public interface ConstantService {
    List<ValueDescriptionBean> getAllYearConstants();
    List<ValueDescriptionBean> getAllCountryConstants();
    List<ValueDescriptionBean> getAllMessageStatusConstants();
    List<ValueDescriptionBean> getAllMessageBuildingStatusConstants();
    List<ValueDescriptionBean> getAllMessageTypeConstants();
}
