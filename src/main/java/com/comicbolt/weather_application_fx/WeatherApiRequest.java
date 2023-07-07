package com.comicbolt.weather_application_fx;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
            //hours = responseJson.getJSONArray("hours");



        }catch (Exception e){
            System.out.println("Sorry Invalid City entered");
        }

    }

//    public void setLocation(String location){
//        this.location = location;
//        System.out.println(this.location);
//    }

    public int getCurrentTemp(){
        ArrayList<Integer> hourArray = getHourArray();
        int hour = Integer.parseInt(getTimeAtLocation().substring(0,getTimeAtLocation().indexOf(":")));
        System.out.println(getHourTimeArray());
        if(AmOrPm().equals("PM")){
            hour = hour + 12;
        }
        System.out.println(hour);
        int currentTemp = hourArray.get(hour);

        return currentTemp;


    }

    public ArrayList<String> getImgPathArray(){
        ArrayList<String> imgPaths = new ArrayList<String>();

        for(int i = 0; i<val.length();i++){
            JSONObject day  = val.getJSONObject(i);
            imgPaths.add("Images/WeatherConditionIcons/"+day.getString("icon")+".png");

        }
        return imgPaths;
    }

    public ArrayList<String> getImgPathHourlyArray(){
        ArrayList<String> imgPaths = new ArrayList<String>();
        JSONObject currentDay = val.getJSONObject(0);
        JSONArray hours = currentDay.getJSONArray("hours");
        for(int i = 0; i<hours.length();i++){
            JSONObject icon  = hours.getJSONObject(i);
            imgPaths.add("Images/WeatherConditionIcons/"+icon.getString("icon")+".png");

        }
        return imgPaths;
    }

    public ArrayList<Integer> getHourArray(){
        JSONObject currentDay = val.getJSONObject(0);
        JSONArray hours = currentDay.getJSONArray("hours");
        ArrayList<Integer> hourlyTemp = new ArrayList<Integer>();
        for(int i = 0; i<hours.length();i++){
            JSONObject temp  = hours.getJSONObject(i);

            hourlyTemp.add((int) Math.round(temp.getDouble("temp")));
        }


        return hourlyTemp;

    }

    public ArrayList<String> getHourTimeArray(){
        JSONObject currentDay = val.getJSONObject(0);
        JSONArray hours = currentDay.getJSONArray("hours");
        ArrayList<String> hourlyTime = new ArrayList<String>();
        for(int i = 0; i<hours.length();i++){
            JSONObject temp  = hours.getJSONObject(i);

            int hour = Integer.parseInt(temp.getString("datetime").substring(0,2));
            if(hour == 0){
                hourlyTime.add((hour + 12) + " AM");
            }
            else if(hour > 11){
                hourlyTime.add((hour - 12) + " PM");
            }
            else{
                hourlyTime.add((hour) + " AM");
            }
        }


        return hourlyTime;

    }
//TODO Finish time label and setup

    public String getTimeAtLocation(){
        String tz = responseJson.getString("timezone");
        Date currentTime = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone(tz));
        System.out.println(sdf.format(currentTime));
        return sdf.format(currentTime).substring(0,6);

    }

    public String AmOrPm(){
        String tz = responseJson.getString("timezone");
        Date currentTime = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        sdf.setTimeZone(TimeZone.getTimeZone(tz));

       return sdf.format(currentTime).substring(6);

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