package com.noborderstravel.models;

import java.util.List;

public class Itineraries {

    private String duration;
    private List<FlightSegment> segments = null;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<FlightSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<FlightSegment> segments) {
        this.segments = segments;
    }

    @Override
    public String toString() {
        return "Itineraries{" +
                "duration='" + duration + '\'' +
                ", segments=" + segments +
                '}';
    }
}
