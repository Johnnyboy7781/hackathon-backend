package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.data.records.TextMessageData;
import com.byrdparkgeese.hackathonbackend.services.TextService;
import com.byrdparkgeese.hackathonbackend.services.AiService;

@RestController
public class TextHookController {

    @Autowired
    TextService textService;

    @Autowired
    AiService aiService;

    @PostMapping("/webhook")
    public void handleTextMessage(@RequestBody TextMessageData payload) {
        System.out.println("Received a text!");
        var res = aiService.callChatgpt(payload.message());

        if (res != null) {
            textService.sendText(payload.sender(), res.reply());
        } else {
            System.out.println("Aborting sending text message, received no data");
        }
    }
}
