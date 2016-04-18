package com.jamin.neeeerdplayer.ui.mainPage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.widget.AutoSlideBoxView;
import com.jamin.neeeerdplayer.ui.widget.NotScrollListView;
import com.jamin.neeeerdplayer.ui.widget.OnlineVideoGridGroup;

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
    List<List<VideoWithUser>> videoLists = new ArrayList<>();
    NotScrollListView noScrollGridView;
    CommendVideoAdapter commendVideoAdapter;
    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
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
    }



    //// TODO: 16-3-8 暂做测试 轮播
    private void initImageCycleView(View view) {
        AutoSlideBoxView autoSlideBoxView = (AutoSlideBoxView) view.findViewById(R.id.cycle_view_test);

        List<AutoSlideBoxView.ImageInfo> list=new ArrayList<AutoSlideBoxView.ImageInfo>();
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image1, "风景1", ""));
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image2, "风景2", ""));
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image3, "风景3", ""));
        list.add(new AutoSlideBoxView.ImageInfo(R.mipmap.test_image4, "风景4", ""));

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


        noScrollGridView = (NotScrollListView) view.findViewById(R.id.lv_commend_video);

        commendVideoAdapter = new CommendVideoAdapter(getActivity(),  videoLists);
        noScrollGridView.setAdapter(commendVideoAdapter);

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
