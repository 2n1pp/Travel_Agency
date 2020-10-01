package com.noborderstravel.controllers;

import com.noborderstravel.models.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.noborderstravel.services.requests;
import com.noborderstravel.services.durationCalculator;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import org.controlsfx.control.textfield.TextFields;
import com.google.gson.Gson;

public class mainViewController{

    Gson gson = new Gson();
    com.noborderstravel.services.requests requests = new requests();
    String[] possible;

    @FXML private TextField from;
    @FXML private  TextField to;
    @FXML private CheckBox one_way;
    @FXML private DatePicker departing_date;
    @FXML private DatePicker return_date;
    @FXML private VBox result_vbox;
    @FXML private TextField adults_nr;
    @FXML private TextField childrens_nr;
    @FXML private TextField infants_nr;
    @FXML private VBox loading_fade;
    @FXML private VBox loading_gif;
    private String flight_response = "";
    private String token;

    public void setToken(String token) {
        this.token = token;
        try {
            getIata();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(from, possible);
        TextFields.bindAutoCompletion(to, possible);
    }


    @FXML
    private void handleCheckBoxAction() {
        if(one_way.isSelected()){
            return_date.setVisible(false);
        }else {
            return_date.setVisible(true);
        }
    }
    @FXML
    public void handleLogout(ActionEvent event) throws Exception{
        double height = ((Node)event.getSource()).getScene().getHeight();
        double width = ((Node)event.getSource()).getScene().getWidth();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(parent, width, height);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    public void handleSettings() throws IOException {
        Stage popupwindow = new Stage();
        durationCalculator dc = new durationCalculator();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Settings");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/settings.fxml"));
        Parent root = loader.load();
        settingsController controller = loader.getController();
        controller.setToken(token);
        Scene scene = new Scene(root);
        popupwindow.setResizable(false);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }

    public void getIata() throws IOException, ParseException {
        String iata_response = requests.GET("http://127.0.0.1:5000/iata?token=" + this.token);
        String iata_json = iata_response.substring(7, iata_response.length()-3);
        possible = iata_json.split("\",(.*?)\"");
    }

    public void search(){
        String from = this.from.getText().replaceAll("(.*?)(\\[)","").replace("]", "");
        String to = this.to.getText().replaceAll("(.*?)(\\[)","").replace("]", "");
        LocalDate departing_date = this.departing_date.getValue();
        LocalDate return_date = this.return_date.getValue();
        String url = "";
        if(one_way.isSelected()){
            url = "?origin=" + from + "&destination=" + to + "&departureDate=" + departing_date;
            if(!adults_nr.getText().equals("")){
                url = url + "&adults=" + adults_nr.getText();
            }
            if(!childrens_nr.getText().equals("")){
                url = url + "&children=" + childrens_nr.getText();
            }
            if(!infants_nr.getText().equals("")){
                url = url + "&infants=" + infants_nr.getText();
            }
            url = url + "&token=" + token;
            System.out.println(url);
            search_oneway(url);
        }else {
            url = "?origin=" + from + "&destination=" + to + "&departureDate=" + departing_date + "&returnDate=" + return_date;
            if(!adults_nr.getText().equals("")){
                url = url + "&adults=" + adults_nr.getText();
            }
            if(!childrens_nr.getText().equals("")){
                url = url + "&children=" + childrens_nr.getText();
            }
            if(!infants_nr.getText().equals("")){
                url = url + "&infants=" + infants_nr.getText();
            }
            url = url + "&token=" + token;
            System.out.println(url);
            search_roundTrip(url);
        }
        System.out.println("from: " + from + "\nto: " + to + "\ndeparting date: " + departing_date);
    }

