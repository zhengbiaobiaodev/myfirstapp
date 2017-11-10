package com.example.myfirstapp.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myfirstapp.MainActivity;
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
import okhttp3.Response;
import okhttp3.Route;

import static com.example.myfirstapp.util.HttpUtil.GET_TOKEN;
import static com.example.myfirstapp.util.HttpUtil.TOKEN_PREFS;

/**
 * Created by Admin on 2017/11/5.
 */

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private View mView;

    private static final String KEY_USER = "user";
    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";
    private static boolean status = false;

    private LinearLayout mLoginFormView;
    private EditText mAccount;
    private EditText mPassword;
    private CheckBox mRememberPassword;
    private Button mLoginButton;
    private View mProgressView;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_login, container, false);

        loginForFirst();
        return mView;
    }

    private void loginForFirst() {
        mLoginFormView = (LinearLayout) mView.findViewById(R.id.login_form);
        mProgressView = mView.findViewById(R.id.login_progress);
        mAccount = (EditText) mView.findViewById(R.id.account);
        mPassword = (EditText) mView.findViewById(R.id.password);
        mRememberPassword = (CheckBox) mView.findViewById(R.id.remember_password);
        mLoginButton = (Button) mView.findViewById(R.id.login);
        mPrefs = getActivity().getSharedPreferences(KEY_USER, 0);

        boolean isRemember = mPrefs.getBoolean(KEY_REMEMBER, false);
        if (isRemember) {
            String account = mPrefs.getString(KEY_ACCOUNT, "");
            String password = mPrefs.getString(KEY_PASSWORD, "");
            mAccount.setText(account);
            mPassword.setText(password);
            mRememberPassword.setChecked(true);
            getTokenByOkHttp(account, password, getActivity());
            showProgress(true);
        } else {
            String account = mPrefs.getString(KEY_ACCOUNT, "");
            mAccount.setText(account);
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                mEditor = mPrefs.edit();
                mEditor.putString(KEY_ACCOUNT, mAccount.getText().toString());
                mEditor.apply();
                getTokenByOkHttp(account, password, getActivity());
                showProgress(true);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    //获取token用的method
    public void getTokenByOkHttp(String account, String password, final Context context) {

        final SharedPreferences prefs = context.getSharedPreferences(TOKEN_PREFS, 0);
        final SharedPreferences.Editor editor = prefs.edit();

        //进行http认证
        final String basic = Credentials.basic(account, password);
        OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                return response.request().newBuilder().header("Authorization", basic).build();
            }
        }).build();

        final Request request = new Request.Builder().get().url(GET_TOKEN).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String account = mPrefs.getString(KEY_ACCOUNT, "");
                        mAccount.setText(account);
                        mPassword.setText("");
                        showProgress(false);
                        Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        //访问成功后存储token
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int expiration = jsonObject.getInt("expiration");
                        String token_inner = jsonObject.getString("token");
                        editor.putInt(HttpUtil.KEY_EXPIRATION, expiration);
                        editor.putString(HttpUtil.KEY_TOKEN, token_inner);
//                        Log.e(TAG, "onResponse: " + token_inner);
                        editor.apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //登陆成功后将账号密码保存在文件中
                    mEditor = mPrefs.edit();
                    if (mRememberPassword.isChecked()) {
                        mEditor.putBoolean(KEY_REMEMBER, true);
                        mEditor.putString(KEY_ACCOUNT, mAccount.getText().toString());
                        mEditor.putString(KEY_PASSWORD, mPassword.getText().toString());
                        mEditor.apply();
                    } else {
                        mEditor.putString(KEY_ACCOUNT, mAccount.getText().toString());
                        mEditor.apply();
                    }

                    //登录成功后的跳转
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showProgress(false);
                            ((MainActivity)getActivity()).replaceFragment(new DataShowFragment());
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String account = mPrefs.getString(KEY_ACCOUNT, "");
                            mAccount.setText(account);
                            mPassword.setText("");
                            showProgress(false);
                            Toast.makeText(context, "认证失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
