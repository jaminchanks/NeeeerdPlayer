package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;

/**
 * Created by jamin on 16-4-18.
 */
public class CommentWithUser implements Serializable {
    private UserInfo commentFrom;

    public UserInfo getCommentFrom() {
        return commentFrom;
    }

    public void setCommentFrom(UserInfo commentFrom) {
        this.commentFrom = commentFrom;
    }

    public UserInfo getGetCommentTo() {
        return getCommentTo;
    }

    public void setGetCommentTo(UserInfo getCommentTo) {
        this.getCommentTo = getCommentTo;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    private UserInfo getCommentTo;
    private Comment comment;

}
