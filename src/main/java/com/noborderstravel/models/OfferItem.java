package com.noborderstravel.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferItem {

    private Price price;
//    private PricePerAdult pricePerAdult;
//    private PricePerInfant pricePerInfant;
//    private PricePerChild pricePerChild;
    private List<Itineraries> itineraries = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private List<TravelerPricings> travelerPricings = null;

    public List<TravelerPricings> getTravelerPricings() {
        return travelerPricings;
    }

    public void setTravelerPricings(List<TravelerPricings> travelerPricings) {
        this.travelerPricings = travelerPricings;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

//    public PricePerAdult getPricePerAdult() {
//        return pricePerAdult;
//    }
//
//    public void setPricePerAdult(PricePerAdult pricePerAdult) {
//        this.pricePerAdult = pricePerAdult;
//    }
//
//    public PricePerInfant getPricePerInfant() { return pricePerInfant; }
//
//    public void setPricePerInfant(PricePerInfant pricePerInfant) { this.pricePerInfant = pricePerInfant; }
//
//    public PricePerChild getPricePerChild() { return pricePerChild; }
//
//    public void setPricePerChild(PricePerChild pricePerChild) { this.pricePerChild = pricePerChild; }

    public List<Itineraries> getServices() {
        return itineraries;
    }

    public void setServices(List<Itineraries> services) {
        this.itineraries = itineraries;
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
                "itineraries=" + itineraries +
//                "price=" + price +
//                ", pricePerAdult=" + pricePerAdult +
//                ", services=" + services +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
