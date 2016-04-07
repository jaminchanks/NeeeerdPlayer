package com.jamin.neeeerdplayer.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.database.UserDao;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.NetConfig;
import com.jamin.neeeerdplayer.ui.user.UserLoginActivity;

import org.xutils.x;

import java.util.Objects;

/**
 * Created by jamin on 16-3-24.
 */
public class SplashActivity extends Activity{


    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(x.app());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NetConfig.ACCOUNT_NAME, "");
        //// TODO: 16-4-4  改变user_token的值
        editor.putInt(NetConfig.USER_TOKEN, -1);
        editor.apply();

        getUserInfo();
    }

    private void getUserInfo() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //从这两个值来判断用户是否已经登陆过
        int token = preferences.getInt(NetConfig.USER_TOKEN, -1);
        String account =  preferences.getString(NetConfig.ACCOUNT_NAME, "");
//        Toast.makeText(this, token + "", Toast.LENGTH_SHORT).show();
        if (token != -1 && !Objects.equals("", account)) {
            //把当前的用户信息全部保存到全部Application当中
            UserDao dao = new UserDao();
            User user = dao.queryUserByAccount(account);
            ((BaseApplication)getApplication()).setUser(user);

            toActivity(MainActivity.class);
        } else {
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
