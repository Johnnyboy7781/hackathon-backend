package com.byrdparkgeese.hackathonbackend.data.records;

import java.util.List;

public record GeocodingResponse(
        String type,
        List<Feature> features,
        Query query
) {

    public record Feature(
            String type,
            Geometry geometry,
            Properties properties
    ) {}

    public record Geometry(
            String type,
            List<Double> coordinates
    ) {}

    public record Properties(
            String country_code,
            String housenumber,
            String street,
            String country,
            String county,
            Datasource datasource,
            String postcode,
            String state,
            String state_code,
            String county_code,
            String city,
            String suburb,
            double lon,
            double lat,
            String result_type,
            String formatted,
            String address_line1,
            String address_line2,
            Timezone timezone,
            String plus_code,
            String plus_code_short,
            String iso3166_2,
            Rank rank,
            String place_id
    ) {}

    public record Datasource(
            String sourcename,
            String attribution,
            String license,
            String url
    ) {}

    public record Timezone(
            String name,
            String offset_STD,
            int offset_STD_seconds,
            String offset_DST,
            int offset_DST_seconds,
            String abbreviation_STD,
            String abbreviation_DST
    ) {}

    public record Rank(
            double popularity,
            double confidence,
            double confidence_city_level,
            double confidence_street_level,
            double confidence_building_level,
            String match_type
    ) {}

    public record Query(
            String text,
            Parsed parsed
    ) {}

    public record Parsed(
            String housenumber,
            String street,
            String postcode,
            String city,
            String state,
            String expected_type
    ) {}
}
