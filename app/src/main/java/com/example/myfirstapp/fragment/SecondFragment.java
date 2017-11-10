package com.example.myfirstapp.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.gson.CH2O;
import com.example.myfirstapp.gson.CO2;
import com.example.myfirstapp.gson.ChartBean;
import com.example.myfirstapp.gson.Forecast;
import com.example.myfirstapp.gson.HumidityData;
import com.example.myfirstapp.gson.OutdoorHumidity;
import com.example.myfirstapp.gson.OutdoorTemperature;
import com.example.myfirstapp.gson.PM25;
import com.example.myfirstapp.gson.TVOC;
import com.example.myfirstapp.gson.TemperatureData;
import com.example.myfirstapp.util.HttpUtil;
import com.example.myfirstapp.util.Utility;
import com.google.gson.Gson;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Admin on 2017/10/21.
 */

public class SecondFragment extends Fragment {
    private static final String TAG = "SecondFragment";

    private static final String KEY_CHART = "chart";

    private LinearLayout mRootLinearLayout;

    private LinearLayout mHumLinearLayout;      //室内湿度图
    private LinearLayout mOutHumLinearLayout;   //室外湿度图

    private LinearLayout mTemLinearLayout;      //室内温度图
    private LinearLayout mOutTemLinearLayout;   //室外温度图

    private LinearLayout mCH2OLinearLayout;      //CH2O含量图
    private LinearLayout mCO2LinearLayout;       //CO2含量图

    private LinearLayout mPM25LinearLayout;       //PM25含量图
    private LinearLayout mTVOCLinearLayout;       //TVOC含量图

    private TextView mHumTextView;             //当前室内的湿度
    private TextView mOutHumTextView;          //当前室外的湿度

    private TextView mTemTextView;             //当前室内的温度
    private TextView mOutTemTextView;          //当前室外的温度

    private TextView mCH2OTextView;            //当前CH2O含量
    private TextView mCO2TextView;             //当前CO2含量

    private TextView mPM25TextView;            //当前PM25含量
    private TextView mTVOCTextView;            //当前TVOC含量

    private SwipeRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        mRootLinearLayout = view.findViewById(R.id.chart_root_view);
        mHumLinearLayout = view.findViewById(R.id.chart_humidity);
        mOutHumLinearLayout = view.findViewById(R.id.chart_outdoor_humidity);
        mTemLinearLayout = view.findViewById(R.id.chart_temperature);
        mOutTemLinearLayout = view.findViewById(R.id.chart_outdoor_temperature);
        mCH2OLinearLayout = view.findViewById(R.id.chart_CH2O);
        mCO2LinearLayout = view.findViewById(R.id.chart_CO2);
        mPM25LinearLayout = view.findViewById(R.id.chart_PM25);
        mTVOCLinearLayout = view.findViewById(R.id.chart_TVOC);
        mRefreshLayout = view.findViewById(R.id.chart_swipe_refresh_layout);

        mHumTextView = view.findViewById(R.id.humidity);
        mOutHumTextView = view.findViewById(R.id.outdoor_humidity);
        mTemTextView = view.findViewById(R.id.temperature);
        mOutTemTextView = view.findViewById(R.id.outdoor_temperature);
        mCH2OTextView = view.findViewById(R.id.CH2O);
        mCO2TextView = view.findViewById(R.id.CO2);
        mPM25TextView = view.findViewById(R.id.PM25);
        mTVOCTextView = view.findViewById(R.id.TVOC);

