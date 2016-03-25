package com.jamin.neeeerdplayer.bean;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jamin on 6/22/15.
 */
public class OnlineComment implements Serializable {

    private int commentId;
    private int userId;
    private String content;
    private Integer commentTo;
    private Timestamp commentTime;
    private int videoId;
    private boolean isBaned;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public boolean getIsBaned() {
        return isBaned;
    }

    public void setIsBaned(boolean isBaned) {
        this.isBaned = isBaned;
    }


    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentTo() {
        return commentTo;
    }

    public void setCommentTo(Integer commentTo) {
        this.commentTo = commentTo;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnlineComment comment = (OnlineComment) o;

        if (commentId != comment.commentId) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (commentTo != null ? !commentTo.equals(comment.commentTo) : comment.commentTo != null) return false;
        if (commentTime != null ? !commentTime.equals(comment.commentTime) : comment.commentTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (commentTo != null ? commentTo.hashCode() : 0);
        result = 31 * result + (commentTime != null ? commentTime.hashCode() : 0);
        return result;
    }
}
