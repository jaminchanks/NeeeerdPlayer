package com.jamin.neeeerdplayer.ui.mainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.bean.VideoLab;
import com.jamin.neeeerdplayer.ui.compent.SlideBox;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-8.
 */
public class HomePageFragment extends Fragment{
    private ImageView[] mImageViews;

    public static Fragment newInstance() {
        return new HomePageFragment();
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        ArrayList<FooVideo> fooVideos = VideoLab.getInstance(getActivity()).getAllVideos();


        if (fooVideos.size() >= 4) {
            SlideBox slideBox = new SlideBox(getActivity(), fooVideos);
            LinearLayout container = (LinearLayout) view.findViewById(R.id.slide_box_container);
//            ViewPager viewPager = new ViewPager(getActivity());
//            viewPager.setAdapter(new MyAdapter());
//            container.addView(viewPager);
            container.addView(slideBox);
        }
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }



    }

}
