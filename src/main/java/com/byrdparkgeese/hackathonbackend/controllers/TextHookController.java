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

    private ConversationsRepository conversationsRepository;

    private UsersRepository usersRepository;

    @PostMapping("/webhook")
    public void handleTextMessage(@RequestBody TextMessageData payload) {
        System.out.println("Received a text!");

        UsersEntity usersEntity = conversationService.loadOrCreateUserEntity(payload.sender());
        String previous_response_id = "";

        if (usersEntity.getPreviousResponseId() != null) {
            previous_response_id = usersEntity.getPreviousResponseId();
        }
        var res = aiService.callAiToGatherInitialInfo(payload, previous_response_id);

        usersEntity.setPreviousResponseId(res.previous_response_id());
        conversationService.saveUser(usersEntity);


        ConversationsEntity conversations = conversationService.loadOrCreateConversation("Open");
        conversations.setAddress(res.address());
        conversations.setIssueDesc(res.issueDescription());
        conversations.setStatus("Open");
        conversations.setUsers_id(usersEntity.id);
        conversationsRepository.save(conversations);

        if (conversations.getAddress().isEmpty() && conversations.getIssueDesc().isEmpty()) {
            textService.sendText(payload.sender(), res.reply());
            return;
        }
        GeocodingResponse geocodingResponse = geocodingService.getLatLongFromAddress(res.address());
        conversations.setUsers_id(usersEntity.id);
        conversations.setStatus("Closed");
        conversations.setAddress(res.address());
        conversations.setLatitude(
                String.valueOf(geocodingResponse.features().get(0).properties().lat())
        );
        conversations.setLongitude(
                String.valueOf(geocodingResponse.features().get(0).properties().lon())
        );
        conversations.setIssueDesc(res.issueDescription());
        conversationsRepository.save(conversations);

        GetReportsData reportsData = rva311Service.getReportWithRadius(
                geocodingResponse.features().get(0).properties().lat(),
                geocodingResponse.features().get(0).properties().lon()
                );
        String formattedReport = textService.formatReportsMessage(reportsData.data());

        textService.sendText(payload.sender(), formattedReport);

        if (res == null) {
            System.out.println("Aborting sending text message, received no data");
        }
    }
}
