package com.jamin.neeeerdplayer.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.jamin.neeeerdplayer.ui.base.Category;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by jamin on 16-3-5.
 */
public class FooVideo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String artist;
    private String displayName; //这个是文件的名字
    private String videoPath;
    private String thumbnailPath;   //缩略图的路径
    private long size;
    private long duration;
    private long latestPlayTime;    //最后的播放时间
    private long playTime;
    private long commentTime;
    private Category category;
    private String title;   //这个是视频的标题



    /**
     * 获取视频缩略图图片
     * @return
     */
    public Bitmap getThumbnail() {
        return ThumbnailUtils.createVideoThumbnail(getVideoPath(),
                MediaStore.Images.Thumbnails.MINI_KIND);
    }


    //// TODO: 16-3-10 部分函数需要修改
    public long getPlayTime() {
        return 123;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public long getCommentTime() {
        return 456;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    //// TODO: 16-3-11 此方法暂做测试
    public Category getCategory() {
        if (category == null) {
            return Category.COMIC;
        }
        return category;
    }

    public void setCategory(Context context, String category) {
        this.category = Category.parseCategory(context, category);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getShortDisplayName() {
        if (displayName.length() > 15)
            return displayName.substring(0, 15) + "...";
        return displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


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



}
