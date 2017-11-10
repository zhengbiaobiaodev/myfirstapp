package com.example.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class OutdoorTemperature {


    /**
     * outdoor/temperature : 15.0
     * outdoor/temperature_chart : [{"data":13.581999999999999,"time":"11-06 22:00"},{"data":13,"time":"11-06 23:00"},{"data":13,"time":"11-07 00:00"},{"data":13,"time":"11-07 01:00"},{"data":13.013333333333334,"time":"11-07 02:00"},{"data":12.971666666666666,"time":"11-07 03:00"},{"data":12.433333333333332,"time":"11-07 04:00"},{"data":11.844999999999999,"time":"11-07 05:00"},{"data":11.798333333333334,"time":"11-07 06:00"},{"data":12.343333333333334,"time":"11-07 07:00"},{"data":15.096666666666666,"time":"11-07 08:00"},{"data":17.985,"time":"11-07 09:00"},{"data":21.14833333333333,"time":"11-07 10:00"},{"data":19.933333333333334,"time":"11-07 11:00"},{"data":21.10833333333333,"time":"11-07 12:00"},{"data":21.224999999999998,"time":"11-07 13:00"},{"data":20.98,"time":"11-07 14:00"},{"data":21.24,"time":"11-07 15:00"},{"data":20.796666666666667,"time":"11-07 16:00"},{"data":18.625,"time":"11-07 17:00"},{"data":17.064999999999998,"time":"11-07 18:00"},{"data":16.491666666666664,"time":"11-07 19:00"},{"data":15.964999999999998,"time":"11-07 20:00"},{"data":15.961666666666666,"time":"11-07 21:00"}]
     */

    @SerializedName("outdoor/temperature")
    private Double outdoorTemperature;
    @SerializedName("outdoor/temperature_chart")
    private List<OutdoorTemperatureChartBean> outdoorTemperatureChart;

    public Double getOutdoorTemperature() {
        return outdoorTemperature;
    }

    public void setOutdoorTemperature(Double outdoorTemperature) {
        this.outdoorTemperature = outdoorTemperature;
    }

    public List<OutdoorTemperatureChartBean> getOutdoorTemperatureChart() {
        return outdoorTemperatureChart;
    }

    public void setOutdoorTemperatureChart(List<OutdoorTemperatureChartBean> outdoorTemperatureChart) {
        this.outdoorTemperatureChart = outdoorTemperatureChart;
    }

    public static class OutdoorTemperatureChartBean {
        /**
         * data : 13.581999999999999
         * time : 11-06 22:00
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
