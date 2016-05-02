package com.jamin.neeeerdplayer.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;

/**
 * Created by jamin on 16-3-11.
 */
public class OnlineVideoView extends FrameLayout {
    private Context mContext;


    public OnlineVideoView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

     public OnlineVideoView(Context context, FooVideo video) {
         super(context);
         this.mContext = context;
         View view = initView();
         ((FooImageView)view.findViewById(R.id.iv_video_online_thumbnail)).setImageBitmap(video.getThumbnail());
         ((TextView)view.findViewById(R.id.tv_video_online_title)).setText(video.getShortDisplayName());
    }



    public OnlineVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private View initView() {
        return View.inflate(mContext, R.layout.list_item_video_online, this);
    }

}
