package com.example.myfirstapp.gson;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class PM25 {

    /**
     * PM25 : 62.0
     * PM25_chart : [{"data":32.0925,"time":"11-06 23:00"},{"data":30.386666666666667,"time":"11-07 00:00"},{"data":30.353333333333335,"time":"11-07 01:00"},{"data":29.385,"time":"11-07 02:00"},{"data":28.87166666666667,"time":"11-07 03:00"},{"data":28.398333333333337,"time":"11-07 04:00"},{"data":28.738333333333333,"time":"11-07 05:00"},{"data":29.615,"time":"11-07 06:00"},{"data":30.941666666666666,"time":"11-07 07:00"},{"data":32.788333333333334,"time":"11-07 08:00"},{"data":37.245,"time":"11-07 09:00"},{"data":40.37499999999999,"time":"11-07 10:00"},{"data":44.46333333333333,"time":"11-07 11:00"},{"data":52.531666666666666,"time":"11-07 12:00"},{"data":65.99833333333333,"time":"11-07 13:00"},{"data":63.533333333333324,"time":"11-07 14:00"},{"data":57.72333333333333,"time":"11-07 15:00"},{"data":60.06999999999999,"time":"11-07 16:00"},{"data":54.20333333333334,"time":"11-07 17:00"},{"data":51.42833333333334,"time":"11-07 18:00"},{"data":49.835,"time":"11-07 19:00"},{"data":51.548333333333325,"time":"11-07 20:00"},{"data":67.955,"time":"11-07 21:00"},{"data":76.636,"time":"11-07 22:00"}]
     */

    private Double PM25;
    private List<PM25ChartBean> PM25_chart;

    public Double getPM25() {
        return PM25;
    }

    public void setPM25(Double PM25) {
        this.PM25 = PM25;
    }

    public List<PM25ChartBean> getPM25_chart() {
        return PM25_chart;
    }

    public void setPM25_chart(List<PM25ChartBean> PM25_chart) {
        this.PM25_chart = PM25_chart;
    }

    public static class PM25ChartBean {
        /**
         * data : 32.0925
         * time : 11-06 23:00
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
