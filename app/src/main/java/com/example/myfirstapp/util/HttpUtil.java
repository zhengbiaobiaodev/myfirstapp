package com.example.myfirstapp.util;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Admin on 2017/10/21.
 */

public class HttpUtil {

    public static final String CONTROLLER = "http://120.25.242.228/api/v1.0/use_actuator";  //控制器API接口
    public static final String GET_TOKEN = "http://120.25.242.228/api/v1.0/token";        //获取token的接口

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");     //设置json格式（不是很懂）

    public static final String TOKEN_PREFS = "token_prefs";          //存储token的文件名
    public static final String KEY_TOKEN = "token";  //获取或存储token的键值
    public static final String KEY_EXPIRATION = "expiration";  //获取或存储有效时间的键值

    private static OkHttpClient client = new OkHttpClient.Builder().build();

    //访问天气API用的method
    public static void sendOkHttpRequest(String address, Callback callback) {
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }


//                RequestBody requestBody = RequestBody.create(HttpUtil.JSON, "{\"topic\": \"temperature\", \"parameter\": 30}");
//                Request request = new Request.Builder().url(CONTROLLER).post(requestBody).build();
//    else {
//         final String basic = Credentials.basic(token, "");
//        OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
//            @Nullable
//            @Override
//            public Request authenticate(Route route, Response response) throws IOException {
//                return response.request().newBuilder().header("Authorization", basic).build();
//            }
//        }).build();
//
//        final Request request = new Request.Builder().get().url(CONTROLLER).build();
//    }


}
