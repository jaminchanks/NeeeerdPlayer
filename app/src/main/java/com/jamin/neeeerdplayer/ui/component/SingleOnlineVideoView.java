package com.jamin.neeeerdplayer.ui.component;

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
public class SingleOnlineVideoView extends FrameLayout {
    private Context mContext;


    public SingleOnlineVideoView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

     public SingleOnlineVideoView(Context context, FooVideo video) {
         super(context);
         this.mContext = context;
         View view = initView();
         ((FooImageView)view.findViewById(R.id.iv_video_online_thumbnail)).setImageBitmap(video.getThumbnail());
         ((TextView)view.findViewById(R.id.tv_video_online_display_name)).setText(video.getShortDisplayName());
    }



    public SingleOnlineVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private View initView() {
        return View.inflate(mContext, R.layout.component_video_online_item, this);
    }

}
