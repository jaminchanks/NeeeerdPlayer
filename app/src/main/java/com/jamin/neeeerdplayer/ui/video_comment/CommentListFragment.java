package com.jamin.neeeerdplayer.ui.video_comment;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.bean.CommentWithUser;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-4-19.
 */
public class CommentListFragment extends ListFragment {
    private static final String TAG = CommentListFragment.class.getSimpleName();

    private static final String ONLINE_VIDEO_ID = "online video id";

    private CommentAdapter mCommentAdapter;
    private List<CommentWithUser> comments = new ArrayList<>();
    private int mVideoId;


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

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setDivider(null);

        mCommentAdapter = new CommentAdapter(getActivity(), comments);
        setListAdapter(mCommentAdapter);
        requestForComment();
    }

    /**
     * 从网络上获取评论内容
     */
    private void requestForComment() {
        mVideoId = getArguments().getInt(ONLINE_VIDEO_ID);

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


}
