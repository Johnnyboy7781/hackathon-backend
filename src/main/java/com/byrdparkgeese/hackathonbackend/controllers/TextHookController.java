package com.byrdparkgeese.hackathonbackend.controllers;

import com.byrdparkgeese.hackathonbackend.data.entities.ConversationsEntity;
import com.byrdparkgeese.hackathonbackend.data.entities.UsersEntity;
import com.byrdparkgeese.hackathonbackend.data.records.GeocodingResponse;
import com.byrdparkgeese.hackathonbackend.data.repositories.ConversationsRepository;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import com.byrdparkgeese.hackathonbackend.services.ConversationService;
import com.byrdparkgeese.hackathonbackend.services.GeocodingService;
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

    @Autowired
    ConversationService conversationService;

    @Autowired
    GeocodingService geocodingService;

    private ConversationsRepository conversationsRepository;

    private UsersRepository usersRepository;

    @PostMapping("/webhook")
    public void handleTextMessage(@RequestBody TextMessageData payload) {
        System.out.println("Received a text!");

        UsersEntity usersEntity = conversationService.loadOrCreateUserEntity(payload.sender());
        String previous_response_id = "";

        if( usersEntity.getPreviousResponseId() != null) {
            previous_response_id = usersEntity.getPreviousResponseId();
        }
        var res = aiService.callAiToGatherInitialInfo(payload, previous_response_id);

        usersEntity.setPreviousResponseId(res.previous_response_id());
        conversationService.saveUser(usersEntity);

        ConversationsEntity conversations = conversationService.loadConversation("Open");
        if (res.address().isEmpty() && res.issueDescription().isEmpty()){
           textService.sendText(payload.sender(), res.reply());
        } else {
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
        }

        if (res == null) {
            System.out.println("Aborting sending text message, received no data");
        }
    }
}
