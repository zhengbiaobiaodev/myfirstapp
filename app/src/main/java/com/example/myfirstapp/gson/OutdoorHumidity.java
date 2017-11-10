package com.example.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class OutdoorHumidity {


    /**
     * outdoor/humidity : 91
     * outdoor/humidity_chart : [{"data":91,"time":"11-06 22:00"},{"data":91,"time":"11-06 23:00"},{"data":91,"time":"11-07 00:00"},{"data":91,"time":"11-07 01:00"},{"data":91,"time":"11-07 02:00"},{"data":90.97166666666668,"time":"11-07 03:00"},{"data":90.43333333333334,"time":"11-07 04:00"},{"data":89.84499999999998,"time":"11-07 05:00"},{"data":89.79833333333333,"time":"11-07 06:00"},{"data":90.34333333333332,"time":"11-07 07:00"},{"data":91.495,"time":"11-07 08:00"},{"data":92.58666666666666,"time":"11-07 09:00"},{"data":93.97500000000001,"time":"11-07 10:00"},{"data":93.2,"time":"11-07 11:00"},{"data":94.04,"time":"11-07 12:00"},{"data":94.22500000000001,"time":"11-07 13:00"},{"data":93.98,"time":"11-07 14:00"},{"data":94.24,"time":"11-07 15:00"},{"data":93.79666666666667,"time":"11-07 16:00"},{"data":93,"time":"11-07 17:00"},{"data":92.12333333333333,"time":"11-07 18:00"},{"data":92,"time":"11-07 19:00"},{"data":91.96166666666666,"time":"11-07 20:00"},{"data":91.96166666666666,"time":"11-07 21:00"}]
     */

    @SerializedName("outdoor/humidity")
    private Double outdoorHumidity;
    @SerializedName("outdoor/humidity_chart")
    private List<OutdoorHumidityChartBean> outdoorHumidityChart;

    public Double getOutdoorHumidity() {
        return outdoorHumidity;
    }

    public void setOutdoorHumidity(Double outdoorHumidity) {
        this.outdoorHumidity = outdoorHumidity;
    }

    public List<OutdoorHumidityChartBean> getOutdoorHumidityChart() {
        return outdoorHumidityChart;
    }

    public void setOutdoorHumidityChart(List<OutdoorHumidityChartBean> outdoorHumidityChart) {
        this.outdoorHumidityChart = outdoorHumidityChart;
    }

    public static class OutdoorHumidityChartBean {
        /**
         * data : 91.0
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
