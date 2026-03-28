package com.byrdparkgeese.hackathonbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.records.TextSendRequestBody;

@Service
public class TextService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${textbee.api_key}")
    private String textBeeApiKey;

    private String textBeeUrl = "https://api.textbee.dev/api/v1/gateway/devices/69c73b4c5763e875d5d00315/send-sms";

    public void sendText(String recipientNumber, String message) {
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("x-api-key", textBeeApiKey);

        var requestBody = new TextSendRequestBody(
            new String[] { recipientNumber },
            message
        );

        var httpEntity = new HttpEntity<Object>(requestBody, requestHeaders);

        try {
            restTemplate.exchange(
                textBeeUrl,
                HttpMethod.POST,
                httpEntity,
                Object.class
            );
        } catch(RuntimeException err) {
            System.out.println("Failed to send message: {}".formatted(err.getMessage()));
            err.printStackTrace();
        }
    }

}
