package com.byrdparkgeese.hackathonbackend.data.records;

public record TextSendRequestBody(
    String[] recipients,
    String message
) {}
