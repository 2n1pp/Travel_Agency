package com.noborderstravel.controllers;

import com.noborderstravel.services.cipher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.noborderstravel.services.requests;

public class loginController {
    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML
    public void handleLogin(ActionEvent event) throws Exception
    {
        String uname = username.getText();
        String pword = password.getText();
        cipher cp = new cipher();
        pword = cp.getSHA256Hash(pword);
        String login_response = requests.GET("http://127.0.0.1:5000/authenticate?username="+uname+"&password="+pword);
        if(login_response.equals("3003") || login_response.equals("500")){
            System.out.println("Login Failed");
            System.out.println(username + " " + password.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(((Node)event.getSource()).getScene().getWindow());
            alert.setTitle("Warning");
            alert.setHeaderText("Username or password is incorrect");
            alert.showAndWait();
        }
        else{
            double height = ((Node)event.getSource()).getScene().getHeight();
            double width = ((Node)event.getSource()).getScene().getWidth();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setMaximized(true);
            window.show();
            mainViewController childController = loader.getController();
            childController.setToken(login_response);
        }
    }

}
