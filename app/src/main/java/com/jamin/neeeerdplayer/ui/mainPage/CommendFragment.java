package com.jamin.neeeerdplayer.ui.mainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.component.AutoCycleImagesView;

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
    }



    //// TODO: 16-3-8 暂做测试
    private void initImageCycleView(View view) {
        AutoCycleImagesView autoCycleImagesView = (AutoCycleImagesView) view.findViewById(R.id.cycle_view_test);

        List<AutoCycleImagesView.ImageInfo> list=new ArrayList<AutoCycleImagesView.ImageInfo>();
        list.add(new AutoCycleImagesView.ImageInfo(R.mipmap.test_image1, "风景1", ""));
        list.add(new AutoCycleImagesView.ImageInfo(R.mipmap.test_image2, "风景2", ""));
        list.add(new AutoCycleImagesView.ImageInfo(R.mipmap.test_image3, "风景3", ""));
        list.add(new AutoCycleImagesView.ImageInfo(R.mipmap.test_image4, "风景4", ""));


        autoCycleImagesView.loadData(list, new AutoCycleImagesView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(AutoCycleImagesView.ImageInfo imageInfo) {
                //本地图片
                ImageView imageView=new ImageView(getActivity());
                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
                return imageView;
            }
        });

        autoCycleImagesView.setCycleDelayed(5000);
    }

    
    
    
}
