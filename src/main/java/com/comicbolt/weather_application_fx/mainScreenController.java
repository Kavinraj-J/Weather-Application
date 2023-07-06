package com.comicbolt.weather_application_fx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class mainScreenController {

    @FXML
    private Label locationLabel, dateLabel, conditionLabel,
            currentTempLabel, currentDayHighLabel,currentDayLowLabel,
            alertLabel, dayLabel,timeLabel, AmPmLabel;
    @FXML
    private ScrollPane alertPane;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Label day1,day2,day3,day4,day5,day6,day7,day8,day9,day10;
    @FXML
    private Label highday1,highday2,highday3,highday4,highday5,highday6,highday7,highday8,highday9,highday10;
    @FXML
    private Label lowday1,lowday2,lowday3,lowday4,lowday5,lowday6,lowday7,lowday8,lowday9,lowday10;
    @FXML
    private Label[] ForecastDays;
    @FXML
    private Label[] highDays;
    @FXML
    private Label[] lowDays;
    private WeatherApiRequest api;
    private Thread MinuteUpdater;

    @FXML
    private MenuBar menuBar;


    private int  minCounter = 0, hourCounter = 0,secCounter = 0, hour,min;

    public void setLabels(String location){
        api = new WeatherApiRequest(location);
        ArrayList<String> dateArray = api.getDateArray();
        ArrayList<String> conditions = api.getConditionArray();
        ArrayList<Integer> maxTemp = api.getMaxTempArray();
        ArrayList<Integer> minTemp = api.getMinTempArray();
        ArrayList<String> alertArray = api.getAlertArray();
        ArrayList<String> dayArray = api.getDayOfWeekArray();
        ForecastDays = new Label[]{day1, day2, day3, day4, day5, day6, day7, day8, day9, day10};
        highDays = new Label[]{highday1, highday2, highday3, highday4, highday5, highday6, highday7, highday8, highday9, highday10};
        lowDays = new Label[]{lowday1, lowday2, lowday3, lowday4, lowday5, lowday6, lowday7, lowday8, lowday9, lowday10};

        MinuteUpdater = new Thread(this::MinuteUpdater);
        MinuteUpdater.start();
        //System.out.println(dayArray);

        locationLabel.setText(api.getLocation());
        timeLabel.setText(api.getTimeAtLocation());
        AmPmLabel.setText(api.AmOrPm());
        dayLabel.setText(dayArray.get(0));
        dateLabel.setText(dateArray.get(0));
        conditionLabel.setText(conditions.get(0));
        currentTempLabel.setText(String.valueOf(api.getCurrentTemp()));
        currentDayHighLabel.setText("High: " + maxTemp.get(0));
        currentDayLowLabel.setText("Low: " + minTemp.get(0));


        String alert = "ALERT: \n";

        for(int i = 0; i< alertArray.size(); i++){
            alert = " * " + alertArray.get(i) + "\n";
        }

        alertLabel.setText("ALERT AREA GONNA BE SEPERATE WINDOW!!");
        alertPane.setContent(alertLabel);


        for(int i = 1; i < dayArray.size(); i++){
            for(int j = 0; j<ForecastDays.length; j++){
                if(i == j+1){
                    ForecastDays[j].setText(dayArray.get(i));
                }
            }

        }

        System.out.println(maxTemp);
        for(int i = 1; i < maxTemp.size(); i++){
            for(int j = 0; j<highDays.length; j++){
                if(i == j+1){
                    highDays[j].setText("High: " + maxTemp.get(i));
                }
            }

        }

        System.out.println(minTemp);
        for(int i = 1; i < minTemp.size(); i++){
            for(int j = 0; j<lowDays.length; j++){
                if(i == j+1){
                    lowDays[j].setText("Low: " + minTemp.get(i));
                }
            }

        }




    }

    //timeLabel.setText(api.getTimeAtLocation());
    public void MinuteUpdater(){


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),e -> {

            timeLabel.setText(api.getTimeAtLocation());

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    @FXML
    public void goHome() throws IOException {
        root = FXMLLoader.load(getClass().getResource("startingScreen.fxml"));
        stage = (Stage)menuBar.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exitProgram(ActionEvent k){
        stage = (Stage)menuBar.getScene().getWindow();
        stage.close();
    }









}
