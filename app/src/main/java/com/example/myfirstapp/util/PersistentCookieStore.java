package com.example.myfirstapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;

/**
 * Created by Admin on 2017/11/3.
 */

public class PersistentCookieStore {

    private static final String LOG_TAG = "PersistentCookieStore";
    private static final String COOKIE_PREFS = "Cookies_Prefs";

    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        cookies = new HashMap<>();

        //将持久化的cookies缓存到内存中 即map cookies
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookiesNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookiesNames) {
                String encodedCookies = cookiePrefs.getString(name, null);
                if (encodedCookies != null) {
                    Cookie decodedCookies = decodeCookie(encodedCookies);
                    if (decodedCookies != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookies);
                    }
                }
            }
        }
    }

    private Cookie decodeCookie(String encodedCookies) {
        return null;
    }
}
