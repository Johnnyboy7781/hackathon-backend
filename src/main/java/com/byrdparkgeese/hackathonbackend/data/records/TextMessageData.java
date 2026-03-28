package com.byrdparkgeese.hackathonbackend.data.records;

import java.time.LocalDateTime;

public record TextMessageData(
    String smsId,
    String message,
    String deviceId,
    String webhookSubscriptionId,
    String webhookEvent,
    String idempotencyKey,
    String sender,
    LocalDateTime receivedAt
) {}
