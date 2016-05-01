package com.jamin.neeeerdplayer.ui.base;

import android.app.Application;
import android.content.Context;

import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.utils.ImageCacheHelper;

import org.xutils.x;

/**
 * Created by jamin on 16-3-24.
 */
public class BaseApplication extends Application{
    private User user;

    public static Context baseContext;

    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志

        BasePathConfig.getInstance().initAppFilePath(this);
        ImageCacheHelper.initImageLoader(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
