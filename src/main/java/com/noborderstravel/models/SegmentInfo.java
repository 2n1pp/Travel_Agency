package com.noborderstravel.models;

public class SegmentInfo {
    private String from;
    private String to;
    private String departing_time;
    private String  arriving_time;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDeparting_time() {
        return departing_time;
    }

    public void setDeparting_time(String departing_time) {
        this.departing_time = departing_time;
    }

    public String getArriving_time() {
        return arriving_time;
    }

    public void setArriving_time(String arriving_time) {
        this.arriving_time = arriving_time;
    }
}
