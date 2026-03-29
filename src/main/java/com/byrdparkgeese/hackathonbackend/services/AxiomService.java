package com.byrdparkgeese.hackathonbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.records.AxiomRequestBody;

@Service
public class AxiomService {

    private String axiomUrl = "https://lar.axiom.ai/api/v3/trigger";
    private String name = "Geese Pothole Filler";

    @Value("${axiom.api_key}")
    private String axiomApiKey;

    @Autowired
    RestTemplate restTemplate;

    public void triggerAxiomHooks(List<String> options) {
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");

        var requestBody = new AxiomRequestBody(
            axiomApiKey, 
            name, 
            List.of(
                options
            )
        );

        var httpEntity = new HttpEntity<>(requestBody, requestHeaders);

        restTemplate.exchange(
            axiomUrl,
            HttpMethod.POST,
            httpEntity,
            Object.class
        );
    }
}
