package com.example.myfirstapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.R;
import com.example.myfirstapp.gson.Forecast;
import com.example.myfirstapp.gson.Weather;
import com.example.myfirstapp.service.AutoUpdateService;
import com.example.myfirstapp.util.HttpUtil;
import com.example.myfirstapp.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Admin on 2017/10/21.
 */

public class FirstFragment extends Fragment {
    private final static String KEY_WEATHER = "weather";
    private final static String KEY_BING = "bing_pic";

    private ScrollView mWeatherLayout;
    private TextView mTitleCityText;
    private TextView mTmpText;
    private TextView mInfoText;
    private TextView mUpdateTimeText;
    private LinearLayout mForecastItemLayout;
    private TextView mAQIIndexText;
    private TextView mPMIndexText;
    private TextView mComfortText;
    private TextView mCarRushText;
    private TextView mSportText;
    private ImageView mBingImageView;
    public SwipeRefreshLayout mRefreshLayout;

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //为了全屏
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getActivity().getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        mWeatherLayout = view.findViewById(R.id.weather_scroll_view);
        mTitleCityText = view.findViewById(R.id.weather_title_city);
        mTmpText = view.findViewById(R.id.weather_temperature_text_view);
        mInfoText = view.findViewById(R.id.weather_info_text_view);
        mUpdateTimeText = view.findViewById(R.id.weather_update_time_text);
        mForecastItemLayout = view.findViewById(R.id.forecast_layout);
        mAQIIndexText = view.findViewById(R.id.weather_aqi_text);
        mPMIndexText = view.findViewById(R.id.weather_pm25_text);
        mComfortText = view.findViewById(R.id.weather_comfort_text);
        mCarRushText = view.findViewById(R.id.weather_car_wash_text);
        mSportText = view.findViewById(R.id.weather_sport_text);
        mBingImageView = view.findViewById(R.id.weather_bg_bing_pic_img);
        mRefreshLayout = view.findViewById(R.id.weather_swipe_refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String weatherContent = sp.getString(KEY_WEATHER, null);
        if (weatherContent != null) {
            Weather weather = Utility.handleWeatherResponse(weatherContent);
            showWeatherInfo(weather);
        } else {
            mWeatherLayout.setVisibility(View.INVISIBLE);
            requestWeather();
        }
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather();
            }
        });
        String bingPic = sp.getString(KEY_BING, null);
        if (bingPic != null) {
            Glide.with(getActivity()).load(bingPic).into(mBingImageView);
        } else {
            loadBingPic();
        }
        return view;
    }

    private void loadBingPic() {
        final String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                editor.putString(KEY_BING, bingPic);
                editor.apply();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(bingPic).into(mBingImageView);
                    }
                });
            }
        });
    }

    private void requestWeather() {
        final String weatherUrl = "http://guolin.tech/api/weather?cityid=CN101190101&" +
                "key=aca6c73a208e4bed95b5560d8e781903";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), R.string.request_weather_info_failed, Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                            editor.putString(KEY_WEATHER, responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(getActivity(), R.string.request_weather_info_failed, Toast.LENGTH_SHORT).show();
                        }
                        mRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    private void showWeatherInfo(Weather weather) {
        if (weather != null && "ok".equals(weather.status)) {
            mTitleCityText.setText(weather.basic.cityName);
            mUpdateTimeText.setText(weather.basic.update.updateTime.split(" ")[1]);
            mTmpText.setText(weather.now.temperature + "℃");
            mInfoText.setText(weather.now.more.info);
            mForecastItemLayout.removeAllViews();
            for (Forecast forecast : weather.forecastList) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_weather_forecast_item,
                        mForecastItemLayout, false);
                TextView dateText = view.findViewById(R.id.forecast_date_text);
                TextView infoText = view.findViewById(R.id.forecast_info_text);
                TextView maxTmpText = view.findViewById(R.id.forecast_max_tmp_text);
                TextView minTmpText = view.findViewById(R.id.forecast_min_tmp_text);
                dateText.setText(forecast.date);
                infoText.setText(forecast.more.info);
                maxTmpText.setText(forecast.temperature.max);
                minTmpText.setText(forecast.temperature.min);
                mForecastItemLayout.addView(view);
            }
            if (weather.aqi != null) {
                mAQIIndexText.setText(weather.aqi.city.aqi);
                mPMIndexText.setText(weather.aqi.city.pm25);
            }
            mComfortText.setText(getResources().getString(R.string.comfort_title) + weather.suggestion.comfort.info);
            mCarRushText.setText(getResources().getString(R.string.carRush_title) + weather.suggestion.carWash.info);
            mSportText.setText(getResources().getString(R.string.sport_title) + weather.suggestion.sport.info);
            mWeatherLayout.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), AutoUpdateService.class);
            getActivity().startService(intent);
        } else {
            Toast.makeText(getActivity(), R.string.request_weather_info_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
