package com.example.myfirstapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

import static com.example.myfirstapp.util.HttpUtil.CONTROLLER;
import static com.example.myfirstapp.util.HttpUtil.TOKEN_PREFS;

/**
 * Created by Admin on 2017/10/21.
 */

public class ThirdFragment extends Fragment {

    private static final String TAG = "ThirdFragment";

    //空气净化器
    private TextView mAirPurifierStatus;
    private Button mStartAirPurifier;
    private Button mStopAirPurifier;

    //空调净化器
    private TextView mAirConditionerStatus;
    private Button mStartAirConditioner;
    private Button mStopAirConditioner;

    //模式
    private Button mChange_wind;
    private Button mAuto_work;
    private Button mClean_wet;
    private Button mMake_hot;
    private Button mMake_cold;

    //摄氏度
    private EditText mTemperatureData;
    private Button mCommitTemperature;

    //风速
    private Button mWindAuto_work;
    private Button mWindGrade_one;
    private Button mWindGrade_two;
    private Button mWindGrade_three;

    //扫风
    private Button mWind_up_down;
    private Button mWind_left_right;
    private Button mWind_upDown_leftRight;
    private Button mWind_close;

    //睡眠
    private Button mStart_sleep;
    private Button mStop_sleep;

    private EditText mSleep_data;
    private Button mCommit_sleep;

    //超强
    private Button mStart_super;
    private Button mStop_super;

    //灯光
    private Button mStart_light;
    private Button mStop_light;

    //健康
    private Button mMake_health;
    private Button mChange_breath;
    private Button mMake_health_breath;
    private Button mHealth_close;

    //干燥/辅热
    private Button mStart_dry;
    private Button mStop_dry;

    //数显
    private Button mSetting_tem;
    private Button mIndoor_tem;

    private SwipeRefreshLayout mRefreshLayout;

    String airConditionStatus = null;
    String airCleanStatus = null;

