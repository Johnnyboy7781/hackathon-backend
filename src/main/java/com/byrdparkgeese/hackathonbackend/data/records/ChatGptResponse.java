package com.byrdparkgeese.hackathonbackend.data.records;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record ChatGptResponse(
    @NonNull String message,
    @Nullable ParsedData parsedData
) {
    public record ParsedData(
        @NonNull String address,
        @Nullable String issueDescription
    ) {}
}
