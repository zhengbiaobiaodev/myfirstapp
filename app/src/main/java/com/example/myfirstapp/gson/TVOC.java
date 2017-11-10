package com.example.myfirstapp.gson;

import java.util.List;

/**
 * Created by Admin on 2017/11/7.
 */

public class TVOC {


    /**
     * TVOC : 186.0
     * TVOC_chart : [{"data":88.1325,"time":"11-06 23:00"},{"data":108.19166666666666,"time":"11-07 00:00"},{"data":119.40333333333332,"time":"11-07 01:00"},{"data":152.61999999999998,"time":"11-07 02:00"},{"data":208.92499999999998,"time":"11-07 03:00"},{"data":260.7083333333333,"time":"11-07 04:00"},{"data":234.37,"time":"11-07 05:00"},{"data":199.36666666666667,"time":"11-07 06:00"},{"data":209.63166666666666,"time":"11-07 07:00"},{"data":203.6316666666667,"time":"11-07 08:00"},{"data":315.10999999999996,"time":"11-07 09:00"},{"data":361.3433333333333,"time":"11-07 10:00"},{"data":264.485,"time":"11-07 11:00"},{"data":182.15,"time":"11-07 12:00"},{"data":105.46,"time":"11-07 13:00"},{"data":131.03166666666667,"time":"11-07 14:00"},{"data":127.96166666666666,"time":"11-07 15:00"},{"data":79.80999999999999,"time":"11-07 16:00"},{"data":91.32000000000001,"time":"11-07 17:00"},{"data":118.95333333333333,"time":"11-07 18:00"},{"data":166.4466666666667,"time":"11-07 19:00"},{"data":248.09166666666667,"time":"11-07 20:00"},{"data":150.18833333333333,"time":"11-07 21:00"},{"data":64.694,"time":"11-07 22:00"}]
     */

    private double TVOC;
    private List<TVOCChartBean> TVOC_chart;

    public double getTVOC() {
        return TVOC;
    }

    public void setTVOC(double TVOC) {
        this.TVOC = TVOC;
    }

    public List<TVOCChartBean> getTVOC_chart() {
        return TVOC_chart;
    }

    public void setTVOC_chart(List<TVOCChartBean> TVOC_chart) {
        this.TVOC_chart = TVOC_chart;
    }

    public static class TVOCChartBean {
        /**
         * data : 88.1325
         * time : 11-06 23:00
         */

        private double data;
        private String time;

        public double getData() {
            return data;
        }

        public void setData(double data) {
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
