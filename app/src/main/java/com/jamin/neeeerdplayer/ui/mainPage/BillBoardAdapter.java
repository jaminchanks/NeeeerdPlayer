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
 * Created by jamin on 16-3-21.
 */
public class BillBoardAdapter extends BaseAdapter {
    private List<VideoWithUser> mVideos;
    private Context mContext;

    public BillBoardAdapter(Context context, List<VideoWithUser> videos) {
        this.mContext = context;
        this.mVideos = videos;
    }


    @Override
    public int getCount() {
        return mVideos.size();
    }

    @Override
    public VideoWithUser getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_billboard, parent, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder = (MyViewHolder) convertView.getTag();
        VideoWithUser video = mVideos.get(position);
        viewHolder.num.setText(position + 1 + ".");
        Glide.with(mContext).load(video.getVideo().getVideo_wrap()).into(viewHolder.videoThumbnail);
        viewHolder.videoName.setText(video.getVideo().getVideo_name());
        viewHolder.videoUser.setText("By " + video.getUserInfo().getUserName());
        return convertView;
    }




    class MyViewHolder {
        TextView num;
        ImageView videoThumbnail;
        TextView videoName;
        TextView videoUser;

        public MyViewHolder(View view) {
            num = (TextView) view.findViewById(R.id.tv_billboard_number);
            videoThumbnail = (ImageView) view.findViewById(R.id.iv_billboard_video_thumbnail);
            videoName = (TextView) view.findViewById(R.id.tv_billboard_video_name);
            videoUser = (TextView) view.findViewById(R.id.tv_billboard_video_user);
        }
    }
}
