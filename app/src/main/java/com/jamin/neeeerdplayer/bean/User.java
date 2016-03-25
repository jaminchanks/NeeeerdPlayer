package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by jamin on 6/8/15.
 */
public class User implements Serializable {
    public static final String TABLE_NAME = "user";
    public static final String COLUMNS[] = {
            "id", "account", "password", "userName", "birthday",
            "email", "marks", "identity", "head", "isBaned", "sex"
    };

    public static final String _ID = "id";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "userName";
    public static final String BIRTHDAY = "birthday";
    public static final String EMAIL = "birthday";
    public static final String MARKS = "marks";
    public static final String IDENTITY = "identity";
    public static final String HEAD = "head";
    public static final String ISBANED = "isBaned";
    public static final String SEX = "sex";


    private int id;
    private String account;
    private String password;
    private String userName;
    private Date birthday;
    private String email;
    private String marks;
    private Integer identity = 0;
    private String head = "head.jpg";
    private boolean isBaned;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public boolean getIsBaned() {
        return isBaned;
    }

    public void setIsBaned(boolean isBanded) {
        this.isBaned = isBanded;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(birthday);
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

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (account != null ? !account.equals(user.account) : user.account != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (marks != null ? !marks.equals(user.marks) : user.marks != null) return false;
        if (identity != null ? !identity.equals(user.identity) : user.identity != null) return false;
        if (head != null ? !head.equals(user.head) : user.head != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (marks != null ? marks.hashCode() : 0);
        result = 31 * result + (identity != null ? identity.hashCode() : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        return result;
    }
}
