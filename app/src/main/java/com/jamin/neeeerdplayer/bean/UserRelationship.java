package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 用户关系表
 *
 * Created by jamin on 16-5-2.
 */
public class UserRelationship implements Serializable {
    List<UserInfo> followUsers;	//关注
    List<UserInfo> beFollowedUsers;	//被关注

    public List<UserInfo> getFollowUsers() {
        return followUsers;
    }

    public void setFollowUsers(List<UserInfo> followUsers) {
        this.followUsers = followUsers;
    }

    public List<UserInfo> getBeFollowedUsers() {
        return beFollowedUsers;
    }

    public void setBeFollowedUsers(List<UserInfo> beFollowedUsers) {
        this.beFollowedUsers = beFollowedUsers;
    }


}
