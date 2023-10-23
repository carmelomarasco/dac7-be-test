package it.finanze.entrate.coopint.dboard.dpi.utils.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class HttpClient {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<?> doCall(String url, HttpMethod method, ParameterizedTypeReference<?> parameterizedTypeReference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, method, entity, parameterizedTypeReference);

    }
}
