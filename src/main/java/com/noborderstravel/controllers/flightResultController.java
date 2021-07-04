package com.noborderstravel.controllers;

import com.google.gson.Gson;
import com.noborderstravel.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.noborderstravel.services.durationCalculator;
import java.util.ArrayList;
import java.util.List;

public class flightResultController {
    @FXML private Label air_comp_name;
    @FXML private Label first_departure;
    @FXML private Label last_arrival;
    @FXML private Label from_iata;
    @FXML private Label to_iata;
    @FXML private Label trip_duration;
    @FXML private Label nr_of_stops;
    @FXML private Label flight_price_pp;
    @FXML private Label flight_json;

    private String adults;
    private String childrens;
    private String infants;
    private String departing_at;
    private String arrival_at;
    private String from;
    private String to;
    private String token;

    public void setData(String air_comp_name, String first_departure, String last_arrival, String from_iata, String to_iata, String trip_duration, String nr_of_stops, String flight_price_pp, String flight_json, String token){
        this.air_comp_name.setText(air_comp_name);
        this.first_departure.setText(first_departure);
        this.last_arrival.setText(last_arrival);
        this.from_iata.setText(from_iata);
        this.to_iata.setText(to_iata);
        this.trip_duration.setText(trip_duration);
        this.nr_of_stops.setText(nr_of_stops);
        this.flight_price_pp.setText(flight_price_pp);
        this.flight_json.setText(flight_json);
        this.from = from_iata;
        this.to = to_iata;
        this.token = token;
    }

    public void setAts(String departing_at, String arrival_at){
        this.departing_at = departing_at;
        this.arrival_at = arrival_at;
    }

    public void setPassagerNr(String adults, String childrens, String infants){
        if(adults.equals("")){
            this.adults = "0";
        }else{
            this.adults = adults;
        }
        if(childrens.equals("")){
            this.childrens = "0";
        }else{
            this.childrens = childrens;
        }
        if(infants.equals("")){
            this.infants = "0";
        }else{
            this.infants = infants;
        }

    }
    public void select() throws Exception{
        Gson gson = new Gson();
        Stage popupwindow = new Stage();
        durationCalculator dc = new durationCalculator();
        String flight_jsonText = flight_json.getText();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Flight Details");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/detailedFlight.fxml"));
        Parent root = loader.load();
        detailedFlightController dtf_controller = loader.getController();
        Scene scene = new Scene(root);
        popupwindow.setResizable(false);
        popupwindow.setAlwaysOnTop(true);
        System.out.println(flight_jsonText);
        OfferItem json = gson.fromJson(flight_jsonText, OfferItem.class);
        List<Itineraries> s = json.getServices();
        for(Itineraries uh:s) {
            List<FlightSegment> ss = uh.getSegments();
            List<SegmentInfo> segInfo = new ArrayList<>();
            for (FlightSegment fs : ss) {
                SegmentInfo si = new SegmentInfo();
                Departure departure = fs.getDeparture();
                Arrival arrival = fs.getArrival();
                String segment_duration = dc.formatter(fs.getDuration().replace("PT","00:").replace("H",":").replace("M",":"));
//                String segment_duration = fs.getDuration().substring(2);
                String comp = fs.getCarrierCode();
                String departure_iata = departure.getIataCode();
                String departure_at = departure.getAt();
                String departure_time = departure_at.substring(11,16);
                String arrival_iata = arrival.getIataCode();
                String arrival_at = arrival.getAt();
                String arrival_time = arrival_at.substring(11,16);
                si.setFrom(departure_iata);
                si.setTo(arrival_iata);
                si.setDeparting_time(departure_time);
                si.setArriving_time(arrival_time);
                segInfo.add(si);
                System.out.println("====================================================");
                dtf_controller.setData(comp, departure_time, arrival_time, segment_duration, departure_iata, arrival_iata, token);
            }
            dtf_controller.setSegmentList(segInfo);
        }
        Price price = json.getPrice();

        Price ppa = null;
        Price ppc = null;
        Price ppi = null;
        List<TravelerPricings> tp = json.getTravelerPricings();
        for(TravelerPricings x : tp){
            if(x.getTravelerType().equals("ADULT")) ppa = x.getPrice();
            if(x.getTravelerType().equals("CHILD")) ppc = x.getPrice();
            if(x.getTravelerType().equals("HELD_INFANT")) ppi = x.getPrice();
        }

        String adult_price = null;
        String children_price = null;
        String infant_price = null;
        String  total_price = "Total: " +price.getTotal() + " €";
        int nr_of_persons = Integer.parseInt(adults) + Integer.parseInt(childrens) + Integer.parseInt(infants);
        try{
            adult_price = "Adult x"+ adults+ ": " + ppa.getTotal() + " €";
        }catch (Exception e){}
        try{
            children_price = "Children x" + childrens + ": " + ppc.getTotal() + " €";
        }catch (Exception e){}
        try{
            infant_price = "Infant x" + infants + ": " + ppi.getTotal() + " €";
        }catch (Exception e){}
        dtf_controller.setPrice(adult_price, children_price, infant_price, total_price, nr_of_persons);
        dtf_controller.setAts(departing_at, arrival_at, from, to);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
}
