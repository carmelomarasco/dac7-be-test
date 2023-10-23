package it.finanze.entrate.coopint.dboard.dpi.com.command.service;

//import it.finanze.entrate.coopint.dboard.dpi.common.bean.CountryServiceBean;


public interface DpiRestTemplateService {
    //List<CountryServiceBean> getPartecipatingCountries();

    //List<CountryServiceBean> getFathersAndSonsCountries();

    Boolean checkComHasMsgBuilt(String comUuid);

    String retrieveDPICommunicationByProtocol(String protocol);
}
