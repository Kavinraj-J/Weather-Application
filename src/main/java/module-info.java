module com.comicbolt.weather_application_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.comicbolt.weather_application_fx to javafx.fxml;
    exports com.comicbolt.weather_application_fx;
}