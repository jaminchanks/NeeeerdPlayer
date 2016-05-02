package com.jamin.neeeerdplayer.ui.mainPage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-3-9.
 */
public class BillboardFragment extends Fragment {

    private Context mContext;
    private ListView mListView;
    private BillBoardAdapter mAdapter;
    private List<VideoWithUser> mVideos = new ArrayList<>();

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_billboard, container, false);
        mListView = (ListView) view.findViewById(R.id.list_view_billboard);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        mAdapter = new BillBoardAdapter(mContext, mVideos);
        mListView.setAdapter(mAdapter);
        requestBillboard();
    }


    private void requestBillboard() {
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/video/billboard");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List<VideoWithUser>>(){}.getType();
                List<VideoWithUser> videos1 = new Gson().fromJson(result, type);
                Log.i("uploaded_video", result);
                mVideos.addAll(videos1);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


}
