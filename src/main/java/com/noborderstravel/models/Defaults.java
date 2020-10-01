package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class Defaults {
    private Integer adults;
    private Boolean nonStop;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Boolean getNonStop() {
        return nonStop;
    }

    public void setNonStop(Boolean nonStop) {
        this.nonStop = nonStop;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
