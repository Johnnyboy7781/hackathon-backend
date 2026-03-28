package com.byrdparkgeese.hackathonbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.records.ChatGptResponse;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptResponse.ParsedData;

public class AiService {
    
    private String chatgptUrl = "";

    @Autowired
    RestTemplate restTemplate;

    public ChatGptResponse callChatgpt() {
        return new ChatGptResponse("hey bitch", new ParsedData("2606 Park ave", null));
    }
}
