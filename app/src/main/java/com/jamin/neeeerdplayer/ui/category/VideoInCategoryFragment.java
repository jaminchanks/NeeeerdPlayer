package com.jamin.neeeerdplayer.ui.category;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.Category;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-5-2.
 */
public class VideoInCategoryFragment extends XBaseFragment{
    private static final String CATEGORY_ID = "category id";


    private int mCategoryId;

    private Activity mActivity;
    private List<VideoWithUser> mVideos = new ArrayList<>();

    private RecyclerView mRecycleView;
    private VideoInCategoryAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;

    public static VideoInCategoryFragment getInstance(int categoryId) {
        Bundle bundle = new Bundle();
        bundle.putInt(CATEGORY_ID, categoryId);
        VideoInCategoryFragment fragment = new VideoInCategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

        mCategoryId = getArguments().getInt(CATEGORY_ID);
        Category category = Category.findCategoryByIndex(mCategoryId);
        mActivity.setTitle(category.toString(mActivity));

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_in_category, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ly_refresh_category);
        setRefreshLayoutSetting();

        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_category_video);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new VideoInCategoryAdapter(mActivity, mVideos);
        mRecycleView.setLayoutManager(new GridLayoutManager(mActivity, 2));

        mRecycleView.setAdapter(mAdapter);

        requestVideoByCategory();
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
                requestVideoByCategory();
            }
        });
    }

    private void requestVideoByCategory() {
        refreshLayout.setRefreshing(true);

        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/video/category");
        params.addParameter("categoryId", mCategoryId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<VideoWithUser>>(){}.getType();
                List<VideoWithUser> videos = new Gson().fromJson(result, type);
                //刷新数据
                mAdapter.refreshData(videos);

                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) { }

            @Override
            public void onFinished() { }
        });
    }

}
