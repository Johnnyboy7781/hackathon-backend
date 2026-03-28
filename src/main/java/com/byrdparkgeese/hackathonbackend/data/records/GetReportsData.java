package com.byrdparkgeese.hackathonbackend.data.records;

public record GetReportsData(
    Report[] data
) {
    public record Report(
        String id,
        String serviceId,
        String serviceName,
        String serviceGroup,
        String serviceGroupId,
        String description,
        String requestDate,
        Integer status,
        Long latitude,
        Long longitude,
        String location,
        String icon,
        Integer votes,
        Boolean hasSubscribed,
        Boolean upvoteEnabled,
        Boolean hasUpvoted,
        Integer numberOfComments,
        Integer numberOfAttachments,
        Integer numberOfImages,
        Boolean ownedByMe,
        Boolean enabledServiceType,
        String activatedService,
        String activatedServiceGroup,
        String activatedServiceGroupName,
        Field[] level1Fields,
        Field[] level2Fields,
        Field[] level3Fields
    ) {
        public record Field(
            String fieldName,
            String fieldValue
        ) {}
    }
}