        requestHumidityData();
        requestOutdoorHumidityData();
        requestTemperatureData();
        requestOutdoorTemperatureData();
        requestCH2OData();
        requestCO2Data();
        requestPM25Data();
        requestTVOCData();
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestHumidityData();
                requestOutdoorHumidityData();
                requestTemperatureData();
                requestOutdoorTemperatureData();
                requestCH2OData();
                requestCO2Data();
                requestPM25Data();
                requestTVOCData();
            }
        });
        return view;
    }

    private void requestHumidityData() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/humidity", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final HumidityData humidityData = new Gson().fromJson(responseText, HumidityData.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(humidityData);
                    }
                });
            }
        });
    }

    private void requestOutdoorHumidityData() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/outdoor/humidity", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final OutdoorHumidity outdoorHumidityData = new Gson().fromJson(responseText, OutdoorHumidity.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(outdoorHumidityData);
                    }
                });
            }
        });
    }

    private void requestTemperatureData() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/temperature",
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final TemperatureData temperatureData = new Gson().fromJson(responseText, TemperatureData.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(temperatureData);
                    }
                });
            }
        });
    }

    private void requestOutdoorTemperatureData() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/outdoor/temperature",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        final OutdoorTemperature outdoorTemperatureData = new Gson().fromJson(responseText, OutdoorTemperature.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DrawingLine(outdoorTemperatureData);
                            }
                        });
                    }
                });
    }

    private void requestCH2OData() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/CH2O", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final CH2O CH2OData = new Gson().fromJson(responseText, CH2O.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(CH2OData);
                    }
                });
            }
        });
    }

    private void requestCO2Data() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/CO2", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final CO2 CO2Data = new Gson().fromJson(responseText, CO2.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(CO2Data);
                    }
                });
            }
        });
    }

    private void requestPM25Data() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/PM25", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final PM25 PM25Data = new Gson().fromJson(responseText, PM25.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(PM25Data);
                    }
                });
            }
        });
    }

    private void requestTVOCData() {
        HttpUtil.sendOkHttpRequest("http://120.25.242.228/api/v1.0/sensor_data_for_android/TVOC", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "获取信息失败", Toast.LENGTH_SHORT).show();
                        mRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final TVOC TVOCData = new Gson().fromJson(responseText, TVOC.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DrawingLine(TVOCData);
                        mRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void DrawingLine(HumidityData chartBean) {

        mHumTextView.setText("当前的室内湿度是 : " + chartBean.getHumidity());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getHumidity_chart().size(); i++) {
            series.add(i, chartBean.getHumidity_chart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室内湿度");     //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室内湿度变化图"); //设置图表标题
        mRenderer.setYAxisMax(100);   //设置Y轴的最大值
        mRenderer.setYAxisMin(0);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getHumidity_chart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getHumidity_chart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mHumLinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(OutdoorHumidity chartBean) {

        mOutHumTextView.setText("当前的室外湿度是 : " + chartBean.getOutdoorHumidity());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getOutdoorHumidityChart().size(); i++) {
            series.add(i, chartBean.getOutdoorHumidityChart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室外湿度");     //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室外湿度变化图"); //设置图表标题
        mRenderer.setYAxisMax(100);   //设置Y轴的最大值
        mRenderer.setYAxisMin(0);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getOutdoorHumidityChart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getOutdoorHumidityChart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mOutHumLinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(TemperatureData chartBean) {

        mTemTextView.setText("当前的室内温度是 : " + chartBean.getTemperature());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getTemperature_chart().size(); i++) {
            series.add(i, chartBean.getTemperature_chart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室内温度/℃");     //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室内温度变化图"); //设置图表标题
        mRenderer.setYAxisMax(30);   //设置Y轴的最大值
        mRenderer.setYAxisMin(24);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(7);

        for (int i = 0; i < chartBean.getTemperature_chart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getTemperature_chart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mTemLinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(OutdoorTemperature chartBean) {

        mOutTemTextView.setText("当前的室外温度是 : " + chartBean.getOutdoorTemperature());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getOutdoorTemperatureChart().size(); i++) {
            series.add(i, chartBean.getOutdoorTemperatureChart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室外温度");     //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室外温度变化图"); //设置图表标题
        mRenderer.setYAxisMax(35);   //设置Y轴的最大值
        mRenderer.setYAxisMin(10);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getOutdoorTemperatureChart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getOutdoorTemperatureChart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mOutTemLinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(CH2O chartBean) {

        mCH2OTextView.setText("当前的室内CH2O是 : " + chartBean.getCH2O());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getCH2O_chart().size(); i++) {
            series.add(i, chartBean.getCH2O_chart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室内CH2O");     //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室内CH2O变化图"); //设置图表标题
        mRenderer.setYAxisMax(200);   //设置Y轴的最大值
        mRenderer.setYAxisMin(0);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getCH2O_chart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getCH2O_chart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mCH2OLinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(CO2 chartBean) {

        mCO2TextView.setText("当前的室内CO2是 : " + chartBean.getCO2());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getCO2_chart().size(); i++) {
            series.add(i, chartBean.getCO2_chart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室内CO2");   //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室内CO2变化图"); //设置图表标题
        mRenderer.setYAxisMax(3000);   //设置Y轴的最大值
        mRenderer.setYAxisMin(100);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getCO2_chart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getCO2_chart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mCO2LinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(PM25 chartBean) {

        mPM25TextView.setText("当前的室内PM2.5是 : " + chartBean.getPM25());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getPM25_chart().size(); i++) {
            series.add(i, chartBean.getPM25_chart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室内PM2.5(μg/m³)");   //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室内PM2.5变化图"); //设置图表标题
        mRenderer.setYAxisMax(40);   //设置Y轴的最大值
        mRenderer.setYAxisMin(0);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getPM25_chart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getPM25_chart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mPM25LinearLayout.addView(chartView, 0);
    }

    private void DrawingLine(TVOC chartBean) {

        mTVOCTextView.setText("当前的室内TVOC是 : " + chartBean.getTVOC());

        XYMultipleSeriesDataset dataset_hum = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        for (int i = 0; i < chartBean.getTVOC_chart().size(); i++) {
            series.add(i, chartBean.getTVOC_chart().get(i).getData());
        }
        dataset_hum.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.RED);
        renderer.setDisplayBoundingPoints(true);
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(renderer);

        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        mRenderer.setXTitle("时间/h");     //设置X轴标题
        mRenderer.setYTitle("室内TVOC");   //设置Y轴标题
        mRenderer.setChartTitle("过去24小时室内TVOC变化图"); //设置图表标题
        mRenderer.setYAxisMax(600);   //设置Y轴的最大值
        mRenderer.setYAxisMin(0);   //设置Y轴的最小值
        mRenderer.setXLabels(0);
        mRenderer.setYLabels(10);

        for (int i = 0; i < chartBean.getTVOC_chart().size(); i++) {
            mRenderer.addXTextLabel(i, chartBean.getTVOC_chart().get(i).getTime().split(" ")[1].split(":")[0]);
        }
        mRenderer.setPointSize(10f);     //设置点的大小
        mRenderer.setChartTitleTextSize(40);  //设置图表标题文字大小
        mRenderer.setAxisTitleTextSize(40);  //设置轴标题文本大小
        mRenderer.setMargins(new int[] { 60, 65, 30, 30 });//设置图表的外边框(上/左/下/右)
        mRenderer.setShowGrid(true);
        mRenderer.setLabelsTextSize(30f);
        mRenderer.setXLabelsColor(Color.BLACK);
        mRenderer.setYLabelsColor(0,Color.BLACK);
        mRenderer.setPanEnabled(false, false);
        mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));

        GraphicalView chartView = ChartFactory.getCubeLineChartView(getActivity(), dataset_hum, mRenderer, 0.1F);
        mTVOCLinearLayout.addView(chartView, 0);
    }
}
