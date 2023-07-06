package com.comicbolt.weather_application_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class startingScreenController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField locationTextView;
    private String location;

    @FXML
    public void getLocation(ActionEvent k) throws IOException {


        location = locationTextView.getText();
        System.out.println(location);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        root = loader.load();

        mainScreenController msc = loader.getController();
        msc.setLabels(location);


        //root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)((Node)k.getSource()).getScene().getWindow();
        stage.setX(60);
        stage.setY(40);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
