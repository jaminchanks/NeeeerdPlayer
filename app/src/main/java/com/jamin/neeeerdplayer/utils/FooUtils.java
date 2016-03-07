package com.jamin.neeeerdplayer.utils;

import com.jamin.neeeerdplayer.bean.FooFolder;
import com.jamin.neeeerdplayer.bean.FooVideo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by jamin on 16-3-7.
 */
public class FooUtils {
    public static ArrayList<FooFolder> videoCategoryByFolder(ArrayList<FooVideo> videos) {
        ArrayList<FooFolder> fooFolderList = new ArrayList<>();
        for (int i = 0; i < videos.size(); i++) {
            //初始化并放入第一个视频
            if (i == 0) {
                FooFolder folder = new FooFolder(new ArrayList<FooVideo>());
                folder.getVideos().add(videos.get(0));
                fooFolderList.add(folder);
                continue;
            }

            //逐一比较视频上一级文件夹名字
            String upperDirector = getUpperDirectorName(videos.get(i).getVideoPath());
            for (int j = 0; j < fooFolderList.size(); j++) {   //若已存在fooFolder，直接放入
                FooFolder existedFolder = fooFolderList.get(j);
                if (existedFolder.getFolderName().equals(upperDirector)) {
                    existedFolder.getVideos().add(videos.get(i));
                    break;
                } else {    //否则新建一个foofolder
                    FooFolder newFolder = new FooFolder(new ArrayList<FooVideo>());
                    newFolder.getVideos().add(videos.get(i));
                    fooFolderList.add(newFolder);
                    break;
                }
            }

        }
        return fooFolderList;
    }


    public static String getUpperDirectorName(String path) {
        return path.substring(0, path.lastIndexOf(File.separator) - 1);
    }

}
