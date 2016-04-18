package com.jamin.neeeerdplayer.bean;

/**
 * 简略的用户信息
 * @author jamin
 *
 */
public class UserInfo {
    private int userId;
    private String userAccount;
    private String userAvatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public UserInfo(User user) {
        setUserId(user.getId());
        setUserAccount(user.getAccount());
        setUserAvatar(user.getAvatar());
    }
}
