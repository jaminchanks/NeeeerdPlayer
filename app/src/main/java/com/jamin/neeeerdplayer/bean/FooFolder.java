package com.jamin.neeeerdplayer.bean;

import com.jamin.neeeerdplayer.utils.FooUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jamin on 16-3-6.
 */
public class FooFolder implements Serializable {

    private String folderName;
    private int fileCount;
    private ArrayList<FooVideo> videos;

    public FooFolder(ArrayList<FooVideo> videos) {
        this.videos = videos;
    }


    //根据视频文件解析其所在文件夹路径
    public String getFolderSimpleName() {
       return FooUtils.getSimperUpperDirectorName(videos.get(0).getVideoPath());
    }

    public String getFolderName() {
        String path = videos.get(0).getVideoPath();
        return FooUtils.getUpperDirectorName(path);
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFileCount() {
        return videos.size();
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public ArrayList<FooVideo> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<FooVideo> videos) {
        this.videos = videos;
    }


}
