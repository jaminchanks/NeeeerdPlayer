package com.jamin.neeeerdplayer.ui.mainPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-12.
 */
public class OnlineVideoGridAdapter extends BaseAdapter {
    private ArrayList<FooVideo> mVideos;
    private Context mContext;
//    private int mViewWidth;
    public OnlineVideoGridAdapter(Context context, ArrayList<FooVideo> fooVideos) {
        this.mVideos = fooVideos;
        this.mContext = context;

//        int videoViewSpacePx = DensityUtils.dip2px(mContext, 10);
//
//        int screenWith = ScreenUtils.getScreenWidth(mContext);
//        mViewWidth = (screenWith -
//                (mContext.getResources().getDimensionPixelSize(R.dimen.activity_default_margin)*2)
//                - DensityUtils.dip2px(mContext, videoViewSpacePx))/2;

    }


    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.component_video_online_item, null);
            viewHolder = new MyViewHolder();
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.iv_video_online_thumbnail);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_video_online_title);
            viewHolder.playTimes = (TextView) convertView.findViewById(R.id.tv_video_online_play_times);
            viewHolder.commentTimes = (TextView)convertView.findViewById(R.id.tv_tv_video_online_comment_times);
            convertView.setTag(viewHolder);
        }

        FooVideo video = mVideos.get(position);
        viewHolder = (MyViewHolder) convertView.getTag();

        //调整gridView组件之间的间距
//            if (position % 2 == 0) {
//                convertView.setPadding(0, 0, mViewWidth / 2, 0);
//            } else {
//                convertView.setPadding(mViewWidth / 2, 0, 0, 0);
//            }

        viewHolder.thumbnail.setImageBitmap(video.getThumbnail());
        viewHolder.title.setText(video.getTitle());
        viewHolder.playTimes.setText(String.valueOf(video.getPlayTimes()));
        viewHolder.commentTimes.setText(String.valueOf(video.getCommentTimes()));
        return convertView;
    }

    private class MyViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView playTimes;
        TextView commentTimes;

        public MyViewHolder() {}

    }



}
