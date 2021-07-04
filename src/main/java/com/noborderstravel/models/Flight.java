package com.noborderstravel.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flight {

    private List<OfferItem> data = null;
    private Dictionaries dictionaries;
    private Meta meta;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<OfferItem> getData() {
        return data;
    }

    public void setData(List<OfferItem> data) {
        this.data = data;
    }

    public Dictionaries getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Dictionaries dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    @Override
    public String toString() {
        return "Flight{" +
                "data=" + data +
                ", dictionaries=" + dictionaries +
                ", meta=" + meta +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
