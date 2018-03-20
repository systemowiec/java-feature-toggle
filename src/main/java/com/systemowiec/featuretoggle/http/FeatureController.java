package com.systemowiec.featuretoggle.http;

import com.systemowiec.featuretoggle.feature.Feature;
import com.systemowiec.featuretoggle.feature.FeatureRepository;
import com.systemowiec.featuretoggle.feature.exception.FeatureDeletedException;
import com.systemowiec.featuretoggle.http.contract.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/features")
    public void create(@RequestBody CreateFeatureRequest request) {
        Feature feature = new Feature(
                request.getId(),
                request.getDescription()
        );

        featureRepository.save(feature);
    }

    @GetMapping("/api/features/{featureId}")
    public Feature getOne(@PathVariable("featureId") String featureId) {
        Feature feature = featureRepository.findOne(featureId);

        if (feature != null) {
            return feature;
        }

        throw new EntityNotFoundException();
    }

    @PutMapping("/api/features/{featureId}")
    public Feature updateFeature(
            @PathVariable("featureId") String featureId,
            @RequestBody UpdateFeatureRequest request
    ) {
        Feature feature = featureRepository.findOne(featureId);

        if (feature != null) {
            try {
                feature.updateDescription(request.getDescription());
            } catch (FeatureDeletedException e) {
                throw new EntityNotFoundException();
            }
            featureRepository.save(feature);

            return feature;
        }

        throw new EntityNotFoundException();
    }

    @RequestMapping(
            path = "/api/features/{featureId}",
            method = RequestMethod.DELETE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeature(@PathVariable("featureId") String featureId) {

        Feature feature = featureRepository.findOne(featureId);

        if (feature == null) {
            throw new EntityNotFoundException();
        }

        try {
            feature.delete();
        } catch (FeatureDeletedException e) {
            throw new UnprocessableEntityException();
        }

        featureRepository.save(feature);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class) // .class has type Class<?>
    public ErrorResponse handleInvalidJson() {
        return new ErrorResponse("Bad JSON");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class) // .class has type Class<?>
    public ErrorResponse unicornsAndBunnies() {
        return new ErrorResponse("Object not found");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public ErrorResponse unprocessableEntity() {
        return new ErrorResponse("Change in entity could not be applied");
    }
}