    //Unicode转中文
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    //将中文的Unicode编码转换成汉字
    public String convert(String str){
        String PREFIX_UNICODE = "\\u";
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf(PREFIX_UNICODE);
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf(PREFIX_UNICODE, begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }

    private static char ascii2Char(String str) {
        String PREFIX_UNICODE = "\\u";
        if (str.length() != 6) {
            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
        }
        if (!PREFIX_UNICODE.equals(str.substring(0, 2))) {
            throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        // 将十六进制转为十进制
        int code = Integer.parseInt(tmp, 16) << 8; // 转为高位，后与地位相加
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16); // 与低8为相加
        return (char) code;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        final SharedPreferences prefs = getActivity().getSharedPreferences(TOKEN_PREFS, 0);

        String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
        if (token != null) {
            Request request = new Request.Builder().url("http://120.25.242.228/api/v1.0/get_actuator").get().build();
            getStatusMethod(token, request);
        }

        mRefreshLayout = view.findViewById(R.id.third_swipe_refresh_layout);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    Request request = new Request.Builder().url("http://120.25.242.228/api/v1.0/get_actuator").get().build();
                    getStatusMethod(token, request);
                }
            }
        });

        mAirPurifierStatus = (TextView) view.findViewById(R.id.Air_purifier_status);

        mStartAirPurifier = (Button) view.findViewById(R.id.start_air_purifier);
        mStartAirPurifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air cleaner\", \"parameter\": \"open\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);

                    Request request_one = new Request.Builder().url("http://120.25.242.228/api/v1.0/get_actuator").get().build();
                    getStatusMethod(token, request_one);
                }
            }
        });

        mStopAirPurifier = (Button) view.findViewById(R.id.stop_air_purifier);
        mStopAirPurifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air cleaner\", \"parameter\": \"off\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);

                    Request request_one = new Request.Builder().url("http://120.25.242.228/api/v1.0/get_actuator").get().build();
                    getStatusMethod(token, request_one);
                }
            }
        });

        mAirConditionerStatus = (TextView) view.findViewById(R.id.Air_conditioner_status);
        mStartAirConditioner = (Button) view.findViewById(R.id.start_air_conditioner);
        mStartAirConditioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/switch\", \"parameter\": \"on\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);

                    Request request_one = new Request.Builder().url("http://120.25.242.228/api/v1.0/get_actuator").get().build();
                    getStatusMethod(token, request_one);
                }
            }
        });
        mStopAirConditioner = (Button) view.findViewById(R.id.stop_air_conditioner);
        mStopAirConditioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/switch\", \"parameter\": \"off\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);

                    Request request_one = new Request.Builder().url("http://120.25.242.228/api/v1.0/get_actuator").get().build();
                    getStatusMethod(token, request_one);
                }
            }
        });


        mChange_wind = (Button) view.findViewById(R.id.change_wind);
        mChange_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/mode\", \"parameter\": \"fan\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mAuto_work = (Button) view.findViewById(R.id.auto_work);
        mAuto_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/mode\", \"parameter\": \"auto\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mClean_wet = (Button) view.findViewById(R.id.clean_wet);
        mClean_wet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/mode\", \"parameter\": \"dry\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mMake_hot = (Button) view.findViewById(R.id.make_hot);
        mMake_hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/mode\", \"parameter\": \"heat\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mMake_cold = (Button) view.findViewById(R.id.make_cold);
        mMake_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/mode\", \"parameter\": \"cool\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mTemperatureData = (EditText) view.findViewById(R.id.temperature_data);
        final String data = mTemperatureData.getText().toString();
        mCommitTemperature = (Button) view.findViewById(R.id.commit_temperature);
        mCommitTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String data = mTemperatureData.getText().toString();
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/temperature\", \"parameter\": " + data +"}");
//                            "{\"topic\": \"air condition/temperature\", \"parameter\": 20}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                    mTemperatureData.setText("");
                }
            }
        });

        mWindAuto_work = (Button) view.findViewById(R.id.wind_auto_work);
        mWindAuto_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/wind\", \"parameter\": \"zero\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mWindGrade_one = (Button) view.findViewById(R.id.wind_grade_one);
        mWindGrade_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/wind\", \"parameter\": \"low\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mWindGrade_two = (Button) view.findViewById(R.id.wind_grade_two);
        mWindGrade_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/wind\", \"parameter\": \"middle\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mWindGrade_three = (Button) view.findViewById(R.id.wind_grade_three);
        mWindGrade_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/wind\", \"parameter\": \"high\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mWind_up_down = (Button) view.findViewById(R.id.wind_up_down);
        mWind_up_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sweep\", \"parameter\": \"seesaw\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mWind_left_right = (Button) view.findViewById(R.id.wind_left_right);
        mWind_left_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sweep\", \"parameter\": \"around\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mWind_upDown_leftRight = (Button) view.findViewById(R.id.wind_upDown_leftRight);
        mWind_upDown_leftRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sweep\", \"parameter\": \"all\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mWind_close = (Button) view.findViewById(R.id.wind_close);
        mWind_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sweep\", \"parameter\": \"none\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mStart_sleep = (Button) view.findViewById(R.id.start_sleep);
        mStart_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sleep\", \"parameter\": \"sleep\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mStop_sleep = (Button) view.findViewById(R.id.stop_sleep);
        mStop_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sleep\", \"parameter\": \"wake\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mSleep_data = (EditText) view.findViewById(R.id.sleep_data);
        final String sleepTime = mSleep_data.getText().toString();
        mCommit_sleep = (Button) view.findViewById(R.id.commit_sleep);
        mCommit_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sleepTime = mSleep_data.getText().toString();
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/sleep time\", \"parameter\": " + sleepTime +"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                    mSleep_data.setText("");
                }
            }
        });

        mStart_super = (Button) view.findViewById(R.id.start_super);
        mStart_super.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/super\", \"parameter\": \"on\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mStop_super = (Button) view.findViewById(R.id.stop_super);
        mStop_super.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/super\", \"parameter\": \"off\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mStart_light = (Button) view.findViewById(R.id.start_light);
        mStart_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/light\", \"parameter\": \"on\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mStop_light = (Button) view.findViewById(R.id.stop_light);
        mStop_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/light\", \"parameter\": \"off\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mMake_health = (Button) view.findViewById(R.id.make_health);
        mMake_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/health\", \"parameter\": \"health\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mChange_breath = (Button) view.findViewById(R.id.change_breath);
        mChange_breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/health\", \"parameter\": \"ventilate\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mMake_health_breath = (Button) view.findViewById(R.id.make_health_breath);
        mMake_health_breath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/health\", \"parameter\": \"both\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mHealth_close = (Button) view.findViewById(R.id.health_close);
        mHealth_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/health\", \"parameter\": \"none\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mStart_dry = (Button) view.findViewById(R.id.start_dry);
        mStart_dry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/dry/heat\", \"parameter\": \"on\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mStop_dry = (Button) view.findViewById(R.id.stop_dry);
        mStop_dry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/dry/heat\", \"parameter\": \"off\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        mSetting_tem = (Button) view.findViewById(R.id.setting_tem);
        mSetting_tem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/display\", \"parameter\": \"set\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });
        mIndoor_tem = (Button) view.findViewById(R.id.indoor_tem);
        mIndoor_tem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = prefs.getString(HttpUtil.KEY_TOKEN, null);
                if (token != null) {
                    RequestBody requestBody = RequestBody.create(HttpUtil.JSON,
                            "{\"topic\": \"air condition/display\", \"parameter\": \"indoor\"}");
                    Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
                    TotalMethod(token, request);
                }
            }
        });

        return view;
    }

    private void TotalMethod(String token, Request request) {
        final String basic = Credentials.basic(token, "");
        OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                return response.request().newBuilder().header("Authorization", basic).build();
            }
        }).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "发送消息失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse:  Success");
            }
        });
    }

    private void getStatusMethod(String token, final Request request) {
        final String basic = Credentials.basic(token, "");
        OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                return response.request().newBuilder().header("Authorization", basic).build();
            }
        }).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "发送消息失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    airCleanStatus = jsonObject.getString("air cleaner");
                    airConditionStatus = jsonObject.getString("air condition/switch");
                    airCleanStatus = convert(airCleanStatus);
                    airConditionStatus = convert(airConditionStatus);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAirPurifierStatus.setText(airCleanStatus);
                            mAirConditionerStatus.setText(airConditionStatus);
                            mRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
