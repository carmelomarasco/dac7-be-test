package it.finanze.entrate.coopint.dboard.dpi.com.command.service.impl;

import it.finanze.entrate.coopint.dboard.dpi.com.command.service.DpiRestTemplateService;
//import it.finanze.entrate.coopint.dboard.dpi.common.bean.CountryServiceBean;
//import oecd.ties.isodpitypes.v1.CountryCodeType;

import it.finanze.entrate.coopint.dboard.dpi.utils.http.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpMethod.GET;

@Service
public class DpiRestTemplateServiceImpl implements DpiRestTemplateService {

    @Value("${url-country-service}")
    private String urlCountryService;

    @Value("${url-dpi-nr-command-service}")
    private String urlDpiNRCommand;

    @Value("${url-boundary-service}")
    private String urlBoundaryService;

    HttpClient httpClient;

    @Autowired
    public DpiRestTemplateServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // ora è su tabella locale
    /*
    @Override
    public List<CountryServiceBean> getPartecipatingCountries() {

        List<CountryServiceBean> countriesList = (List<CountryServiceBean>) httpClient.doCall(urlCountryService + "/find-partecipating-countries?excludeExtraEUCountries=true", GET, new ParameterizedTypeReference<List<CountryServiceBean>>() {
        }).getBody();
        //filter only countries with dataInizioScambio != null & dataFineScambio == null & countryCode != IT
        return countriesList.stream().filter(csb -> null != csb.getDataInizioScambio() && null == csb.getDataFineScambio() && !csb.getCodiceIso().equalsIgnoreCase(CountryCodeType.IT.value())).collect(Collectors.toList());
    }

    @Override
    public List<CountryServiceBean> getFathersAndSonsCountries() {
        return (List<CountryServiceBean>) httpClient.doCall(urlCountryService + "/getCountries", GET, new ParameterizedTypeReference<List<CountryServiceBean>>() {
        }).getBody();
    }
*/
    @Override
    public Boolean checkComHasMsgBuilt(String comUuid) {
        return (Boolean) httpClient.doCall(urlDpiNRCommand + "/dpi-nr-command/checkComMsg/" + comUuid, GET, new ParameterizedTypeReference<Boolean>() {
        }).getBody();
    }

    @Override
    public String retrieveDPICommunicationByProtocol(String protocol) {
        return (String) httpClient.doCall(urlBoundaryService + "/boundaryservice/dpi-communication/getCommunicationByProtocol/" + protocol, GET,
                new ParameterizedTypeReference<String>() {}).getBody();
    }
}
