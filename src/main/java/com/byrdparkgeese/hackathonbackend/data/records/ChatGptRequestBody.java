package com.byrdparkgeese.hackathonbackend.data.records;

public record ChatGptRequestBody<T>(
    String model,
    Message[] input,
    Text<T> text
) {
    public record Message(
        String role,
        String content
    ) {}

    public record Text<T>(
        Format<T> format
    ) {
        public record Format<T>(
            String type,
            String name,
            Schema<T> schema
        ) {
            public record Schema<T>(
                String type,
                T properties,
                String[] required,
                Boolean additionalProperties
            ) {
                public record Bot1Properties(
                    Property reply,
                    Property address,
                    Property issueDescription
                ) {
                    public record Property(
                        String type
                    ) {}
                }
                public record Bot2Properties(
                    Property category
                ) {
                    public record Property(
                        String type
                    ) {}
                }
            }
        }
    }
}
