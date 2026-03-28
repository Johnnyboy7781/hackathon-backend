package com.byrdparkgeese.hackathonbackend.controllers;

import com.byrdparkgeese.hackathonbackend.data.entities.ConversationsEntity;
import com.byrdparkgeese.hackathonbackend.data.entities.UsersEntity;
import com.byrdparkgeese.hackathonbackend.data.records.GeocodingResponse;
import com.byrdparkgeese.hackathonbackend.data.records.GetReportsData;
import com.byrdparkgeese.hackathonbackend.data.repositories.ConversationsRepository;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import com.byrdparkgeese.hackathonbackend.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.byrdparkgeese.hackathonbackend.data.records.TextMessageData;

@RestController
public class TextHookController {

    @Autowired
    TextService textService;

    @Autowired
    AiService aiService;

    @Autowired
    ConversationService conversationService;

    @Autowired
    GeocodingService geocodingService;

    @Autowired
    Rva311Service rva311Service;

    @Autowired
    private ConversationsRepository conversationsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/webhook")
    public void handleTextMessage(@RequestBody TextMessageData payload) {
        System.out.println("Received a text!");

        UsersEntity usersEntity = conversationService.loadOrCreateUserEntity(payload.sender());

        var res = aiService.callAiToGatherInitialInfo(payload);

        usersEntity.setPreviousResponseId("12345");
        conversationService.saveUser(usersEntity);

        ConversationsEntity conversations = conversationService.loadOrCreateConversation(usersEntity.id, "Open");

        if (!res.address().isEmpty()) {
            conversations.setAddress(res.address());
        }
        if (!res.issueDescription().isEmpty()) {
            conversations.setIssueDesc(res.issueDescription());
        }
        conversations.setStatus("Open");
        conversations.setUsers_id(usersEntity.id);
        conversationsRepository.save(conversations);

        if (conversations.getAddress().isEmpty() || conversations.getIssueDesc().isEmpty()) {
            textService.sendText(payload.sender(), res.reply());
            return;
        }

        GeocodingResponse geocodingResponse = geocodingService.getLatLongFromAddress(res.address());
        conversations.setStatus("Closed");
        conversations.setLatitude(
                String.valueOf(geocodingResponse.features().get(0).properties().lat())
        );
        conversations.setLongitude(
                String.valueOf(geocodingResponse.features().get(0).properties().lon())
        );
        conversationsRepository.save(conversations);

        GetReportsData reportsData = rva311Service.getReportWithRadius(
                geocodingResponse.features().get(0).properties().lat(),
                geocodingResponse.features().get(0).properties().lon()
                );
        String formattedReport = textService.formatReportsMessage(reportsData.data());

        textService.sendText(payload.sender(), formattedReport);
    }
}
