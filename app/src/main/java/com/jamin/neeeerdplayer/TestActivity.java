package com.jamin.neeeerdplayer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.database.UserDao;

import org.xutils.ex.DbException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jamin on 16-4-4.
 */
public class TestActivity extends Activity {
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        User user = new User();
        user.setAccount("xxx");
        user.setMarks("xxx");
        user.setBaned(false);

        user.setBirthday("1992-12-8");
        user.setEmail("xxx");
        user.setHead("xxx");
        user.setPassword("123");
        user.setSex("xx");
        user.setIdentity("12");
        UserDao userDao = new UserDao();
        userDao.save(user);
        try {
            Log.e("xxx", userDao.queryAll().toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
