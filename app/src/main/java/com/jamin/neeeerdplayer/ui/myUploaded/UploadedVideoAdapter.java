package com.jamin.neeeerdplayer.ui.myUploaded;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
 * Created by jamin on 16-5-1.
 */
public class UploadedVideoAdapter extends RecyclerView.Adapter {
    private List<VideoWithUser> mVideos;
    private Context mContext;

    public UploadedVideoAdapter(Context context, List<VideoWithUser> videos) {
        this.mContext = context;
        this.mVideos = videos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_video_online, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder)holder;
        final VideoWithUser video = mVideos.get(position);
        Glide.with(mContext).load(video.getVideo().getVideo_wrap()).into(viewHolder.thumbnail);
        viewHolder.title.setText(video.getVideo().getVideo_name());
        viewHolder.playTimes.setText(String.valueOf(video.getVideo().getWatch_numbers()));
        viewHolder.commentTimes.setText(String.valueOf(video.getVideo().getLike_numbers()));

        viewHolder.getItemView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showOperateDialog(position);
                return false;
            }
        });

        viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
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

    public void setItems(List<VideoWithUser> videos) {
        this.mVideos = videos;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView playTimes;
        TextView commentTimes;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.thumbnail = (ImageView) itemView.findViewById(R.id.iv_video_online_thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.tv_video_online_title);
            this.playTimes = (TextView) itemView.findViewById(R.id.tv_video_online_play_times);
            this.commentTimes = (TextView)itemView.findViewById(R.id.tv_tv_video_online_comment_times);
        }

        public View getItemView() {
            return view;
        }
    }


    /**弹出选择操作的对话框
     *
     * @param position item对应的位置
     *
     */
    public void showOperateDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //builder.setTitle("请选择操作");
        builder.setItems(new CharSequence[]{ "删除"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:	//删除操作
                                //// TODO: 16-5-2 这里只是在本地删除了视频
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                                dialogBuilder.setTitle("删除");
                                dialogBuilder.setMessage("确定删除这个视频?");
                                dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mVideos.remove(position);
                                        notifyDataSetChanged();
                                    }
                                });
                                dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();

                                break;
                            default:
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
