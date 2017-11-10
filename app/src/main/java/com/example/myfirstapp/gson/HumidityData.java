package com.example.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class HumidityData {
    /**
     * humidity : 44.29
     * humidity_chart : [{"data":39.88333333333333,"time":"11-06 16:00"},{"data":42.32500000000001,"time":"11-06 17:00"},{"data":42.06333333333333,"time":"11-06 18:00"},{"data":44.26,"time":"11-06 19:00"},{"data":45.27166666666667,"time":"11-06 20:00"},{"data":44.35166666666667,"time":"11-06 21:00"},{"data":45.442,"time":"11-06 22:00"},{"data":44.965,"time":"11-06 23:00"},{"data":44.913333333333334,"time":"11-07 00:00"},{"data":45.083333333333336,"time":"11-07 01:00"},{"data":45.419999999999995,"time":"11-07 02:00"},{"data":45.085,"time":"11-07 03:00"},{"data":44.681666666666665,"time":"11-07 04:00"},{"data":44.81,"time":"11-07 05:00"},{"data":44.47166666666667,"time":"11-07 06:00"},{"data":44.39666666666667,"time":"11-07 07:00"},{"data":44.376666666666665,"time":"11-07 08:00"},{"data":45.906666666666666,"time":"11-07 09:00"},{"data":45.300000000000004,"time":"11-07 10:00"},{"data":45.41333333333333,"time":"11-07 11:00"},{"data":45.718333333333334,"time":"11-07 12:00"},{"data":44.90833333333333,"time":"11-07 13:00"},{"data":45.33833333333333,"time":"11-07 14:00"},{"data":45.14666666666667,"time":"11-07 15:00"}]
     */

    @SerializedName("humidity")
    private Double humidity;
    private List<HumidityChartBean> humidity_chart;

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public List<HumidityChartBean> getHumidity_chart() {
        return humidity_chart;
    }

    public void setHumidity_chart(List<HumidityChartBean> humidity_chart) {
        this.humidity_chart = humidity_chart;
    }

    public static class HumidityChartBean {
        /**
         * data : 39.88333333333333
         * time : 11-06 16:00
         */

        private Double data;
        private String time;

        public Double getData() {
            return data;
        }

        public void setData(Double data) {
            this.data = data;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
