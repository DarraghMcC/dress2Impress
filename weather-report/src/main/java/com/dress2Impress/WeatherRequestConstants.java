package com.dress2Impress;

public interface WeatherRequestConstants {

    interface URL {
        String CURRENT_WEATHER = "http://samples.openweathermap.org/data/2.5/find";
    }

    interface WeatherApp {
        String USER_ID = "54faad966c85d3dd82c59621798e0fdb";
    }

    interface Parameter {
        String LOCATION = "q";
        String UNITS = "units";
        String APP_ID = "appid";
    }

    interface ResponseValues {
        String LIST = "list";
        String MAIN = "main";
        String TEMP = "temp";

    }

    interface Location {
        String LONDON = "london";
    }

}
