package com.jamin.neeeerdplayer.ui.mainPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;

import java.util.List;

/**
 * Created by jamin on 16-3-12.
 */
public class OnlineVideoGridAdapter extends BaseAdapter {
    private List<VideoWithUser> mVideos;
    private Context mContext;
//    private int mViewWidth;
    public OnlineVideoGridAdapter(Context context, List<VideoWithUser> fooVideos) {
        this.mVideos = fooVideos;
        this.mContext = context;

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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_video_online, null);
            viewHolder = new MyViewHolder();
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.iv_video_online_thumbnail);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_video_online_title);
            viewHolder.playTimes = (TextView) convertView.findViewById(R.id.tv_video_online_play_times);
            viewHolder.commentTimes = (TextView)convertView.findViewById(R.id.tv_tv_video_online_comment_times);
            convertView.setTag(viewHolder);
        }

        VideoWithUser video = mVideos.get(position);
        viewHolder = (MyViewHolder) convertView.getTag();

        Glide.with(mContext).load(video.getVideo().getVideo_wrap()).into(viewHolder.thumbnail);
        viewHolder.title.setText(video.getVideo().getVideo_name());
        viewHolder.playTimes.setText(String.valueOf(video.getVideo().getWatch_numbers()));
        viewHolder.commentTimes.setText(String.valueOf(video.getVideo().getLike_numbers()));
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
