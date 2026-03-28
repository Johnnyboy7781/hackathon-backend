package com.byrdparkgeese.hackathonbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.byrdparkgeese.hackathonbackend.data.records.GeocodingResponse;

@Service
public class GeocodingService {
    
    private String geoapifyUrl = "https://api.geoapify.com/v1/geocode/search?text=%s&apiKey=%s";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${geoapify.api_key}")
    private String geoapifyApiKey;

    public GeocodingResponse getLatLongFromAddress(String address) {
        String fullUrl = geoapifyUrl.formatted(address, geoapifyApiKey);

        var res = restTemplate.getForEntity(fullUrl, GeocodingResponse.class);

        return res.getBody();
    }

}
