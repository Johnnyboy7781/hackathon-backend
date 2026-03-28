package com.byrdparkgeese.hackathonbackend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookConsumerController {
    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody Object payload) {
        System.out.println("Received a webhook request");
        System.out.println(payload);
    }
}
