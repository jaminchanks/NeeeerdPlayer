package com.jamin.neeeerdplayer.ui.component;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.FrameLayout;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-8.
 */
public class SlideBox extends FrameLayout {
    private ArrayList<FooVideo> mVideos = null;

    public SlideBox(Context context) {
        super(context);
        initView(context);
    }


    private void initView(Context context){
        View.inflate(context, R.layout.component_slide_box, this);
    }

}
