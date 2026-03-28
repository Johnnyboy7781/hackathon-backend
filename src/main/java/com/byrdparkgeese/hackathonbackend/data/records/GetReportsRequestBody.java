package com.byrdparkgeese.hackathonbackend.data.records;

import org.springframework.lang.NonNull;

public record GetReportsRequestBody(
    @NonNull String end,
    @NonNull String orderBy,
    @NonNull String orderDirection,
    @NonNull Integer pageNumber,
    @NonNull String start,
    Filter[] dynamicalStringFilters,
    String[] status,
    String[] services
) {
    public record Filter(
        @NonNull String filterName,
        @NonNull String[] filterValues
    ) {}
}
