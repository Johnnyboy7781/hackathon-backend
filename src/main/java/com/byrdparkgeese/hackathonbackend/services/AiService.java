package com.byrdparkgeese.hackathonbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Message;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format.Schema;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format.Schema.Properties;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format.Schema.Properties.Property;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptResponse;

@Service
public class AiService {
    
    private String chatgptUrl = "https://api.openai.com/v1/responses";

    @Value("${chatgpt.api_key}")
    private String chatgptApiKey;

    @Autowired
    private RestTemplate restTemplate;

    public ChatGptResponse callChatgpt(String message) {
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer %s".formatted(chatgptApiKey));
        requestHeaders.add("Content-Type", "application/json");

        Message systemInput = new Message("system",  "Extract the address and issue description. If you cannot extract an address, add a reply to the user telling them that you need an address. Act as a public servant and be very nice.");
        Message userInput = new Message("user", message);

        var requestBody = new ChatGptRequestBody(
            "gpt-5-nano", 
            new Message[]{ systemInput, userInput }, 
            new Text(
                new Format(
                    "json_schema", 
                    "parsedData",
                    new Schema(
                        "object",
                        new Properties(
                            new Property("string"), 
                            new Property("string"), 
                            new Property("string")
                        ),
                        new String[]{"reply", "address", "issueDescription"},
                        false
                    )
                )
            )
        );

        var httpEntity = new HttpEntity<>(requestBody, requestHeaders);

        var response = restTemplate.exchange(
            chatgptUrl,
            HttpMethod.POST,
            httpEntity,
            ChatGptResponse.class
        );

        return response.getBody();
    }
}
