package it.finanze.entrate.coopint.dboard.dpi.nr.command.service.impl;

import it.finanze.entrate.coopint.dboard.dpi.utils.http.HttpClient;
import it.finanze.entrate.coopint.dboard.dpi.nr.command.service.Dac7NRRestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Dac7NRRestTemplateServiceImpl implements Dac7NRRestTemplateService {

    @Value("${url-exchange-view-service}")
    private String urlExchangeView;

    @Autowired
    HttpClient httpClient;

    @Override
    public Date getDownloadedMessageDate(String msgUuid) {
        return (Date) httpClient.doCall(urlExchangeView.concat("/exchange-crs-view/getDownloadedDate/{msgUuid}").replace("{msgUuid}", msgUuid), HttpMethod.GET, new ParameterizedTypeReference<Date>() {
        }).getBody();
    }
}
