package com.byrdparkgeese.hackathonbackend.data.records;

import java.util.List;

public record AxiomRequestBody(
    String key,
    String name,
    List<List<String>> data
) {}
