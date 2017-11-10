package com.example.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 2017/11/5.
 */

public class ChartBean {

    private TemAndHumBean mTemAndHum;

    @SerializedName("air_quality")
    private AirQualityBean mAirQuality;

    public TemAndHumBean getTemAndHum() {
        return mTemAndHum;
    }

    public void setTemAndHum(TemAndHumBean TemAndHum) {
        this.mTemAndHum = TemAndHum;
    }

    public AirQualityBean getAirQuality() {
        return mAirQuality;
    }

    public void setAirQuality(AirQualityBean airQuality) {
        this.mAirQuality = airQuality;
    }

    public static class TemAndHumBean {

//        当前的数据
        private Double mHumidity;

        @SerializedName("outdoor/humidity")
        private Double mOutdoorHumidity;

        @SerializedName("outdoor/temperature")
        private Double mOutdoorTemperature;

        private Double mTemperature;

        private Double mWeather;

//        折线图数据
        @SerializedName("humidity_chart")
        private List<HumidityChartBean> mHumidityChart;

        @SerializedName("outdoor/humidity_chart")
        private List<OutdoorHumidityChartBean> mOutdoorHumidityChart;

        @SerializedName("outdoor/temperature_chart")
        private List<OutdoorTemperatureChartBean> mOutdoorTemperatureChart;

        @SerializedName("temperature_chart")
        private List<TemperatureChartBean> mTemperatureChart;

        public Double getHumidity() {
            return mHumidity;
        }

        public void setHumidity(Double humidity) {
            this.mHumidity = humidity;
        }

        public Double getOutdoorHumidity() {
            return mOutdoorHumidity;
        }

        public void setOutdoorHumidity(Double outdoorHumidity) {
            this.mOutdoorHumidity = outdoorHumidity;
        }

        public Double getOutdoorTemperature() {
            return mOutdoorTemperature;
        }

        public void setOutdoorTemperature(Double outdoorTemperature) {
            this.mOutdoorTemperature = outdoorTemperature;
        }

        public Double getTemperature() {
            return mTemperature;
        }

        public void setTemperature(Double temperature) {
            this.mTemperature = temperature;
        }

        public Double getWeather() {
            return mWeather;
        }

        public void setWeather(Double weather) {
            this.mWeather = weather;
        }

        public List<HumidityChartBean> getHumidityChart() {
            return mHumidityChart;
        }

        public void setHumidityChart(List<HumidityChartBean> humidityChart) {
            this.mHumidityChart = humidityChart;
        }

        public List<OutdoorHumidityChartBean> getOutdoorHumidityChart() {
            return mOutdoorHumidityChart;
        }

        public void setOutdoorHumidityChart(List<OutdoorHumidityChartBean> outdoorHumidityChart) {
            this.mOutdoorHumidityChart = outdoorHumidityChart;
        }

        public List<OutdoorTemperatureChartBean> getOutdoorTemperatureChart() {
            return mOutdoorTemperatureChart;
        }

        public void setOutdoorTemperatureChart(List<OutdoorTemperatureChartBean> outdoorTemperatureChart) {
            this.mOutdoorTemperatureChart = outdoorTemperatureChart;
        }

        public List<TemperatureChartBean> getTemperatureChart() {
            return mTemperatureChart;
        }

        public void setTemperatureChart(List<TemperatureChartBean> temperatureChart) {
            this.mTemperatureChart = temperatureChart;
        }

        public static class HumidityChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }

        public static class OutdoorHumidityChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }

        public static class OutdoorTemperatureChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }

        public static class TemperatureChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }
    }

    public static class AirQualityBean {

//        当前数据
        private Double CH2O;
        private Double CO2;
        private Double PM25;
        private Double TVOC;

//        折线图数据
        @SerializedName("CH2O_chart")
        private List<CH2OChartBean> CH2OChart;

        @SerializedName("CO2_chart")
        private List<CO2ChartBean> CO2Chart;

        @SerializedName("PM25_chart")
        private List<PM25ChartBean> PM25Chart;

        @SerializedName("TVOC_chart")
        private List<TVOCChartBean> TVOCChart;

        public Double getCH2O() {
            return CH2O;
        }

        public void setCH2O(Double CH2O) {
            this.CH2O = CH2O;
        }

        public Double getCO2() {
            return CO2;
        }

        public void setCO2(Double CO2) {
            this.CO2 = CO2;
        }

        public Double getPM25() {
            return PM25;
        }

        public void setPM25(Double PM25) {
            this.PM25 = PM25;
        }

        public Double getTVOC() {
            return TVOC;
        }

        public void setTVOC(Double TVOC) {
            this.TVOC = TVOC;
        }

        public List<CH2OChartBean> getCH2OChart() {
            return CH2OChart;
        }

        public void setCH2OChart(List<CH2OChartBean> CH2OChart) {
            this.CH2OChart = CH2OChart;
        }

        public List<CO2ChartBean> getCO2Chart() {
            return CO2Chart;
        }

        public void setCO2Chart(List<CO2ChartBean> CO2Chart) {
            this.CO2Chart = CO2Chart;
        }

        public List<PM25ChartBean> getPM25Chart() {
            return PM25Chart;
        }

        public void setPM25Chart(List<PM25ChartBean> PM25Chart) {
            this.PM25Chart = PM25Chart;
        }

        public List<TVOCChartBean> getTVOCChart() {
            return TVOCChart;
        }

        public void setTVOCChart(List<TVOCChartBean> TVOCChart) {
            this.TVOCChart = TVOCChart;
        }

        public static class CH2OChartBean {

            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }

        public static class CO2ChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }

        public static class PM25ChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }

        public static class TVOCChartBean {


            private int mData;
            private String mTime;

            public int getData() {
                return mData;
            }

            public void setData(int data) {
                this.mData = data;
            }

            public String getTime() {
                return mTime;
            }

            public void setTime(String time) {
                this.mTime = time;
            }
        }
    }
}
