package com.example.myfirstapp.gson;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class TemperatureData {

    /**
     * temperature : 26.94
     * temperature_chart : [{"data":27.613999999999997,"time":"11-06 22:00"},{"data":27.4425,"time":"11-06 23:00"},{"data":27.611666666666665,"time":"11-07 00:00"},{"data":27.375,"time":"11-07 01:00"},{"data":27.189999999999998,"time":"11-07 02:00"},{"data":27.388333333333335,"time":"11-07 03:00"},{"data":27.570000000000004,"time":"11-07 04:00"},{"data":27.47,"time":"11-07 05:00"},{"data":27.488333333333333,"time":"11-07 06:00"},{"data":27.403333333333336,"time":"11-07 07:00"},{"data":27.446666666666673,"time":"11-07 08:00"},{"data":27.051666666666662,"time":"11-07 09:00"},{"data":27.565,"time":"11-07 10:00"},{"data":27.271666666666665,"time":"11-07 11:00"},{"data":26.998333333333335,"time":"11-07 12:00"},{"data":27.378333333333334,"time":"11-07 13:00"},{"data":27.400000000000002,"time":"11-07 14:00"},{"data":27.366666666666664,"time":"11-07 15:00"},{"data":27.351666666666663,"time":"11-07 16:00"},{"data":27.416666666666668,"time":"11-07 17:00"},{"data":27.518333333333334,"time":"11-07 18:00"},{"data":27.711666666666662,"time":"11-07 19:00"},{"data":27.64333333333333,"time":"11-07 20:00"},{"data":27.224999999999998,"time":"11-07 21:00"}]
     */

    private Double temperature;
    private List<TemperatureChartBean> temperature_chart;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public List<TemperatureChartBean> getTemperature_chart() {
        return temperature_chart;
    }

    public void setTemperature_chart(List<TemperatureChartBean> temperature_chart) {
        this.temperature_chart = temperature_chart;
    }

    public static class TemperatureChartBean {
        /**
         * data : 27.613999999999997
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
