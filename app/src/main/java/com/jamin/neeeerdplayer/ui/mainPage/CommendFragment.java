package com.jamin.neeeerdplayer.ui.mainPage;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.widget.AutoSlideBoxView;
import com.jamin.neeeerdplayer.ui.widget.NotScrollListView;
import com.jamin.neeeerdplayer.ui.widget.OnlineVideoGridGroup;
import com.jamin.neeeerdplayer.utils.NetUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-3-8.
 */
public class CommendFragment extends Fragment{
    private List<List<VideoWithUser>> videoLists = new ArrayList<>();
    private NotScrollListView noScrollGridView;
    private CommendVideoAdapter commendVideoAdapter;
    private SwipeRefreshLayout refreshLayout;
    private AnimationDrawable animationDrawable;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);

        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getActivity().getResources().getDrawable(R.mipmap.loading_96_01), 300);
        animationDrawable.addFrame(getActivity().getResources().getDrawable(R.mipmap.loading_96_02), 300);
        animationDrawable.addFrame(getActivity().getResources().getDrawable(R.mipmap.loading_96_03), 300);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_commend, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        initImageCycleView(view);
        initOnlineVideos(view);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.ly_refresh_commend);
        setRefreshLayoutSetting();
    }

    //// TODO: 16-3-8 暂做测试 轮播
    private void initImageCycleView(View view) {
        AutoSlideBoxView autoSlideBoxView = (AutoSlideBoxView) view.findViewById(R.id.cycle_view_test);

        List<AutoSlideBoxView.ImageInfo> list=new ArrayList<AutoSlideBoxView.ImageInfo>();
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image1, "秋色", ""));
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image2, "落叶", ""));
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image3, "初音演唱会", ""));
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image4, "黄昏", ""));

        autoSlideBoxView.loadData(list, new AutoSlideBoxView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(AutoSlideBoxView.ImageInfo imageInfo) {
                //本地图片
                ImageView imageView=new ImageView(getActivity());
                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
                return imageView;
            }
        });

        autoSlideBoxView.setCycleDelayed(5000);

        noScrollGridView = (NotScrollListView) view.findViewById(R.id.lv_commend_video);
        LinearLayout emptyView = (LinearLayout) view.findViewById(R.id.empty_view);

        if (NetUtils.isNetworkAvailable(getActivity())) {
            ImageView emptyViewIcon = (ImageView) emptyView.findViewById(R.id.empty_view_icon);
            emptyViewIcon.setBackground(animationDrawable);
            animationDrawable.setOneShot(false);
            animationDrawable.start();
            ((TextView) emptyView.findViewById(R.id.empty_view_title)).setText("正在加载中...");
        } else  {
            (emptyView.findViewById(R.id.empty_view_icon)).setBackgroundResource(R.mipmap.no_wifi_96);
            ((TextView) emptyView.findViewById(R.id.empty_view_title)).setText("没有网络~");
        }
        noScrollGridView.setEmptyView(emptyView);
        commendVideoAdapter = new CommendVideoAdapter(getActivity(),  videoLists);
        noScrollGridView.setAdapter(commendVideoAdapter);
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
                refreshLayout.setRefreshing(true);
                //模拟刷新数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshLayout.isRefreshing())
                            refreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
    }


    /**
     * 获取网站推荐的视频信息内容
     * @param view
     */
    private void initOnlineVideos(View view) {
        //从网络上获取推荐视频内容
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/video/commend");
        List< List<VideoWithUser>> videoWithUser;

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Type type = new TypeToken<List< List<VideoWithUser>>>(){}.getType();
                List< List<VideoWithUser>> videos1 = new Gson().fromJson(result, type);
                Log.i("commend_video", result);
                videoLists.clear();
                videoLists.addAll(videos1);
                commendVideoAdapter.notifyDataSetChanged();
                animationDrawable.stop();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("commend_video", "error");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("commend_video", "cancel");
            }

            @Override
            public void onFinished() {
                Log.i("commend_video", "finish");
            }
        });

    }


    private class CommendVideoAdapter extends BaseAdapter{
        private Context context;
        private List<List<VideoWithUser>> fooVideos;

        private CommendVideoAdapter(Context context, List<List<VideoWithUser>> videos) {
            this.context = context;
            this.fooVideos = videos;
        }


        @Override
        public int getCount() {
            return fooVideos.size();
        }

        @Override
        public List<VideoWithUser> getItem(int position) {
            return fooVideos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_online_video_grid_view, null);
                myViewHolder = new MyViewHolder(convertView);
                convertView.setTag(myViewHolder);
            }

            myViewHolder = (MyViewHolder) convertView.getTag();
            myViewHolder.onlineVideoGridGroup.loadData(videoLists.get(position));

            return convertView;
        }
    }

    private class MyViewHolder {
        private OnlineVideoGridGroup onlineVideoGridGroup;

        public MyViewHolder(View view) {
            onlineVideoGridGroup = (OnlineVideoGridGroup) view.findViewById(R.id.online_video_grid_group);
        }
    }

}
