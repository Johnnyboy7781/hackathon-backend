package com.byrdparkgeese.hackathonbackend.data.records;

public record ChatGptParsedData(
    String reply,
    String address,
    String issueDescription
) {}
