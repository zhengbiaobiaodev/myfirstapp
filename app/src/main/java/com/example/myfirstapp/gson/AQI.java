package com.example.myfirstapp.gson;

/**
 * Created by Admin on 2017/10/21.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;

        public String pm25;
    }
}
