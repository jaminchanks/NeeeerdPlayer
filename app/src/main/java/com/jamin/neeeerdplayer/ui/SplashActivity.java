package com.jamin.neeeerdplayer.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.user.UserLoginActivity;
import com.jamin.neeeerdplayer.ui.user.UserRegisterActivity;

import java.util.Objects;

/**
 * Created by jamin on 16-3-24.
 */
public class SplashActivity extends Activity{
    private static final String USER_TOKEN = "user_token";

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_splash);

        getUserInfo();
    }

    private void getUserInfo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString(USER_TOKEN, "");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        if (Objects.equals("", token)) {
            Intent intent = new Intent();
            intent.setClass(this, UserLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }



}
