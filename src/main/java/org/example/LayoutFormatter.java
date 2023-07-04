package org.example;

import java.util.ArrayList;

public class LayoutFormatter {

    private ArrayList<String> dateArray;
    private ArrayList<Double> maxTempArray;
    private ArrayList<Double> minTempArray;
    private ArrayList<String> conditionArray;
    private ArrayList<String> alertArray;

    private ArrayList<String> all = new ArrayList<String>();
    public LayoutFormatter(WeatherApiRequest api){

        dateArray = api.getDateArray();
        maxTempArray = api.getMaxTempArray();
        minTempArray = api.getMinTempArray();
        conditionArray = api.getConditionArray();
        alertArray = api.getAlertArray();

        String space = "     ";
        String currentDate = "Current Date: " + dateArray.get(0);
        String location = "Location: " + api.getLocation();
        String currentTemp = "Current Weather: " + api.getCurrentTemp();
        String highLowLine = "High: " + maxTempArray.get(0) + space + "Low: " + minTempArray.get(0);
        String forecastLabel = "15 Day Weather Forecast:";
        String alertLabel = "Alerts in the area:";

        all.add(space);
        all.add(currentDate);
        all.add(location);
        all.add(currentTemp);
        all.add(highLowLine);
        all.add(space);
        all.add(forecastLabel);

        String weatherString = "";
        for(int i = 0; i < dateArray.size(); i++){
            weatherString = dateArray.get(i) + space + "High: " + maxTempArray.get(i) + space + "Low: "
                    + minTempArray.get(i) + space + conditionArray.get(i);
            all.add(weatherString);
        }

        all.add(space);

        if(alertArray.size() > 0) {
            all.add(alertLabel);

            String alert = "";
            for (int i = 0; i < alertArray.size(); i++) {
                alert = " * " + alertArray.get(i);
                all.add(alert);
            }
        }

        all.add(space);





    }

    public ArrayList<String> getAllStrings(){
        return all;
    }
}
