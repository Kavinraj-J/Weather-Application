package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.apache.http.client.utils.URIBuilder;

public class WeatherApiRequest {
    private String apiEndPoint="https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";
    private String location="London, UK";
    private String startDate=null; //optional (omit for forecast)
    private String endDate=null; //optional (requires a startDate if present)
    private String unitGroup="us"; //us,metric,uk
    private String apiKey="5XMSRZXGDR8BYV82WN2HQH97W";


    public void APIrequest(){

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

            System.out.println(response.toString());



        }catch (Exception e){
            System.out.println(e);
        }


    }
}
