package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.services.AiService;
import com.byrdparkgeese.hackathonbackend.services.GeocodingService;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import com.byrdparkgeese.hackathonbackend.services.Rva311Service;
import com.byrdparkgeese.hackathonbackend.services.TextService;

@RestController
public class TestController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    Rva311Service rva311Service;

    @Autowired
    AiService aiService;

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    TextService textService;

    @GetMapping("/")
    public Object hello() {
        return geocodingService.getLatLongFromAddress("200 N Boulevard, Richmond, VA 23220").features().get(0).properties();
    }

}
