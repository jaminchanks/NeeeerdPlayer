package com.jamin.neeeerdplayer.database;

import android.content.Context;

import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.model.VideoManager;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-5.
 */
public class VideoLab {

    private ArrayList<FooVideo> mFooVideos;
    private static VideoLab mVideoLab;
    private Context mAppContext;

    private VideoLab(Context context) {
        mAppContext = context;
//        mFooVideos = new ArrayList<>();
    }

    public static VideoLab getInstance(Context context) {
        if (null == mVideoLab) {
            synchronized (VideoLab.class) {
                if (null == mVideoLab) {
                    mVideoLab = new VideoLab(context);
                }
            }
        }
        return mVideoLab;
    }

    public ArrayList<FooVideo> getAllVideos() {
        //todo 在这里从数据库中获取所有视频信息，确定可将所有视频类放在内存中？
        return new VideoManager(mAppContext).queryAllVideos();
    }


    public FooVideo getVideo(int id) {
        //todo 这里改成从数据库中查询指定id的视频？ //暂时先这么做
        for (FooVideo fooVideo : mFooVideos) {
            if (id == fooVideo.getId()) {
                return fooVideo;
            }
        }
        return null;
    }


}
