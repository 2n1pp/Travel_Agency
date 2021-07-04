package com.noborderstravel.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.noborderstravel.services.requests;
import java.io.IOException;
import java.text.ParseException;


public class detailedSegmentController {
    @FXML private Label air_comp_name_d;
    @FXML private Label from_city_d;
    @FXML private Label first_departure_d;
    @FXML private Label trip_duration_d;
    @FXML private Label to_city_d;
    @FXML private Label last_arrival_d;
    @FXML private Label from_airport_d;
    @FXML private Label to_airport_d;

    public void setData(String air_comp_name, String first_departure, String last_arrival, String trip_duration, String from_city, String to_city, String token) throws IOException, ParseException {
        if(air_comp_name == null){
            air_comp_name = "Sorry, name is not available";
            System.out.println(first_departure);
        }
        System.out.println(from_city + " " + to_city);
        String departing_city_data = requests.GET("http://127.0.0.1:5000/iatadetail/"+ from_city + "?token="+token);
        String arrival_city_data = requests.GET("http://127.0.0.1:5000/iatadetail/"+ to_city+ "?token="+token);
        System.out.println("departing: " + departing_city_data + "\narrival: " + arrival_city_data);
        departing_city_data = departing_city_data.substring(7, departing_city_data.length()-4);
        arrival_city_data = arrival_city_data.substring(7, arrival_city_data.length()-4);
        String[] departing_city_list = departing_city_data.split(",");
        String[] arrival_city_list = arrival_city_data.split(",");
        this.air_comp_name_d.setText(air_comp_name);
        this.first_departure_d.setText(first_departure);
        this.last_arrival_d.setText(last_arrival);
        this.trip_duration_d.setText(trip_duration);
        this.from_city_d.setText(departing_city_list[1] + ", " + departing_city_list[2]);
        this.to_city_d.setText(arrival_city_list[1] + ", " + arrival_city_list[2]);
        this.from_airport_d.setText(departing_city_list[0]);
        this.to_airport_d.setText(arrival_city_list[0]);
    }
}
