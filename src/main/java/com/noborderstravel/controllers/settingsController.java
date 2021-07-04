package com.noborderstravel.controllers;

import com.noborderstravel.services.cipher;
import com.noborderstravel.services.requests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

public class settingsController {

    @FXML private Button acc_button;
    @FXML private Button payment_button;
    @FXML private Button about_button;
    @FXML private Pane acc_pane;
    @FXML private PasswordField curr_pass;
    @FXML private PasswordField new_pass1;
    @FXML private PasswordField new_pass2;
    @FXML private Pane payment_pane;
    @FXML private TextField card_name;
    @FXML private TextField card_cvv;
    @FXML private TextField card_num;
    @FXML private TextField card_month;
    @FXML private TextField card_year;
    @FXML private ImageView americ_img;
    @FXML private ImageView master_img;
    @FXML private ImageView visa_img;
    @FXML private Pane about_pane;

    private String token = "";
    private String selected = "-fx-background-color: #515151; -fx-background-radius: 13px 0px 0px 13px";
    private String unselected = "-fx-background-color: #4f98ca; -fx-background-radius: 13px 0px 0px 13px";

    public void setToken(String token){
        this.token = token;
    }

    public void account_select(){
        acc_button.setStyle(selected);
        payment_button.setStyle(unselected);
        about_button.setStyle(unselected);
        acc_pane.setVisible(true);
        payment_pane.setVisible(false);
        about_pane.setVisible(false);
    }
    public void payment_select(){
        acc_button.setStyle(unselected);
        payment_button.setStyle(selected);
        about_button.setStyle(unselected);
        acc_pane.setVisible(false);
        payment_pane.setVisible(true);
        about_pane.setVisible(false);
    }
    public void about_select(){
        acc_button.setStyle(unselected);
        payment_button.setStyle(unselected);
        about_button.setStyle(selected);
        acc_pane.setVisible(false);
        payment_pane.setVisible(false);
        about_pane.setVisible(true);
    }

    public void initialize() {
        card_num.textProperty().addListener((obs, oldText, newText) -> {
            setCardType();
        });
    }

    public void setCardType(){
        String first_num = "0";
        try{
            first_num = card_num.getText().substring(0, 1);
        }catch (Exception e){
            first_num = "0";
        }
        System.out.println(first_num);
        if(first_num.equals("3")){
            americ_img.setOpacity(1.0);
            visa_img.setOpacity(0.3);
            master_img.setOpacity(0.3);
        }else if(first_num.equals("4")){
            americ_img.setOpacity(0.3);
            visa_img.setOpacity(1.0);
            master_img.setOpacity(0.3);
        }else if(first_num.equals("5")){
            americ_img.setOpacity(0.3);
            visa_img.setOpacity(0.3);
            master_img.setOpacity(1.0);
        }else if(first_num.equals("0")) {
            americ_img.setOpacity(1.0);
            visa_img.setOpacity(1.0);
            master_img.setOpacity(1.0);
        }
    }

    public void changePass(ActionEvent event) throws IOException, ParseException {
        String curr_pass = this.curr_pass.getText();
        String new_pass1 = this.new_pass1.getText();
        String new_pass2 = this.new_pass2.getText();
        cipher cp = new cipher();
        if(!new_pass1.equals(new_pass2)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(((Node)event.getSource()).getScene().getWindow());
            alert.setTitle("Warning");
            alert.setHeaderText("Passwords dosent match!");
            alert.showAndWait();
        }
        else {
            curr_pass = cp.getSHA256Hash(curr_pass);
            new_pass1 = cp.getSHA256Hash(new_pass1);
            String response = requests.GET("http://127.0.0.1:5000/changepass?oldpass=" + curr_pass + "&newpass=" + new_pass1 + "&token=" + token);
            if (response.equals("200")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(((Node) event.getSource()).getScene().getWindow());
                alert.setTitle("Success");
                alert.setHeaderText("Password changed succesfully");
                alert.showAndWait();
            } else if (response.equals("500")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(((Node) event.getSource()).getScene().getWindow());
                alert.setTitle("Warning");
                alert.setHeaderText("Old password is not correct");
                alert.showAndWait();
            }
        }
    }
    public void savePaymentInfo(ActionEvent event) throws IOException, ParseException, URISyntaxException {
        String card_name = this.card_name.getText();
        String card_num = this.card_num.getText();
        String card_cvv = this.card_cvv.getText();
        String card_exp = this.card_month.getText() + "/" + this.card_year.getText();
        String query = "holdername=" + card_name + "&cardnumber=" + card_num + "&cvv=" + card_cvv + "&expiration=" + card_exp + "&token="+ token;
        URI uri = new URI(null, null, null, query, null);
        System.out.println(uri);
        String response = requests.GET("http://127.0.0.1:5000/savepayment"+uri.toString());
        System.out.println(response);
        if (response.equals("200")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(((Node) event.getSource()).getScene().getWindow());
            alert.setTitle("Success");
            alert.setHeaderText("Payment info saved succesfully");
            alert.showAndWait();
        } else if (response.equals("500")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(((Node) event.getSource()).getScene().getWindow());
            alert.setTitle("Warning");
            alert.setHeaderText("Something went wrong!");
            alert.showAndWait();
        }

    }
}
