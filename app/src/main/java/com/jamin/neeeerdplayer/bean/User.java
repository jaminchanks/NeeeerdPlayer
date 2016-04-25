package com.jamin.neeeerdplayer.bean;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by jamin on 16-4-4.
 */
//@Table(name = "user")
public class User implements Serializable{
//    @Column(name = "id", isId = true)
//    private int id;
//    @Column(name = "account")
//    private String account;
//    @Column(name = "password")
//    private String password;
//    @Column(name = "userName")
//    private String userName;
//    @Column(name = "birthday")
//    private String birthday;
//    @Column(name = "email")
//    private String email;
//    @Column(name = "marks")
//    private String marks;
//    @Column(name = "identity")
//    private String identity;
//    @Column(name = "avatar")
//    private String head;
//    @Column(name = "isBaned")
//    private boolean isBaned;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getAccount() {
//        return account;
//    }
//
//    public void setAccount(String account) {
//        this.account = account;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getBirthday() {
//        return birthday;
//    }
//
//
//    /**
//     * 设置出生日期
//     * @param birthday
//     */
//    public void setBirthday(String birthday) {
////        Calendar c = Calendar.getInstance();//获得一个日历的实例
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        Date date = null;
////        try{
////            date = sdf.parse(birthday);//初始日期
////        }catch(Exception e){
////            e.printStackTrace();
////        }
////        c.setTime(date);//设置日历时间
//
//        this.birthday = birthday;
//    }
//
//    public void setBirthday(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        this.birthday = sdf.format(date);
//    }
//
//
//    /**
//     * 获取时间的字符串格式
//     * @return
//     */
//    public String getBirthdayStr() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(birthday);
//    }
//
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getMarks() {
//        return marks;
//    }
//
//    public void setMarks(String marks) {
//        this.marks = marks;
//    }
//
//    public String getIdentity() {
//        return identity;
//    }
//
//    public void setIdentity(String identity) {
//        this.identity = identity;
//    }
//
//    public String getHead() {
//        return head;
//    }
//
//    public void setHead(String head) {
//        this.head = head;
//    }
//
//    public boolean isBaned() {
//        return isBaned;
//    }
//
//    public void setBaned(boolean baned) {
//        isBaned = baned;
//    }
//
//
//
//    public User getUserBean (DbManager db) throws DbException {
//        return db.findById(User.class, id);
//    }
//
//
//    @Override
//    public String toString() {
//        return "user{ " +
//                "id=" + id + "," +
//                "account=\'" + account + "\'," +
//                "userName=\'" + userName + "\'," +
//                "head=\'" + head + "\'" +
//                "birthday=\'" + birthday + "\'," +
//                "marks=\'" + marks + "\'," +
//                "email=\'" + email + "\'," +
//                "}";
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIsBaned() {
        return isBaned;
    }

    public void setIsBaned(int isBaned) {
        this.isBaned = isBaned;
    }

    private int id;
    private String account;
    private String password;
    private String userName;
    private String birthday;
    private String email;
    private String marks;
    private int identity;
    private String avatar;
    private int isBaned;


}
