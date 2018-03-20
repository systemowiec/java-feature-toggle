package com.systemowiec.featuretoggle.feature;

import com.systemowiec.featuretoggle.feature.exception.FeatureDeletedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "features")
public class Feature {

    @Id
    private String id;
    private String description;

    @Column(name = "creation_time", columnDefinition = "datetime") // set the name for the column and database type
    private LocalDateTime creationTime = LocalDateTime.now();

    @Column(name = "deletion_time", columnDefinition = "datetime")
    private LocalDateTime deletionTime = null;

    public Feature() {
    }

    public Feature(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getDeletionTime() {
        return deletionTime;
    }

    public void updateDescription(String newDescription) throws FeatureDeletedException {
        guardAgainstDeleted();

        description = newDescription;
    }

    public void updateDescription(char[] newDescription) throws FeatureDeletedException {
        guardAgainstDeleted();

        description = String.valueOf(newDescription);
    }

    public void delete() throws FeatureDeletedException {
        guardAgainstDeleted();

        deletionTime = LocalDateTime.now();
    }

    private void guardAgainstDeleted() throws FeatureDeletedException {
        if (isDeleted()) {
            throw new FeatureDeletedException();
        }
    }

    private boolean isDeleted() {
        return deletionTime != null;
    }
}