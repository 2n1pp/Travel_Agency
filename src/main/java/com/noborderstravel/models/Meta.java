package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class Meta {
    private String currency;
    private Defaults defaults;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Defaults getDefaults() {
        return defaults;
    }

    public void setDefaults(Defaults defaults) {
        this.defaults = defaults;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
