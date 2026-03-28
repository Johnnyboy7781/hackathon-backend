package com.byrdparkgeese.hackathonbackend.data.records;

import java.util.List;
import java.util.Map;

public record ChatGptResponse(
        String id,
        String object,
        long created_at,
        String status,
        boolean background,
        Billing billing,
        Long completed_at,
        Object error,
        double frequency_penalty,
        Object incomplete_details,
        Object instructions,
        Object max_output_tokens,
        Object max_tool_calls,
        String model,
        List<Output> output,
        boolean parallel_tool_calls,
        double presence_penalty,
        String previous_response_id,
        Object prompt_cache_key,
        Object prompt_cache_retention,
        Reasoning reasoning,
        Object safety_identifier,
        String service_tier,
        boolean store,
        double temperature,
        Text text,
        String tool_choice,
        List<Object> tools,
        double top_logprobs,
        double top_p,
        String truncation,
        Usage usage,
        Object user,
        Map<String, Object> metadata
) {

    public record Billing(String payer) {}

    public record Reasoning(String effort, Object summary) {}

    public record Usage(
            int input_tokens,
            InputTokensDetails input_tokens_details,
            int output_tokens,
            OutputTokensDetails output_tokens_details,
            int total_tokens
    ) {
        public record InputTokensDetails(int cached_tokens) {}
        public record OutputTokensDetails(int reasoning_tokens) {}
    }

    public record Output(
            String id,
            String type,
            String status,
            List<Content> content,
            List<Object> summary,
            String role
    ) {}

    public record Content(
            String type,
            List<Object> annotations,
            List<Object> logprobs,
            String text
    ) {}

    public record Text(
            Format format,
            String verbosity
    ) {}

    public record Format(
            String type,
            String description,
            String name,
            Schema schema,
            Boolean strict
    ) {}

    public record Schema(
            String type,
            Map<String, SchemaProperty> properties,
            List<String> required,
            boolean additionalProperties
    ) {}

    public record SchemaProperty(
            String type
    ) {}
}
