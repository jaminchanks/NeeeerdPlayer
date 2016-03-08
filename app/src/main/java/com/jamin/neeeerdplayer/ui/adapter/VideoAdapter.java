package com.jamin.neeeerdplayer.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.utils.FooUtils;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-5.
 */
public class VideoAdapter extends ArrayAdapter<FooVideo>{
    private Activity mActivity;
    private ArrayList<FooVideo> mVideoList;

    public VideoAdapter(Activity activity, ArrayList<FooVideo> fooVideos) {
        super(activity, 0, fooVideos);
        mActivity = activity;
        mVideoList = fooVideos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.list_item_video, null);
            viewHolder = new MyViewHolder();
            viewHolder.videoThumbnail = (ImageView) convertView.findViewById(R.id.imgv_video_thumbnail);
            viewHolder.durationView = (TextView) convertView.findViewById(R.id.tv_video_duration);
            viewHolder.displayNameView = (TextView) convertView.findViewById(R.id.tv_video_display_name);
            convertView.setTag(viewHolder);
        }
        viewHolder = (MyViewHolder) convertView.getTag();
        FooVideo fooVideo = getItem(position);
        //从视频中提取相关显示信息
        Bitmap thumbnail = BitmapFactory.decodeFile(fooVideo.getThumbnailPath());
        String displayName = fooVideo.getDisplayName();
        // TODO: 16-3-5 这里暂不处理图像的大小
            String data = fooVideo.getVideoPath();
            viewHolder.videoThumbnail.setImageBitmap(fooVideo.getThumbnail());
//            viewHolder.videoThumbnail.setImageBitmap(thumbnail);

        viewHolder.displayNameView.setText(displayName);
        viewHolder.durationView.setText(FooUtils.timeFormat(fooVideo.getDuration()));
        return convertView;
    }


    @Override
    public FooVideo getItem(int position) {
        return mVideoList.get(position);
    }


    class MyViewHolder {
        ImageView videoThumbnail;
        TextView durationView;
        TextView displayNameView;
    }
}
