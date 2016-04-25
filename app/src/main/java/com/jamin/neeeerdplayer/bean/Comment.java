package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;

/**
 * Created by jamin on 16-4-18.
 */
public class Comment implements Serializable {
    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComment_to() {
        return comment_to;
    }

    public void setComment_to(int comment_to) {
        this.comment_to = comment_to;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getIsBaned() {
        return isBaned;
    }

    public void setIsBaned(int isBaned) {
        this.isBaned = isBaned;
    }

    private int comment_id;
    private int user_id;
    private String content;
    private int comment_to;
    private int video_id;
    private String comment_time;
    private int isBaned;

}
