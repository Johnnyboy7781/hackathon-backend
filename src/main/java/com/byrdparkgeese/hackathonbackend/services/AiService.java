package com.byrdparkgeese.hackathonbackend.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Message;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format.Schema;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format.Schema.Properties;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptRequestBody.Text.Format.Schema.Properties.Property;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptResponse;
import com.byrdparkgeese.hackathonbackend.data.records.ChatGptParsedData;
import com.byrdparkgeese.hackathonbackend.data.Constants;

@Service
public class AiService {
    
    private String chatgptUrl = "https://api.openai.com/v1/responses";

    @Value("${chatgpt.api_key}")
    private String chatgptApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;

    private ChatGptResponse callChatgpt(String message, ChatGptRequestBody requestBody) {
        var requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer %s".formatted(chatgptApiKey));
        requestHeaders.add("Content-Type", "application/json");

        var httpEntity = new HttpEntity<>(requestBody, requestHeaders);

        var response = restTemplate.exchange(
            chatgptUrl,
            HttpMethod.POST,
            httpEntity,
            ChatGptResponse.class
        );

        return response.getBody();
    }

    public ChatGptParsedData callAiToGatherInitialInfo(String userMessage) {
        Message systemInput = new Message("system", Constants.AI_INSTRUCTIONS_INITIAL_INFORMATION_GATHER);
        Message userInput = new Message("user", userMessage);

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

        var response = callChatgpt(userMessage, requestBody);

        return parseResponse(response.output().get(1).content().get(0).text());
    }

    private ChatGptParsedData parseResponse(String responseString) {
        ChatGptParsedData parsed = null;

        try {
            Map<String, String> jsonMap = mapper.readValue(responseString, Map.class);

            parsed = new ChatGptParsedData(
                jsonMap.get("reply"),
                jsonMap.get("address"),
                jsonMap.get("issueDescription")
            );
        } catch(JsonProcessingException err) {
            System.out.println("Failed to parse response from ChatGPT: ".formatted(err.getMessage()));
            err.printStackTrace();
        }

        return parsed;
    }
}
