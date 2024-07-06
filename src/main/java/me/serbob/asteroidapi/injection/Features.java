package me.serbob.asteroidapi.injection;

import java.util.ArrayList;
import java.util.List;

public class Features {
    private final List<Feature> features = new ArrayList<>();

    public void addFeature(Feature feature) {
        features.add(feature);
    }

    public void removeFeature(Feature feature) {
        features.remove(feature);
    }

    public List<Feature> getFeatures() {
        return features;
    }
}
