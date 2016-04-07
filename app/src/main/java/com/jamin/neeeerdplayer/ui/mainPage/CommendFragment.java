package com.jamin.neeeerdplayer.ui.mainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.database.VideoLab;
import com.jamin.neeeerdplayer.ui.widget.AutoSlideBoxView;
import com.jamin.neeeerdplayer.ui.widget.OnlineVideoGridGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-3-8.
 */
public class CommendFragment extends Fragment{
    private ImageView[] mImageViews;

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

    private void initOnlineVideos(View view) {
        ArrayList<FooVideo> fooVideos = new ArrayList<>();

        ArrayList<FooVideo> videos = VideoLab.getInstance(getActivity()).getAllVideos();
        //测试代码
        for (int i = 0; i < (videos.size() > 4 ? 4 : videos.size()); i++) {
            fooVideos.add(videos.get(i));
        }


        OnlineVideoGridGroup videoGroup1 = (OnlineVideoGridGroup) view.findViewById(R.id.online_video_group1);
        videoGroup1.loadData(fooVideos);

        OnlineVideoGridGroup videoGroup2 = (OnlineVideoGridGroup) view.findViewById(R.id.online_video_group2);
        videoGroup2.loadData(fooVideos);

        OnlineVideoGridGroup videoGroup3 = (OnlineVideoGridGroup) view.findViewById(R.id.online_video_group3);
        videoGroup3.loadData(fooVideos);

    }
    
    
}
