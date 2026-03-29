package com.byrdparkgeese.hackathonbackend.controllers;

import com.byrdparkgeese.hackathonbackend.data.entities.ConversationsEntity;
import com.byrdparkgeese.hackathonbackend.data.entities.UsersEntity;
import com.byrdparkgeese.hackathonbackend.data.records.GeocodingResponse;
import com.byrdparkgeese.hackathonbackend.data.records.GetReportsData;
import com.byrdparkgeese.hackathonbackend.data.repositories.ConversationsRepository;
import com.byrdparkgeese.hackathonbackend.data.repositories.RepliesByTypeRepository;
import com.byrdparkgeese.hackathonbackend.data.repositories.UsersRepository;
import com.byrdparkgeese.hackathonbackend.services.*;

import java.util.List;

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
    private RepliesByTypeRepository repliesTypeRepo;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AxiomService axiomService;

    public void handleReportableTextMessage(TextMessageData payload, ConversationsEntity conversation) { 
        var message = payload.message().toLowerCase().trim();

        System.out.println(message);
        
        if (message.equalsIgnoreCase("no")) {
            conversation.setStatus("Closed");
            conversationsRepository.save(conversation);
            return;
        }

        if (!message.equalsIgnoreCase("yes")) {
            textService.sendText(payload.sender(), "Please reply YES if you want to submit a report, else reply NO to cancel");
            return;
        }

        var category = aiService.callAiToClassifyWorkType(conversation.getIssueDesc()).category();
        
        var dbCategory = repliesTypeRepo.findByCategory(category);

        textService.sendText(payload.sender(), dbCategory.getReplyText());

        var optionsList = List.of("12345", conversation.getAddress(), conversation.getIssueDesc());

        if (dbCategory.getCategory() == "Pothole On Road") {
            optionsList.add("Pothole");
            optionsList.add("0");
            optionsList.add("0");
        } else if (dbCategory.getCategory() == "Repair Sidewalk Or Ramp") {
            optionsList.add("Sidewalk");
            optionsList.add("0");
            optionsList.add("0");
            optionsList.add("ArrowDown||Enter");
        } else {
            return;
        }

        axiomService.triggerAxiomHooks(optionsList);
    }

    @PostMapping("/webhook")
    public void handleTextMessage(@RequestBody TextMessageData payload) {
        System.out.println("Received a text!");

        UsersEntity usersEntity = conversationService.loadOrCreateUserEntity(payload.sender());

        if (usersEntity.id != null) {
            var conversationOptional = conversationService.loadConversationIfExists(usersEntity.id, "Reportable");
                if (conversationOptional.isPresent()) {
                    var convo = conversationOptional.get();
                    handleReportableTextMessage(payload, convo);
                    return;
                }
        }

        var res = aiService.callAiToGatherInitialInfo(payload);

        usersEntity.setPreviousResponseId("12345");
        conversationService.saveUser(usersEntity);

        ConversationsEntity conversations = conversationService.loadOrCreateConversation(usersEntity.id, "Open");

        if (!res.address().isEmpty()) {
            conversations.setAddress(res.address());
        } else if (conversations.getAddress() == null) {
            conversations.setAddress("");
        }
        if (!res.issueDescription().isEmpty()) {
            System.out.println("RECEIVED: %s".formatted(res.issueDescription()));
            conversations.setIssueDesc(res.issueDescription());
        } else if (conversations.getIssueDesc() == null) {
            conversations.setIssueDesc("");
        }
        conversations.setStatus("Open");
        conversations.setUsers_id(usersEntity.id);
        conversationsRepository.save(conversations);

        if (conversations.getAddress().isEmpty() || conversations.getIssueDesc().isEmpty()) {
            textService.sendText(payload.sender(), res.reply());
            return;
        }

        GeocodingResponse geocodingResponse = geocodingService.getLatLongFromAddress(conversations.getAddress());
        conversations.setStatus("Reportable");
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
