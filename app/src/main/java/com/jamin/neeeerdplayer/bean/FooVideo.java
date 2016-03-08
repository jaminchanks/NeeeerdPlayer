package com.jamin.neeeerdplayer.bean;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by jamin on 16-3-5.
 */
public class FooVideo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String artist;
    private String displayName;
    private String videoPath;
    private String thumbnailPath;   //缩略图的路径
    private long size;
    private long duration;
    private long latestPlayTime;    //最后的播放时间

    public FooVideo() {

    }

    public FooVideo(int id, String artist, String displayName, String videoPath, String thumbnailPath,
                    long size, long duration, long latestPlayTime) {
        setId(id);
        setArtist(artist);
        setDisplayName(displayName);
        setVideoPath(videoPath);
        setThumbnailPath(thumbnailPath);
        setSize(size);
        setDuration(duration);
        setLatestPlayTime(latestPlayTime);
    }


    /**
     * 获取视频缩略图图片
     * @return
     */
    public Bitmap getThumbnail() {
        return ThumbnailUtils.createVideoThumbnail(getVideoPath(),
                MediaStore.Images.Thumbnails.MINI_KIND);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLatestPlayTime() {
        return latestPlayTime;
    }

    public void setLatestPlayTime(long latestPlayTime) {
        this.latestPlayTime = latestPlayTime;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }



}
