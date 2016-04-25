package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;

/**
 * 简略的用户信息
 * @author jamin
 *
 */
public class UserInfo implements Serializable{
    private int userId;
    private String userName;
    private String userAvatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public UserInfo(User user) {
        setUserId(user.getId());
        setUserName(user.getUserName());
        setUserAvatar(user.getAvatar());
    }
}
