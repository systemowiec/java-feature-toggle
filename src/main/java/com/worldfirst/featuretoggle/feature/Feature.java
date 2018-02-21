package com.worldfirst.featuretoggle.feature;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "features")
public class Feature {

    @Id
    private String id;
    private String description;
    private LocalDateTime creationTime = LocalDateTime.now();

    public Feature() {
    }

    public Feature(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}