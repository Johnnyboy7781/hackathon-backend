package com.byrdparkgeese.hackathonbackend.controllers;

import com.byrdparkgeese.hackathonbackend.data.records.TextMessageData;
import com.byrdparkgeese.hackathonbackend.services.GeocodingService;
import com.byrdparkgeese.hackathonbackend.services.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.services.AiService;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import com.byrdparkgeese.hackathonbackend.services.Rva311Service;

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
        TextMessageData TextMessageData = new TextMessageData(
                null,
                "Help there's a tree down! I dont know the message",
                null,
                null,
                null,
                null,
                "8041234567",
                null
        );
        return aiService.callAiToGatherInitialInfo(TextMessageData , "");
    }

}
