package com.noborderstravel.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferItem {

    private Price price;
    private PricePerAdult pricePerAdult;
    private PricePerInfant pricePerInfant;
    private PricePerChild pricePerChild;
    private List<Service> services = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public PricePerAdult getPricePerAdult() {
        return pricePerAdult;
    }

    public void setPricePerAdult(PricePerAdult pricePerAdult) {
        this.pricePerAdult = pricePerAdult;
    }

    public PricePerInfant getPricePerInfant() { return pricePerInfant; }

    public void setPricePerInfant(PricePerInfant pricePerInfant) { this.pricePerInfant = pricePerInfant; }

    public PricePerChild getPricePerChild() { return pricePerChild; }

    public void setPricePerChild(PricePerChild pricePerChild) { this.pricePerChild = pricePerChild; }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "OfferItem{" +
                "price=" + price +
                ", pricePerAdult=" + pricePerAdult +
                ", services=" + services +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
