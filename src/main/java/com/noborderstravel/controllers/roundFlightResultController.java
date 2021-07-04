package com.noborderstravel.controllers;

import com.google.gson.Gson;
import com.noborderstravel.models.*;
import com.noborderstravel.services.durationCalculator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class roundFlightResultController {

    @FXML private Label going_air_comp_name;
    @FXML private Label going_first_departure;
    @FXML private Label going_from_iata;
    @FXML private Label going_trip_duration;
    @FXML private Label going_nr_of_stops;
    @FXML private Label going_date;
    @FXML private Label going_last_arrival;
    @FXML private Label going_to_iata;
    @FXML private Label coming_air_comp_name;
    @FXML private Label coming_first_departure;
    @FXML private Label coming_from_iata;
    @FXML private Label coming_trip_duration;
    @FXML private Label coming_nr_of_stops;
    @FXML private Label coming_date;
    @FXML private Label coming_last_arrival;
    @FXML private Label coming_to_iata;
    @FXML private Label total_flight_price;
    @FXML private Label flight_json;

    private String adults;
    private String childrens;
    private String infants;
    private String going_departing_at;
    private String going_arrival_at;
    private String coming_departing_at;
    private String coming_arrival_at;
    private String going_from;
    private String going_to;
    private String coming_from;
    private String coming_to;
    private String token;

    public void setData(String going_air_comp_name, String going_first_departure, String going_last_arrival, String going_from_iata, String going_to_iata, String going_trip_duration, String going_nr_of_stops, String coming_air_comp_name, String coming_first_departure, String coming_last_arrival, String coming_from_iata, String coming_to_iata, String coming_trip_duration, String coming_nr_of_stops, String going_date, String coming_date, String total_flight_price, String flight_json, String token){
        this.going_air_comp_name.setText(going_air_comp_name);
        this.going_first_departure.setText(going_first_departure);
        this.going_last_arrival.setText(going_last_arrival);
        this.going_from_iata.setText(going_from_iata);
        this.going_to_iata.setText(going_to_iata);
        this.going_trip_duration.setText(going_trip_duration);
        this.going_nr_of_stops.setText(going_nr_of_stops);
        this.going_from = going_from_iata;
        this.going_to = going_to_iata;
        this.coming_air_comp_name.setText(coming_air_comp_name);
        this.coming_first_departure.setText(coming_first_departure);
        this.coming_last_arrival.setText(coming_last_arrival);
        this.coming_from_iata.setText(coming_from_iata);
        this.coming_to_iata.setText(coming_to_iata);
        this.coming_trip_duration.setText(coming_trip_duration);
        this.coming_nr_of_stops.setText(coming_nr_of_stops);
        this.coming_from = coming_from_iata;
        this.coming_to = coming_to_iata;
        this.total_flight_price.setText(total_flight_price);
        this.flight_json.setText(flight_json);
        this.going_date.setText(going_date);
        this.coming_date.setText(coming_date);
        this.token = token;
    }

    public void setAts(String going_departing_at, String going_arrival_at, String coming_departing_at, String coming_arrival_at){
        this.going_departing_at = going_departing_at;
        this.going_arrival_at = going_arrival_at;
        this.coming_departing_at = coming_departing_at;
        this.coming_arrival_at = coming_arrival_at;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/roundDetailedFlight.fxml"));
        Parent root = loader.load();
        roundDetailedFlightController rdtf_controller = loader.getController();
        Scene scene = new Scene(root);
        popupwindow.setResizable(false);
        OfferItem json = gson.fromJson(flight_jsonText, OfferItem.class);
        List<Itineraries> s = json.getServices();
        int gc = 1;
        for(Itineraries uh:s) {
            List<FlightSegment> ss = uh.getSegments();
            List<SegmentInfo> segInfo = new ArrayList<>();
            for (FlightSegment fs : ss) {
                SegmentInfo si = new SegmentInfo();
//                FlightSegment fs = sss.getFlightSegment();
                Departure departure = fs.getDeparture();
                Arrival arrival = fs.getArrival();
                String segment_duration = dc.formatter(fs.getDuration().replace("PT","00:").replace("H",":").replace("M",":"));
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
                System.out.println(segment_duration + ", " + comp  + ", " + departure_iata + ", " + departure_time + ", " + arrival_iata + ", " + arrival_time);
                rdtf_controller.setData(comp, departure_time, arrival_time, segment_duration, departure_iata, arrival_iata, gc, token);
            }
            rdtf_controller.setSegmentList(segInfo, gc);
            gc ++;
        }
//        Price price = json.getPrice();
//        PricePerAdult ppa = json.getPricePerAdult();
//        PricePerChild ppc = json.getPricePerChild();
//        PricePerInfant ppi = json.getPricePerInfant();
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
//        rdtf_controller.setPrice(adult_price, children_price, infant_price, "500", nr_of_persons);
        rdtf_controller.setPrice(adult_price, children_price, infant_price, total_price, nr_of_persons);
        rdtf_controller.setAts(going_departing_at, going_arrival_at, going_from, going_to);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
}