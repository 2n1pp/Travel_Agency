package com.noborderstravel.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.noborderstravel.models.SegmentInfo;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class detailedFlightController {

    @FXML private VBox result_vbox;
    @FXML private Label adult_price;
    @FXML private Label children_price;
    @FXML private Label infant_price;
    @FXML private Label total_price;
    @FXML private VBox price_vbox;
    @FXML private AnchorPane ap;
    @FXML private VBox vbx;

    private int nr_of_persons;
    private List<SegmentInfo> segmentList;
    private String departing_at;
    private String arrival_at;
    private String from_iata;
    private String to_iata;
    private String token;

    public void setSegmentList(List<SegmentInfo> segmentlist){
        this.segmentList = segmentlist;
    }

    public void setData(String air_comp_name, String first_departure, String last_arrival, String trip_duration, String from_city, String to_city, String token) throws IOException, ParseException {
        this.token = token;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/detailedSegment.fxml"));
        result_vbox.getChildren().add(loader.load());
        detailedSegmentController childController = loader.getController();
        childController.setData(air_comp_name, first_departure, last_arrival, trip_duration, from_city, to_city, token);
    }

    public void setAts(String departing_at, String arrival_at, String from, String to){
        this.departing_at = departing_at;
        this.arrival_at = arrival_at;
        this.from_iata = from;
        this.to_iata = to;
    }

    public void setPrice(String adult_price, String children_price, String infant_price, String total_price, int nr_of_persons){
        if(adult_price != null) {
            this.adult_price.setText(adult_price);
        }else{
            price_vbox.getChildren().remove(this.adult_price);
        }
        if(children_price != null){
            this.children_price.setText(children_price);
        }else{
            price_vbox.getChildren().remove(this.children_price);
        }
        if(infant_price != null){
            this.infant_price.setText(infant_price);
        }else{
            price_vbox.getChildren().remove(this.infant_price);
        }
        this.nr_of_persons = nr_of_persons;
        this.total_price.setText(total_price);
    }
    @FXML
    public void procreed() throws IOException {
        System.out.println("Aaaaa");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/travelerDetail.fxml"));
        ap.getChildren().remove(this.vbx);
        ap.getChildren().add(loader.load());
        Stage stage = (Stage) ap.getScene().getWindow();
        travelerDetailController tdc = loader.getController();
        tdc.setNr_of_persons(nr_of_persons);
        tdc.setGoingSegmentList(segmentList);
        tdc.setAts(departing_at, arrival_at, from_iata, to_iata, token);
        stage.setMaxHeight(290.00);
        stage.setMaxWidth(669.00);
    }
}
