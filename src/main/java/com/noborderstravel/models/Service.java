package com.noborderstravel.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private List<Segment> segments = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Service{" +
                "segments=" + segments +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}

