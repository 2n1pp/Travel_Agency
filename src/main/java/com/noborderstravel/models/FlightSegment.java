package com.noborderstravel.models;

import java.util.HashMap;
import java.util.Map;

public class FlightSegment {

    private Aircraft aircraft;
    private Arrival arrival;
    private String carrierCode;
    private Departure departure;
    private String duration;
    private String number;
    private Operating operating;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Operating getOperating() {
        return operating;
    }

    public void setOperating(Operating operating) {
        this.operating = operating;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "FlightSegment{" +
                "aircraft=" + aircraft +
                ", arrival=" + arrival +
                ", carrierCode='" + carrierCode + '\'' +
                ", departure=" + departure +
                ", duration='" + duration + '\'' +
                ", number='" + number + '\'' +
                ", operating=" + operating +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
