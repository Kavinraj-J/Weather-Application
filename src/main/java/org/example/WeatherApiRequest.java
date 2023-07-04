package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherApiRequest {
    private String apiEndPoint="https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    ApiKey key = new ApiKey();
    private String apiKey= key.apiKey;
    private String ApiResponse;
    private JSONObject responseJson;
    private JSONArray val;


    public WeatherApiRequest(String location){

        try {
            URL url  = new URL(apiEndPoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");

            String apiContent = "{\"location\":\"" +location+ "\",\n  \"key\":\""+apiKey+"\"}";
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(apiContent);
            writer.flush();
            writer.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ApiResponse = response.toString();

            responseJson = new JSONObject(ApiResponse);

            //System.out.println(responseJson.getString("timezone"));

            val = responseJson.getJSONArray("days");

        }catch (Exception e){
            System.out.println("Sorry Invalid City entered");
        }

    }

    public double getCurrentTemp(){
        JSONObject currentDay = val.getJSONObject(0);
        double currentTemp = currentDay.getDouble("temp");

        return currentTemp;

    }

    public ArrayList<Double> getMaxTempArray(){
        ArrayList<Double> MaxTempArray = new ArrayList<Double>();
        for(int i = 0; i<val.length();i++){
            JSONObject day  = val.getJSONObject(i);

            MaxTempArray.add(day.getDouble("tempmax"));

        }
        //System.out.println(MaxTempArray);
        return MaxTempArray;
    }

    public ArrayList<Double> getMinTempArray(){
        ArrayList<Double> MinTempArray = new ArrayList<Double>();
        for(int i = 0; i<val.length();i++){
            JSONObject day  = val.getJSONObject(i);

            MinTempArray.add(day.getDouble("tempmin"));

        }
        //System.out.println(MinTempArray);
        return MinTempArray;
    }

    public String getLocation(){
        String location = responseJson.getString("resolvedAddress");
        //System.out.println(location);
        return location;
    }

    public ArrayList<String> getDateArray(){
        ArrayList<String> DateArray = new ArrayList<String>();
        for(int i = 0; i<val.length();i++){
            JSONObject dayJson  = val.getJSONObject(i);
            DateArray.add(dateFormatter(dayJson.getString("datetime")));

        }
        //System.out.println(DateArray);
        return DateArray;
    }

    public String dateFormatter(String rawDate){
        String year = rawDate.substring(0,4);
        String monthNum = rawDate.substring(5,7);
        String day = rawDate.substring(8);
        String month = "";

        switch(monthNum){
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }
        return month + " " + day + "," + year;
    }

    public ArrayList<String> getAlertArray(){
        JSONArray alertJsonArray = responseJson.getJSONArray("alerts");
        ArrayList<String> alertArray = new ArrayList<String>();
        for(int i = 0; i<alertJsonArray.length();i++){
            JSONObject alert  = alertJsonArray.getJSONObject(i);

            alertArray.add(alert.getString("description"));

        }
        //System.out.println(alertArray);
        return alertArray;
    }

    public ArrayList<String> getConditionArray(){
        ArrayList<String> ConditionArray = new ArrayList<String>();
        for(int i = 0; i<val.length();i++){
            JSONObject day  = val.getJSONObject(i);

            ConditionArray.add(day.getString("conditions"));

        }
        //System.out.println(ConditionArray);
        return ConditionArray;
    }



}
