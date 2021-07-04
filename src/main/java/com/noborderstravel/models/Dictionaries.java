package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class Dictionaries {

    private Map<String, String> carriers = new HashMap<String, String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, String> getCarriers() {
        return this.carriers;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Dictionaries{" +
                "carriers=" + carriers +
                '}';
    }
}
