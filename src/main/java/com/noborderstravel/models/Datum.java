package com.noborderstravel.models;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Datum {

    private String id;
    private List<OfferItem> offerItems = null;
    private String type;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OfferItem> getOfferItems() {
        return offerItems;
    }

    public void setOfferItems(List<OfferItem> offerItems) {
        this.offerItems = offerItems;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String json = gson.toJson(offerItems);
        return json;
    }
}
