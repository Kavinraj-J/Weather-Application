package com.comicbolt.weather_application_fx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;

public class mainScreenController {

    @FXML
    private Label locationLabel, dateLabel, conditionLabel,
            currentTempLabel, currentDayHighLabel,currentDayLowLabel,
            alertLabel, dayLabel;

    @FXML
    private ScrollPane alertPane;

    private WeatherApiRequest api;

    public void setLabels(String location){
        api = new WeatherApiRequest(location);
        ArrayList<String> dateArray = api.getDateArray();
        ArrayList<String> conditions = api.getConditionArray();
        ArrayList<Integer> maxTemp = api.getMaxTempArray();
        ArrayList<Integer> minTemp = api.getMinTempArray();
        ArrayList<String> alertArray = api.getAlertArray();
        ArrayList<String> dayArray = api.getDayOfWeekArray();
        //System.out.println(dayArray);

        locationLabel.setText(api.getLocation());
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






    }








}
