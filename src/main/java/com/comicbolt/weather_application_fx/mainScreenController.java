package com.comicbolt.weather_application_fx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class mainScreenController {

    @FXML
    private Label locationLabel, dateLabel, conditionLabel,
            currentTempLabel, currentDayHighLabel,currentDayLowLabel,
            alertLabel, dayLabel,timeLabel, AmPmLabel;
    @FXML
    private ScrollPane alertPane;


    @FXML
    private Label day1,day2,day3,day4,day5,day6,day7,day8,day9,day10;
    @FXML
    private Label[] ForecastDays;
    private WeatherApiRequest api;
    private Thread MinuteUpdater;


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



    }

    //timeLabel.setText(api.getTimeAtLocation());
    public void MinuteUpdater(){
//        String currentTime = api.getTimeAtLocation();
//        hour = Integer.parseInt(currentTime.substring(0,currentTime.indexOf(":")));
//        min = Integer.parseInt(currentTime.substring(currentTime.indexOf(":")+1));
//        minCounter = 0;
//        hourCounter = 0;
//        secCounter = 0;

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),e -> {

//            if(secCounter == 60){
//                minCounter++;
//                min = min + minCounter;
//                if(min < 10){
//                    timeLabel.setText(hour + ":0" + min);
//                }
//                else{
//                    timeLabel.setText(hour + ":" + min);
//                }
//                secCounter = 0;
//            }
//
//            if (minCounter == 60){
//                hourCounter++;
//                hour = hour + hourCounter;
//                if(min < 10){
//                    timeLabel.setText(hour + ":0" + min);
//                }
//                else{
//                    timeLabel.setText(hour + ":" + min);
//                }
//                minCounter = 0;
//            }
//
//            if(AmPmLabel.getText().equals("AM") && hour == 12){
//                AmPmLabel.setText("PM");
//            }
//            if(AmPmLabel.getText().equals("PM") && hour == 12){
//                AmPmLabel.setText("AM");
//            }
//
//            secCounter++;
//           // System.out.println(secCounter);
            timeLabel.setText(api.getTimeAtLocation());

        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }








}
