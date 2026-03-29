package com.byrdparkgeese.hackathonbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.Constants.REPORT_STATUS;
import com.byrdparkgeese.hackathonbackend.data.records.GetReportsData;
import com.byrdparkgeese.hackathonbackend.data.records.GetReportsData.Report;
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

    public String formatReportsMessage(Report[] reportsList) {
       if (reportsList.length == 0) {
           return "Looks like there are no open reports nearby for this issue, reply YES if you would like me to submit a report for you!";
       }

       String formattedMessage = "Below are the following open reports that seem to correspond with your address and issue!\n\n";

        for (Report report : reportsList) {
            System.out.println("Report: %s".formatted(report.location()));
            formattedMessage += "-----\n\n";
            formattedMessage += "Title: %s\n".formatted(report.serviceName());
            formattedMessage += "Description: %s\n".formatted(report.description());
            formattedMessage += "Location: %s\n".formatted(report.location());
            formattedMessage += "Status: %s\n\n".formatted(REPORT_STATUS.getName(report.status()));
        }

        formattedMessage += "-----\n\n";
        formattedMessage += "If none of these seem to correspond with the issue you are reporting, reply YES if you would like me submit a report for you!";

       return formattedMessage;
    }

}