    @FXML
    public void search_oneway(String url){
        loading_gif.setVisible(true);
        loading_fade.setVisible(true);
        Runnable task = () -> {
            try {
                this.flight_response = requests.GET("http://127.0.0.1:5000/flight" + url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            search_oneway_formatter();
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.setPriority(Thread.MAX_PRIORITY);
        backgroundThread.start();
    }
    public void search_oneway_formatter(){
        Platform.runLater(() -> {
            Pane empty_pane = new Pane();
            empty_pane.setMaxHeight(20.0);
            result_vbox.getChildren().clear();
            result_vbox.getChildren().add(empty_pane);
            durationCalculator dc = new durationCalculator();
            String to_iata = "";
            String from_iata = "";
            String departure_time = "";
            String arrival_time = "";
            String nr_of_stops = "";
            ArrayList<String> airline_names = new ArrayList();
            String final_comp_name = "";
            String total_duration = "00:00:00";
            String flight_json = "";
            if (flight_response.equals("500")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner((result_vbox.getScene().getWindow()));
                alert.setTitle("Warning");
                alert.setHeaderText("Couldnt find a flight!");
                alert.showAndWait();
                loading_gif.setVisible(false);
                loading_fade.setVisible(false);
            }else if(flight_response.equals("3003")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner((result_vbox.getScene().getWindow()));
                alert.setTitle("Error");
                alert.setHeaderText("You are not authorized to perform this action");
                alert.showAndWait();
                loading_gif.setVisible(false);
                loading_fade.setVisible(false);
            } else {
                Flight json = gson.fromJson(flight_response, Flight.class);
                Dictionaries dict = json.getDictionaries();
                List<Datum> data = json.getData();
                String departure_at = "";
                String arrival_at = "";
                for (Datum x : data) {
                    List<OfferItem> p = x.getOfferItems();
                    for (OfferItem oi : p) {
                        int klkl = 0;
                        Price price = oi.getPrice();
                        String total_price = "€ " + price.getTotal();
                        List<Service> s = oi.getServices();
                        System.out.println(s.size());
                        for (Service uh : s) {
                            List<Segment> ss = uh.getSegments();
                            for (Segment sss : ss) {
                                FlightSegment fs = sss.getFlightSegment();
                                Departure departure = fs.getDeparture();
                                Arrival arrival = fs.getArrival();
                                Operating operating = fs.getOperating();
                                String segment_duration = fs.getDuration().replace("DT", ":").replace("H", ":").replace("M", ":");
                                String comp = operating.getCarrierCode();
                                airline_names.add(dict.getCarriers().get(comp));
                                fs.setCarrierCode(dict.getCarriers().get(comp));
                                String departure_iata = departure.getIataCode();
                                departure_at = departure.getAt();
                                String arrival_iata = arrival.getIataCode();
                                arrival_at = arrival.getAt();
                                to_iata = arrival_iata;
                                arrival_time = arrival_at.substring(11, 16);
                                if (klkl == 0) {
                                    from_iata = departure_iata;
                                    departure_time = departure_at.substring(11, 16);
                                }
                                total_duration = dc.calc(total_duration, segment_duration);

                                klkl++;
                            }
                        }
                        if (klkl != 1) {
                            if (klkl == 2) {
                                nr_of_stops = klkl - 1 + " stop";
                            } else {
                                nr_of_stops = klkl - 1 + " stops";
                            }
                        }
                        String testing_name = airline_names.get(0);
                        for (String comp_name : airline_names) {
                            if(comp_name == null){
                                comp_name = "Name not available";
                            }
                            if (!comp_name.equals(testing_name)) {
                                final_comp_name = "Multiple carriers";
                                break;
                            } else {
                                final_comp_name = comp_name;
                            }
                        }
                        flight_json = x.toString();
                        total_duration = dc.formatter(total_duration);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/flightResult.fxml"));
                        try {
                            result_vbox.getChildren().add(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        flightResultController childController = loader.getController();
                        childController.setPassagerNr(adults_nr.getText(), childrens_nr.getText(), infants_nr.getText());
                        childController.setAts(departure_at, arrival_at);
                        childController.setData(final_comp_name, departure_time, arrival_time, from_iata, to_iata, total_duration, nr_of_stops, total_price, flight_json, token);
                        airline_names.clear();
                        total_duration = "00:00:00";
                    }
                }
            }
            loading_gif.setVisible(false);
            loading_fade.setVisible(false);
        });
    }

    @FXML
    public void search_roundTrip(String url){
        loading_gif.setVisible(true);
        loading_fade.setVisible(true);
        Runnable task = () -> {
            try {
                this.flight_response = requests.GET("http://127.0.0.1:5000/flight" + url);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            search_roundTrip_formatter();
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.setPriority(Thread.MAX_PRIORITY);
        backgroundThread.start();
    }
    public void search_roundTrip_formatter(){
        Platform.runLater(() -> {
            Pane empty_pane = new Pane();
            empty_pane.setMaxHeight(20.0);
            result_vbox.getChildren().clear();
            result_vbox.getChildren().add(empty_pane);
            durationCalculator dc = new durationCalculator();
            String going_to_iata = "";
            String going_from_iata = "";
            String coming_to_iata = "";
            String coming_from_iata = "";
            String going_departure_time = "";
            String going_arrival_time = "";
            String going_nr_of_stops = "";
            String coming_departure_time = "";
            String coming_arrival_time = "";
            String coming_nr_of_stops = "";
            ArrayList<String> going_airline_names = new ArrayList();
            ArrayList<String> coming_airline_names = new ArrayList();
            String going_final_comp_name = "";
            String coming_final_comp_name = "";
            String going_total_duration = "00:00:00";
            String coming_total_duration = "00:00:00";
            String flight_json = "";
            if (flight_response.equals("500")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner((result_vbox.getScene().getWindow()));
                alert.setTitle("Warning");
                alert.setHeaderText("Couldnt find a flight!");
                alert.showAndWait();
                loading_gif.setVisible(false);
                loading_fade.setVisible(false);
            }else if(flight_response.equals("3003")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner((result_vbox.getScene().getWindow()));
                alert.setTitle("Error");
                alert.setHeaderText("You are not authorized to perform this action");
                alert.showAndWait();
                loading_gif.setVisible(false);
                loading_fade.setVisible(false);
            } else {
                Flight json = gson.fromJson(flight_response, Flight.class);
                Dictionaries dict = json.getDictionaries();
                List<Datum> data = json.getData();
                String going_departure_at = "";
                String going_arrival_at = "";
                String coming_departure_at = "";
                String coming_arrival_at = "";
                for (Datum x : data) {
                    List<OfferItem> p = x.getOfferItems();
                    for (OfferItem oi : p) {
                        int going_stops_counter = 0;
                        int coming_stops_counter = 0;
                        Price price = oi.getPrice();
                        String total_price = "€ " + price.getTotal();
                        List<Service> s = oi.getServices();
                        System.out.println(s.size());
                        int gc = 1;
                        for (Service uh : s) {
                            List<Segment> ss = uh.getSegments();
                            if(gc == 1){
                                going_stops_counter = 0;
                                System.out.println("going");
                                for (Segment sss : ss) {
                                    FlightSegment fs = sss.getFlightSegment();
                                    Departure departure = fs.getDeparture();
                                    Arrival arrival = fs.getArrival();
                                    Operating operating = fs.getOperating();
                                    String segment_duration = fs.getDuration().replace("DT", ":").replace("H", ":").replace("M", ":");
                                    String comp = operating.getCarrierCode();
                                    going_airline_names.add(dict.getCarriers().get(comp));
                                    fs.setCarrierCode(dict.getCarriers().get(comp));
                                    String departure_iata = departure.getIataCode();
                                    going_departure_at = departure.getAt();
                                    String arrival_iata = arrival.getIataCode();
                                    going_arrival_at = arrival.getAt();
                                    going_to_iata = arrival_iata;
                                    going_arrival_time = going_arrival_at.substring(11, 16);
                                    if (going_stops_counter == 0) {
                                        going_from_iata = departure_iata;
                                        going_departure_time = going_departure_at.substring(11, 16);
                                    }
                                    going_total_duration = dc.calc(going_total_duration, segment_duration);
                                    going_stops_counter++;
                                }
                            }
                            else if(gc ==2){
                                coming_stops_counter = 0;
                                System.out.println("coming");
                                for (Segment sss : ss) {
                                    FlightSegment fs = sss.getFlightSegment();
                                    Departure departure = fs.getDeparture();
                                    Arrival arrival = fs.getArrival();
                                    Operating operating = fs.getOperating();
                                    String segment_duration = fs.getDuration().replace("DT", ":").replace("H", ":").replace("M", ":");
                                    String comp = operating.getCarrierCode();
                                    coming_airline_names.add(dict.getCarriers().get(comp));
                                    fs.setCarrierCode(dict.getCarriers().get(comp));
                                    String departure_iata = departure.getIataCode();
                                    coming_departure_at = departure.getAt();
                                    String arrival_iata = arrival.getIataCode();
                                    coming_arrival_at = arrival.getAt();
                                    coming_to_iata = arrival_iata;
                                    coming_arrival_time = coming_arrival_at.substring(11, 16);
                                    if (coming_stops_counter == 0) {
                                        coming_from_iata = departure_iata;
                                        coming_departure_time = coming_departure_at.substring(11, 16);
                                    }
                                    coming_total_duration = dc.calc(coming_total_duration, segment_duration);
                                    coming_stops_counter++;
                                }
                            }
                            gc ++;
                        }
                        if (going_stops_counter != 1) {
                            if (going_stops_counter == 2) {
                                going_nr_of_stops = going_stops_counter - 1 + " stop";
                            } else {
                                going_nr_of_stops = going_stops_counter - 1 + " stops";
                            }
                        }
                        if (coming_stops_counter != 1) {
                            if (coming_stops_counter == 2) {
                                coming_nr_of_stops = coming_stops_counter - 1 + " stop";
                            } else {
                                coming_nr_of_stops = coming_stops_counter - 1 + " stops";
                            }
                        }

                        String going_testing_name = going_airline_names.get(0);
                        for (String comp_name : going_airline_names) {
                            if(comp_name == null){
                                comp_name = "Name not available";
                            }
                            if (!comp_name.equals(going_testing_name)) {
                                going_final_comp_name = "Multiple carriers";
                                break;
                            } else {
                                going_final_comp_name = comp_name;
                            }
                        }

                        String coming_testing_name = coming_airline_names.get(0);
                        for (String comp_name : coming_airline_names) {
                            if(comp_name == null){
                                comp_name = "Name not available";
                            }
                            if (!comp_name.equals(coming_testing_name)) {
                                coming_final_comp_name = "Multiple carriers";
                                break;
                            } else {
                                coming_final_comp_name = comp_name;
                            }
                        }
                        System.out.println("going from " + going_from_iata +" at: " + going_departure_time +" to " + going_to_iata + " at: " + going_arrival_time + going_nr_of_stops);
                        System.out.println("coming from " + coming_from_iata +" at: " + coming_departure_time +" to " + coming_to_iata + " at: " + coming_arrival_time + coming_nr_of_stops);
                        flight_json = x.toString();
                        going_total_duration = dc.formatter(going_total_duration);
                        coming_total_duration = dc.formatter(coming_total_duration);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/roundFlightResult.fxml"));
                        try {
                            result_vbox.getChildren().add(loader.load());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String going_date = this.departing_date.getValue().toString();
                        String coming_date = this.return_date.getValue().toString();
                        roundFlightResultController childController = loader.getController();
                        childController.setPassagerNr(adults_nr.getText(), childrens_nr.getText(), infants_nr.getText());
                        childController.setAts(going_departure_at, going_arrival_at, coming_departure_at, coming_arrival_at);
                        childController.setData(going_final_comp_name, going_departure_time, going_arrival_time, going_from_iata, going_to_iata, going_total_duration, going_nr_of_stops, coming_final_comp_name, coming_departure_time, coming_arrival_time, coming_from_iata, coming_to_iata, coming_total_duration, coming_nr_of_stops, going_date, coming_date, total_price, flight_json, token);
                        going_airline_names.clear();
                        coming_airline_names.clear();
                        going_total_duration = "00:00:00";
                        coming_total_duration = "00:00:00";
                    }
                }
            }
            loading_gif.setVisible(false);
            loading_fade.setVisible(false);
        });
    }
}
