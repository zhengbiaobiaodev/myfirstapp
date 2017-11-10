package com.example.myfirstapp.gson;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class CH2O {

    /**
     * CH2O : 58.6
     * CH2O_chart : [{"data":46.974000000000004,"time":"11-06 22:00"},{"data":34.765,"time":"11-06 23:00"},{"data":42.84166666666667,"time":"11-07 00:00"},{"data":47.315,"time":"11-07 01:00"},{"data":60.568333333333335,"time":"11-07 02:00"},{"data":83.20333333333333,"time":"11-07 03:00"},{"data":103.90166666666666,"time":"11-07 04:00"},{"data":93.30833333333334,"time":"11-07 05:00"},{"data":79.325,"time":"11-07 06:00"},{"data":83.48166666666667,"time":"11-07 07:00"},{"data":81.14,"time":"11-07 08:00"},{"data":125.54333333333334,"time":"11-07 09:00"},{"data":144.13833333333335,"time":"11-07 10:00"},{"data":105.33999999999999,"time":"11-07 11:00"},{"data":72.39833333333334,"time":"11-07 12:00"},{"data":41.685,"time":"11-07 13:00"},{"data":51.965,"time":"11-07 14:00"},{"data":50.714999999999996,"time":"11-07 15:00"},{"data":31.465000000000003,"time":"11-07 16:00"},{"data":36.093333333333334,"time":"11-07 17:00"},{"data":47.12166666666667,"time":"11-07 18:00"},{"data":66.02166666666666,"time":"11-07 19:00"},{"data":98.80833333333334,"time":"11-07 20:00"},{"data":59.646666666666654,"time":"11-07 21:00"}]
     */

    private Double CH2O;
    private List<CH2OChartBean> CH2O_chart;

    public Double getCH2O() {
        return CH2O;
    }

    public void setCH2O(Double CH2O) {
        this.CH2O = CH2O;
    }

    public List<CH2OChartBean> getCH2O_chart() {
        return CH2O_chart;
    }

    public void setCH2O_chart(List<CH2OChartBean> CH2O_chart) {
        this.CH2O_chart = CH2O_chart;
    }

    public static class CH2OChartBean {
        /**
         * data : 46.974000000000004
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
