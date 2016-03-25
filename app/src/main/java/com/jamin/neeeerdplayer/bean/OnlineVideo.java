package com.jamin.neeeerdplayer.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jamin on 6/16/15.
 */
public class OnlineVideo implements Serializable {
    public static final String TABLE_NAME = "video";
    public static final String COLUMNS[] = {
    };

    private int videoId;
    private int userId;
    private Timestamp uploadTime;
    private String videoSrc;
    private String videoWrapper;
    private String category;
    private String videoName;
    private int likeCounts;
    private int displayCounts;
    private boolean isBaned;


    public boolean isBaned() {
        return isBaned;
    }

    public void setIsBaned(boolean isBaned) {
        this.isBaned = isBaned;
    }


    public int getDisplayCounts() {
        return displayCounts;
    }

    public void setDisplayCounts(int displayCounts) {
        this.displayCounts = displayCounts;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getLikeCounts() {
        return likeCounts;
    }

    public void setLikeCounts(int likeCounts) {
        this.likeCounts = likeCounts;
    }


    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getVideoWrapper() {
        return videoWrapper;
    }

    public void setVideoWrapper(String videoWrapper) {
        this.videoWrapper = videoWrapper;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnlineVideo video = (OnlineVideo) o;

        if (videoId != video.videoId) return false;
        if (likeCounts != video.likeCounts) return false;
        if (uploadTime != null ? !uploadTime.equals(video.uploadTime) : video.uploadTime != null) return false;
        if (videoSrc != null ? !videoSrc.equals(video.videoSrc) : video.videoSrc != null) return false;
        if (videoWrapper != null ? !videoWrapper.equals(video.videoWrapper) : video.videoWrapper != null) return false;
        if (category != null ? !category.equals(video.category) : video.category != null) return false;
        if (videoName != null ? !videoName.equals(video.videoName) : video.videoName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = videoId;
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        result = 31 * result + likeCounts;
        result = 31 * result + (videoSrc != null ? videoSrc.hashCode() : 0);
        result = 31 * result + (videoWrapper != null ? videoWrapper.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (videoName != null ? videoName.hashCode() : 0);
        return result;
    }
}
