package com.jamin.neeeerdplayer.ui.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.ui.videoWithComment.VideoWithCommentActivity;

import java.util.List;

/**
 * Created by jamin on 16-5-2.
 */
public class VideoInCategoryAdapter extends RecyclerView.Adapter<VideoInCategoryAdapter.MyViewHolder> {

    private List<VideoWithUser> mVideos;
    private Context mContext;

    public VideoInCategoryAdapter(Context context, List<VideoWithUser> videos) {
        this.mContext = context;
        this.mVideos = videos;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_video_online, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final VideoWithUser video = mVideos.get(position);
        Glide.with(mContext).load(video.getVideo().getVideo_wrap()).into(holder.thumbnail);
        holder.title.setText(video.getVideo().getVideo_name());
        holder.playTimes.setText(String.valueOf(video.getVideo().getWatch_numbers()));
        holder.commentTimes.setText(String.valueOf(video.getVideo().getLike_numbers()));
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(VideoWithCommentActivity.VIDEO_WITH_COMMENT_SELECTED, video);
                intent.setClass(mContext, VideoWithCommentActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public void refreshData(List<VideoWithUser> videos) {
        this.mVideos = videos;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView thumbnail;
        TextView title;
        TextView playTimes;
        TextView commentTimes;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.cardView = (CardView) itemView.findViewById(R.id.cv_video_online);
            this.thumbnail = (ImageView) itemView.findViewById(R.id.iv_video_online_thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.tv_video_online_title);
            this.playTimes = (TextView) itemView.findViewById(R.id.tv_video_online_play_times);
            this.commentTimes = (TextView)itemView.findViewById(R.id.tv_tv_video_online_comment_times);
        }

        public View getItemView() {
            return view;
        }
    }


}
