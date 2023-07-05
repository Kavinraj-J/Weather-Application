package com.comicbolt.weather_application_fx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherApiRequest {
    private String apiEndPoint="https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    ApiKey key = new ApiKey();
    private String apiKey= key.apiKey;
    private String ApiResponse;
    private JSONObject responseJson;
    private JSONArray val;
    private JSONArray hours;




    //private String location;

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
            hours = responseJson.getJSONArray("hours");



        }catch (Exception e){
            System.out.println("Sorry Invalid City entered");
        }

    }

//    public void setLocation(String location){
//        this.location = location;
//        System.out.println(this.location);
//    }

    public int getCurrentTemp(){
        JSONObject currentDay = val.getJSONObject(0);
        int currentTemp = (int) Math.round(currentDay.getDouble("temp"));
        ZonedDateTime currentDate = ZonedDateTime.now( ZoneOffset.UTC );
        System.out.println(currentDate);

        return currentTemp;

    }
//TODO Finish time label and setup

    public String getTimeAtLocation(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now( ZoneOffset.UTC );
        double hour = Integer.parseInt(zonedDateTime.toString().substring(11,13));
        int min = Integer.parseInt(zonedDateTime.toString().substring(14,16));
        double tzoffset = responseJson.getDouble("tzoffset");

        if(hour > 12){
            hour = hour - 12;
        }

        hour = hour + tzoffset;

        if(hour > 12){
            hour = hour - 12;
        }

        if(hour - ((int) hour ) == 0.5){
            min = min + 30;
            if(min > 59){
                min = min - 60;
            }
        }
        //System.out.println(hour +":" + min );



        if(min < 10){
            return ((int)hour) + ":0" + min;
        }

        return ((int)hour) + ":" + min;


    }

    public String AmOrPm(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now( ZoneOffset.UTC );
        int hour = Integer.parseInt(zonedDateTime.toString().substring(11,13));
        int tzoffset = responseJson.getInt("tzoffset"); //6
        boolean Am = false;
        boolean Pm = false; //2.00pm     14.00
        if(hour > 11){
            Pm = true;
            Am = false;
        }

        if(hour > 12){
            hour = hour - 12; //2
        }

        hour = hour + tzoffset; //8

        if(hour > 12){
            Am = true;
            Pm = false;

            hour = hour - 12;
        }

        if(Am){
            return "AM";
        }
        if(Pm){
            return "PM";
        }

        return " ";

    }

    public String getDayOfWeek(String rawDate){
        int year = Integer.parseInt(rawDate.substring(0,4));
        int monthNum = Integer.parseInt(rawDate.substring(5,7));
        int date = Integer.parseInt(rawDate.substring(8));
        String day = "";

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,monthNum-1,date);
        int dayNum = calendar.get(calendar.DAY_OF_WEEK);

        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"};
        //System.out.println(daysOfWeek[dayNum-1]);
        return daysOfWeek[dayNum-1];

    }

    public ArrayList<String> getDayOfWeekArray(){
        ArrayList<String> days = new ArrayList<String>();

        for(int i = 0; i<val.length();i++){
            JSONObject dayJson  = val.getJSONObject(i);
            days.add(getDayOfWeek(dayJson.getString("datetime")));

        }
       // System.out.println(days);
        return days;
    }

    public ArrayList<Integer> getMaxTempArray(){
        ArrayList<Integer> MaxTempArray = new ArrayList<Integer>();
        for(int i = 0; i<val.length();i++){
            JSONObject day  = val.getJSONObject(i);

            MaxTempArray.add((int) Math.round(day.getDouble("tempmax")));

        }
        //System.out.println(MaxTempArray);
        return MaxTempArray;
    }

    public ArrayList<Integer> getMinTempArray(){
        ArrayList<Integer> MinTempArray = new ArrayList<Integer>();
        for(int i = 0; i<val.length();i++){
            JSONObject day  = val.getJSONObject(i);

            MinTempArray.add((int) Math.round(day.getDouble("tempmin")));

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
        return month + " " + day + ", " + year;
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