package com.jamin.neeeerdplayer.database;

import android.widget.ListView;

import com.jamin.neeeerdplayer.bean.User;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by jamin on 16-4-4.
 */
public class UserDao {
    public void save(User user) {
        try {
            x.getDb(DBConfig.getDaoConfig()).save(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<User> queryAll() throws DbException {
            return x.getDb(DBConfig.getDaoConfig()).findAll(User.class);
    }


    public User queryUserByAccount(String account) {
        DbManager dbManager =  x.getDb(DBConfig.getDaoConfig());
        User user = null;
        try {
            user = dbManager.selector(User.class).where("account", "=", account).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return user;
    }
}
