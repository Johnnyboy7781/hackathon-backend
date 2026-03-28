package com.byrdparkgeese.hackathonbackend.data.records;

public record GetReportsRequestBody(
    String end,
    String orderBy,
    String orderDirection,
    Integer pageNumber,
    String start
) {}
