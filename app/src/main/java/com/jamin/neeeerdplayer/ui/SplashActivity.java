package com.jamin.neeeerdplayer.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.config.NetConfig;
import com.jamin.neeeerdplayer.ui.user.login.UserLoginActivity;

/**
 * Created by jamin on 16-3-24.
 */
public class SplashActivity extends Activity{
    User user;

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_splash);

        getUserInfo();

//        toActivity(UserLoginActivity.class);
    }


    public void initAppPath() {

    }


    /**
     * 从本地获取用户已登陆信息
     */
    private void getUserInfo() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //从这个值来判断用户是否已经登陆过

        int uid = preferences.getInt(NetConfig.UID, -1);

        //用户之前曾登陆过
        if (uid != -1) {
            //从本地文件中读取用户信息
            String userInfo = preferences.getString(NetConfig.USER_INFO, "");
            Gson gson = new Gson();
            user = gson.fromJson(userInfo, User.class);

            //把当前的用户信息全部保存到全部Application当中
            ((BaseApplication)getApplication()).setUser(user);

            toActivity(MainActivity.class);
        } else {
            //没有当前用户登陆信息
            toActivity(UserLoginActivity.class);
        }
    }

    private void toActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
        finish();
    }




}
