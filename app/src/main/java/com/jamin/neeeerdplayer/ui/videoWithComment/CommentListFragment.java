package com.jamin.neeeerdplayer.ui.videoWithComment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.CommentWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-4-19.
 */
public class CommentListFragment extends XBaseFragment{
    private static final String TAG = CommentListFragment.class.getSimpleName();
    public static final String REFRESH_COMMENT_DATA = "refresh comments";


    private static final String ONLINE_VIDEO_ID = "online video id";

    private ListView mListView;
    private SwipeRefreshLayout refreshLayout;
    public CommentAdapter mCommentAdapter;
    private List<CommentWithUser> comments = new ArrayList<>();
    private int mVideoId;
    private RefreshBroadCast broadCast;

    public static CommentListFragment newInstance(int videoId) {
        Bundle args = new Bundle();
        args.putInt(ONLINE_VIDEO_ID, videoId);
        CommentListFragment fragment = new CommentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_list, null);
        mListView = (ListView) view.findViewById(R.id.lv_comments);
        LinearLayout emptyView = (LinearLayout) view.findViewById(R.id.empty_view);
        (emptyView.findViewById(R.id.empty_view_icon)).setBackgroundResource(R.mipmap.no_comment_96);
        ((TextView) emptyView.findViewById(R.id.empty_view_title)).setText("就等你来评论啦~");
        mListView.setEmptyView(emptyView);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        setRefreshLayoutSetting();

        return view;
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(REFRESH_COMMENT_DATA);
        broadCast = new RefreshBroadCast();
        getActivity().registerReceiver(broadCast, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadCast);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mListView.setDivider(null);
        mCommentAdapter = new CommentAdapter(getActivity(), comments);
        mListView.setAdapter(mCommentAdapter);
        requestForComment();
    }

    /**
     * 从网络上获取评论内容
     */
    private void requestForComment() {
        mVideoId = getArguments().getInt(ONLINE_VIDEO_ID);

        refreshLayout.setRefreshing(true);

        RequestParams requestParams = new RequestParams(BaseNetConfig.WEB_URL + "/comment/video_id");
        requestParams.addQueryStringParameter("id", String.valueOf(mVideoId));
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new  TypeToken<List<CommentWithUser>>(){}.getType();
                List<CommentWithUser> commentWithUsers1 = new Gson().fromJson(result, type);
                comments.clear();
                comments.addAll(commentWithUsers1);
                mCommentAdapter.notifyDataSetChanged();
                Log.i("获取评论", "成功" + mVideoId);

                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("获取评论", "失败error");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("获取评论", "失败cancel");
            }

            @Override
            public void onFinished() {
                Log.i("获取评论", "失败finish");
            }
        });
    }


    /**
     * 下拉刷新设置
     * */
    private void setRefreshLayoutSetting() {
        //设置刷新时动画的颜色，可以设置4个
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        requestForComment();
                    }
                });
            }
        });
    }

    public class RefreshBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            requestForComment();
        }
    }

}
