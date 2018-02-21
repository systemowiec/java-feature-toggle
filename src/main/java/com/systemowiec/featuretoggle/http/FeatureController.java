package com.systemowiec.featuretoggle.http;

import com.systemowiec.featuretoggle.feature.Feature;
import com.systemowiec.featuretoggle.feature.FeatureRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class FeatureController {

    private FeatureRepository featureRepository;

    public FeatureController(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @GetMapping("/api/features")
    public Iterable<Feature> findAll() {
        return featureRepository.findAll();
    }

    @PostMapping("/api/features")
    public void create() {
        Feature feature = new Feature(
                UUID.randomUUID().toString(),
                "Random Feature"
        );

        featureRepository.save(feature);
    }
}