package com.jamin.neeeerdplayer.ui.mainPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.videoWithComment.VideoWithCommentActivity;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-3-9.
 */
public class DynamicFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private List<VideoWithUser> mVideos;
    private MyAdapter myAdapter;
    private LinearLayoutManager mLayoutManager;

    private Activity mActivity;
    private User mUser;

    private int pageNum = 1;

    private boolean mIsVideoMore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mUser = ((BaseApplication) x.app()).getUser();
        mVideos = new ArrayList<>();
        myAdapter = new MyAdapter();
        mIsVideoMore = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic);
        mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ly_refresh_dynamic);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(myAdapter);
        setRefreshLayoutSetting();
        requestFriendVideos(pageNum);
    }

    /**
     * 下拉刷新设置
     * */
    private void setRefreshLayoutSetting() {
        //设置刷新时动画的颜色，可以设置4个
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mIsVideoMore = true;
                mVideos.clear();
                requestFriendVideos(pageNum);
            }
        });
        //设置recycleView的上拉刷新
        mRecyclerView.setOnScrollListener(new RecycleViewScrollListener());
    }


    private void requestFriendVideos(int pageNum) {
        if (!mIsVideoMore){
            Toast.makeText(mActivity, "没有更多了...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mIsVideoMore && pageNum > 1) {
            Toast.makeText(mActivity, "加载更多中...", Toast.LENGTH_SHORT).show();
        }

        mRefreshLayout.setRefreshing(true);
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/video/friends");
        params.addParameter("userId", mUser.getId());
        params.addParameter("pageNum", pageNum);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("[]") ) {
                    mIsVideoMore = false;
                }

                Type type = new TypeToken<List<VideoWithUser>>(){}.getType();
                List<VideoWithUser> videos1 = new Gson().fromJson(result, type);

                Log.i("friend_video", result);
                mVideos.addAll(videos1);
                myAdapter.notifyDataSetChanged();

                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) { }

            @Override
            public void onFinished() {
            }
        });
    }




    /**
     * 适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_dynamic, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final VideoWithUser video = mVideos.get(position);
            Glide.with(mActivity).load(video.getUserInfo().getUserAvatar()).transform(new GlideCircleTransform(mActivity))
                    .into(holder.avatar);
            holder.userName.setText(video.getUserInfo().getUserName() + "上传了视频:");
            Glide.with(mActivity).load(video.getVideo().getVideo_wrap()).into(holder.thumbnail);
            holder.videoName.setText(video.getVideo().getVideo_name());
            holder.upTime.setText(video.getVideo().getUp_time());
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(VideoWithCommentActivity.VIDEO_WITH_COMMENT_SELECTED, video);
                    intent.setClass(mActivity, VideoWithCommentActivity.class);
                    mActivity.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mVideos.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView userName;
        ImageView thumbnail;
        TextView videoName;
        TextView upTime;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            avatar = (ImageView) itemView.findViewById(R.id.iv_avatar_dynamic);
            userName = (TextView) itemView.findViewById(R.id.tv_user_name_dynamic);
            thumbnail = (ImageView) itemView.findViewById(R.id.iv_video_thumbnail_dynamic);
            videoName = (TextView) itemView.findViewById(R.id.tv_video_name_dynamic);
            upTime = (TextView) itemView.findViewById(R.id.tv_up_time_dynamic);
        }

        public View getItemView() {
            return view;
        }


    }


    /**
     * recycleView滑动监听器
     */
    class RecycleViewScrollListener extends RecyclerView.OnScrollListener {
        boolean isSlidingToLast =false;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCounts = mLayoutManager.getItemCount();

                if (lastVisibleItem >= (totalItemCounts - 1) && isSlidingToLast) {
                    pageNum += 1;
                    requestFriendVideos(pageNum);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                isSlidingToLast = true;
            } else {
                isSlidingToLast = false;
            }
        }
    }


}
