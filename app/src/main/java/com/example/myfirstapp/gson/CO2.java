package com.example.myfirstapp.gson;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class CO2 {

    /**
     * CO2 : 1580.45
     * CO2_chart : [{"data":1483.458,"time":"11-06 22:00"},{"data":1384.7425,"time":"11-06 23:00"},{"data":1449.125,"time":"11-07 00:00"},{"data":1485.545,"time":"11-07 01:00"},{"data":1594.2816666666668,"time":"11-07 02:00"},{"data":1780.0166666666667,"time":"11-07 03:00"},{"data":1948.4066666666668,"time":"11-07 04:00"},{"data":1863.0550000000003,"time":"11-07 05:00"},{"data":1747.6750000000002,"time":"11-07 06:00"},{"data":1782.236666666667,"time":"11-07 07:00"},{"data":1761.9033333333334,"time":"11-07 08:00"},{"data":2128.8533333333335,"time":"11-07 09:00"},{"data":2274.895,"time":"11-07 10:00"},{"data":1960.5816666666667,"time":"11-07 11:00"},{"data":1690.8166666666666,"time":"11-07 12:00"},{"data":1440.7716666666668,"time":"11-07 13:00"},{"data":1524.5133333333333,"time":"11-07 14:00"},{"data":1514.6016666666667,"time":"11-07 15:00"},{"data":1355.3133333333333,"time":"11-07 16:00"},{"data":1393.3366666666668,"time":"11-07 17:00"},{"data":1484.6116666666667,"time":"11-07 18:00"},{"data":1639.955,"time":"11-07 19:00"},{"data":1909.091666666667,"time":"11-07 20:00"},{"data":1584.6833333333334,"time":"11-07 21:00"}]
     */

    private Double CO2;
    private List<CO2ChartBean> CO2_chart;

    public Double getCO2() {
        return CO2;
    }

    public void setCO2(Double CO2) {
        this.CO2 = CO2;
    }

    public List<CO2ChartBean> getCO2_chart() {
        return CO2_chart;
    }

    public void setCO2_chart(List<CO2ChartBean> CO2_chart) {
        this.CO2_chart = CO2_chart;
    }

    public static class CO2ChartBean {
        /**
         * data : 1483.458
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
