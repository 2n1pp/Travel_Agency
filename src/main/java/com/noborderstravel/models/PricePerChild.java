package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class PricePerChild {
    private String total;
    private String totalTaxes;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(String totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
