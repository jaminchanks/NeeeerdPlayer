package com.jamin.neeeerdplayer.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.UserRelationship;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.config.PreferensConfig;
import com.jamin.neeeerdplayer.ui.user.login.UserLoginActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jamin on 16-3-24.
 */
public class SplashActivity extends Activity{
    private User user;
    private long beginTime;
    private static final long SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_splash);

        beginTime = System.currentTimeMillis();

        //开启App图标动画效果
        ImageView icon = (ImageView)findViewById(R.id.iv_splash_icon);
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.in_from_bottom_alpha);
        icon.startAnimation(animation);

        getUserInfo();

//        toActivity(UserLoginActivity.class);
    }


    /**
     * 从本地获取用户已登陆信息
     */
    private void getUserInfo() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //从这个值来判断用户是否已经登陆过

        int uid = preferences.getInt(PreferensConfig.UID, -1);

        //用户之前曾登陆过
        if (uid != -1) {
            //从本地文件中读取用户信息
            String userInfo = preferences.getString(PreferensConfig.USER_INFO, "");
            Gson gson = new Gson();
            user = gson.fromJson(userInfo, User.class);

            //把当前的用户信息全部保存到全部Application当中
            ((BaseApplication)getApplication()).setUser(user);
            //获取用户的所有好友信息
            getRelationship();
        } else {
            //没有当前用户登陆信息
            toActivity(UserLoginActivity.class);
        }
    }

    private void toActivity(Class clazz) {
        final Intent intent = new Intent();
        intent.setClass(this, clazz);

        long timDiff = System.currentTimeMillis() - beginTime;
        if (timDiff > SPLASH_TIME) {
            startActivity(intent);
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME - timDiff);
        }
    }


    private void getRelationship() {
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/relationship/user");
        params.setConnectTimeout(4000);
        params.addParameter("userId", user.getId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserRelationship relationship = new Gson().fromJson(result, UserRelationship.class);
                //保存好友列表
                ((BaseApplication) x.app()).setUserRelationship(relationship);
                toActivity(MainActivity.class);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(SplashActivity.this, "请检查您的网络", Toast.LENGTH_SHORT).show();
                toActivity(MainActivity.class);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                toActivity(MainActivity.class);
            }

            @Override
            public void onFinished() {
            }
        });
    }


}
