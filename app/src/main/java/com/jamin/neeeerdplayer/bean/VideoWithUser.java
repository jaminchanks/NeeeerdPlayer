package com.jamin.neeeerdplayer.bean;


import java.io.Serializable;

/**
 * 用于一对一查询
 * @author jamin
 *
 */
public class VideoWithUser implements Serializable{
    public OnlineVideo getVideo() {
        return video;
    }

    public void setVideo(OnlineVideo video) {
        this.video = video;
    }

    public UserInfo getUserInfo() {
        return user;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.user = userInfo;
    }

    OnlineVideo video;
    UserInfo user;

    public VideoWithUser(OnlineVideo video, User user1) {
        this.video = video;
        this.user = new UserInfo(user1);
    }
}