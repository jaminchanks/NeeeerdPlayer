package com.jamin.neeeerdplayer.ui.component;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jamin.neeeerdplayer.bean.FooVideo;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-8.
 */
public class SlideBox1 extends ViewPager{
    private Context mContext;
    private ArrayList<FooVideo> mVideos;
    private ArrayList<ImageView> mImageViews = new ArrayList<>();
    private static final int PAGER_COUNT = 4;
    public SlideBox1(Context context, ArrayList<FooVideo> fooVideos) {
        super(context);
        this.mContext = context;
        this.mVideos = fooVideos;

        //要求传入的子项不少于4项
        if (mVideos.size() < PAGER_COUNT) {
            throw new IllegalArgumentException("所添加的ViewPager子项少于4项");
        }

        for (int i = 0; i < PAGER_COUNT; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(mVideos.get(i).getThumbnail());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews.add(imageView);
        }
        setAdapter(new SlideBoxAdapter());
    }


    private class SlideBoxAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            //设置成最大，使用户看不到边界
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            //对ViewPager页号求模取出View列表中要显示的项
//            position %= mImageViews.size();
//            if (position < 0){
//                position = mImageViews.size()+position;
//            }
//            ImageView view = mImageViews.get(position);
//            //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//            ViewParent vp =view.getParent();
//            if (vp!=null){
//                ViewGroup parent = (ViewGroup)vp;
//                parent.removeView(view);
//            }
//            container.addView(view);
//            //add listeners here if necessary
            container.addView(mImageViews.get(position % PAGER_COUNT), 0);
            return mImageViews.get(position % PAGER_COUNT);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //Warning：不要在这里调用removeView
            container.removeView(mImageViews.get(position % PAGER_COUNT));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }
    }

}
