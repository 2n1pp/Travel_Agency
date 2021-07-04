package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class Segment {
    private FlightSegment flightSegment;
    private PricingDetailPerAdult pricingDetailPerAdult;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public FlightSegment getFlightSegment() {
        return flightSegment;
    }

    public void setFlightSegment(FlightSegment flightSegment) {
        this.flightSegment = flightSegment;
    }

    public PricingDetailPerAdult getPricingDetailPerAdult() {
        return pricingDetailPerAdult;
    }

    public void setPricingDetailPerAdult(PricingDetailPerAdult pricingDetailPerAdult) {
        this.pricingDetailPerAdult = pricingDetailPerAdult;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Segment{" +
                "flightSegment=" + flightSegment +
                ", pricingDetailPerAdult=" + pricingDetailPerAdult +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
