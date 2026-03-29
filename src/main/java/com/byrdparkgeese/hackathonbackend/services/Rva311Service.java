package com.byrdparkgeese.hackathonbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.records.GetReportsData;
import com.byrdparkgeese.hackathonbackend.data.records.GetReportsRequestBody;

@Service
public class Rva311Service {

    String rva311Url = "https://webapi.citizenservices.org/rvaone/api/v1/requests";

    @Autowired
    RestTemplate restTemplate;

    public GetReportsData getReports() {
        var requestBody = new GetReportsRequestBody(
            "1774646961246", 
            "requestDate", 
            "desc", 
            1, 
            "1766870961246",
            null,
            null,
            null
        );

        ResponseEntity<GetReportsData> responseEntity = restTemplate.postForEntity(
            rva311Url,
            requestBody,
            GetReportsData.class
        );

        return responseEntity.getBody();
    }

    public GetReportsData getReportWithRadius(
            double latitude,
            double longitude
    )
    {
        String url = rva311Url + "/query?latitude=" + String.valueOf(latitude) +
                "&longitude=" + String.valueOf(longitude) +
                "&radius=100&status=1&status=2&status=3&status=4";

        ResponseEntity<GetReportsData> responseEntity = restTemplate.getForEntity(
                url,
                GetReportsData.class
        );

        return responseEntity.getBody();
    }
}
