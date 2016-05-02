package com.jamin.neeeerdplayer.ui.uploaded;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-5-1.
 */
public class UploadedVideoFragment extends XBaseFragment {
    private User mUser;
    private Activity mActivity;

    private RecyclerView mRecyclerView;
    private GridLayoutManager gm;
    private UploadedVideoAdapter mAdapter;
    private List<VideoWithUser> mVideos = new ArrayList<>();
    private int pageNum;
    private boolean isPageMore = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUser = ((BaseApplication) x.app()).getUser();
        mActivity = getActivity();
        pageNum = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uploaded_video, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_upload_video);

        gm = new GridLayoutManager(mActivity, 2);
        mRecyclerView.setLayoutManager(gm);
        mAdapter = new UploadedVideoAdapter(mActivity, mVideos);
        mRecyclerView.setAdapter(mAdapter);

        //设置recycleView的上拉刷新
        mRecyclerView.setOnScrollListener(new RecycleViewScrollListener());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestMyUploadVideos(pageNum);
    }

    private void requestMyUploadVideos(int pageNum) {
        if (!isPageMore){
            Toast.makeText(mActivity, "没有更多了...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isPageMore && pageNum > 1) {
            Toast.makeText(mActivity, "加载更多中...", Toast.LENGTH_SHORT).show();
        }
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/video/user");
        params.addParameter("userId", mUser.getId());
        params.addParameter("pageNum", pageNum);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("[]")) {
                    isPageMore = false;
                }
                Type type = new TypeToken<List<VideoWithUser>>(){}.getType();
                List<VideoWithUser> videos1 = new Gson().fromJson(result, type);
                Log.i("uploaded_video", result);
                mVideos.addAll(videos1);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) { }

            @Override
            public void onFinished() { }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
    }


    class RecycleViewScrollListener extends RecyclerView.OnScrollListener {
        boolean isSlidingToLast =false;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                int lastVisibleItem = gm.findLastVisibleItemPosition();
                int totalItemCounts = gm.getItemCount();

                if (lastVisibleItem >= (totalItemCounts - 1) && isSlidingToLast) {
                    pageNum += 1;
                    requestMyUploadVideos(pageNum);
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
