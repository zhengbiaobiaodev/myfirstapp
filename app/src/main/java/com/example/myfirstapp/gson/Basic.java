package com.example.myfirstapp.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 2017/10/21.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
