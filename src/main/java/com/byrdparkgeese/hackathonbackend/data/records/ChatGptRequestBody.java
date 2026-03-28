package com.byrdparkgeese.hackathonbackend.data.records;

import jakarta.annotation.Nullable;

public record ChatGptRequestBody(
    String model,
    Message[] input,
    Text text
) {
    public record Message(
        String role,
        String content
    ) {}

    public record Text(
        Format format
    ) {
        public record Format(
            String type,
            String name,
            Schema schema
        ) {
            public record Schema(
                String type,
                Properties properties,
                String[] required,
                Boolean additionalProperties
            ) {
                public record Properties(
                    Property reply,
                    Property address,
                    Property issueDescription
                ) {
                    public record Property(
                        String type
                    ) {}
                }
            }
        }
    }
}
