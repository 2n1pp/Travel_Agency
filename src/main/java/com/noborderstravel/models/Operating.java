package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class Operating {
    private String carrierCode;
    private String number;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Operating{" +
                "carrierCode='" + carrierCode + '\'' +
                ", number='" + number + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
