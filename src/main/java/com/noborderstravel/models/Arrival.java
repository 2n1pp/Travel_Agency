package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class Arrival {

    private String at;
    private String iataCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "at='" + at + '\'' +
                ", iataCode='" + iataCode + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
