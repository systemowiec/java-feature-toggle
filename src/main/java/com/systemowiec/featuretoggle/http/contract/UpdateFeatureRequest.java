package com.systemowiec.featuretoggle.http.contract;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UpdateFeatureRequest {
    private final String description;

    @JsonCreator
    public UpdateFeatureRequest(@JsonProperty("description") String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}