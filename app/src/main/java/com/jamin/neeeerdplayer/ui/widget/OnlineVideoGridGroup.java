package com.jamin.neeeerdplayer.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.bean.OnlineVideo;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.ui.mainPage.OnlineVideoGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-3-12.
 */
public class OnlineVideoGridGroup extends FrameLayout{

    private Context mContext;
    private View mVideoGroup;
    private static final int VIDEO_VIEW_SPACE_DP = 10; //两个videoView之间的间隔

    public OnlineVideoGridGroup(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public OnlineVideoGridGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
         mVideoGroup = LayoutInflater.from(context).inflate(R.layout.component_video_online_group, this);
    }

     public void loadData(List<VideoWithUser> videos) {
         GridView gridView = (GridView) findViewById(R.id.grid_video_online);
         gridView.setAdapter(new OnlineVideoGridAdapter(mContext, videos));
     }




}
