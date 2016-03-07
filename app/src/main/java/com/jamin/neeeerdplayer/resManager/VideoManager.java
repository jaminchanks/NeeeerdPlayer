package com.jamin.neeeerdplayer.resManager;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.jamin.neeeerdplayer.bean.FooVideo;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-5.
 */
public class VideoManager {

    private Context mContext;

    public VideoManager(Context context) {
        mContext = context;
    }



    public ArrayList<FooVideo> queryAllVideos() {
        ArrayList<FooVideo> videos = null;

        //MediaStore.Video.Thumbnails.DATA：视频缩略图的文件路径
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID};
        //MediaStore.Video.Media.DATA:视频文件路径
        //MediaStore.Video.Media.DISPLAY_NAME: 视频的文件名
        //MediaStore.Video.Media.TITLE：视频标题
        //MediaStore.Video.Media.DURATION:视频时长
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION};

        Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);
        if (null != cursor) {
            videos = new ArrayList<>();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
//                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                String artist = "123";
                String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
//                String thumbnail = queryVideoThumbnail(id);
                //尚未设置视频的最后播放时间
                FooVideo fooVideo = new FooVideo(id, artist, displayName, videoPath, null, size, duration, 0);

                if (fooVideo.getDuration() > 1000)
                    videos.add(fooVideo);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return videos;
    }


    /**
     * 查找视频的缩略图
     */
    public String queryVideoThumbnail(int id) {
        String thumbnail = null;
        String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Thumbnails.DATA}, selection, new String[]{String.valueOf(id)}, null);

        if (null != cursor&& !cursor.isAfterLast()) {
            cursor.moveToFirst();
            thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
            cursor.close();
        }
        return thumbnail;
    }



}
