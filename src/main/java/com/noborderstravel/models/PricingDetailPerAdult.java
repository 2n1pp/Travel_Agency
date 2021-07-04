package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class PricingDetailPerAdult {
    private Integer availability;
    private String fareBasis;
    private String fareClass;
    private String travelClass;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getFareBasis() {
        return fareBasis;
    }

    public void setFareBasis(String fareBasis) {
        this.fareBasis = fareBasis;
    }

    public String getFareClass() {
        return fareClass;
    }

    public void setFareClass(String fareClass) {
        this.fareClass = fareClass;
    }

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "PricingDetailPerAdult{" +
                "availability=" + availability +
                ", fareBasis='" + fareBasis + '\'' +
                ", fareClass='" + fareClass + '\'' +
                ", travelClass='" + travelClass + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
