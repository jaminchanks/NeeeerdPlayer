package com.jamin.neeeerdplayer.ui.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.ui.local.VideoListFragment;

/**
 * Created by jamin on 16-3-6.
 */
public class PlayerActivity extends SingleFragmentActivity {
    private FooVideo mPlayingVideo;

    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }


    @Override
    protected Fragment onCreateFragment() {
        return PlayerFragment.newInstance(mPlayingVideo);
    }


    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        mPlayingVideo = (FooVideo) getIntent().getSerializableExtra(VideoListFragment.SELECTED_VIDEO);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        super.onCreate(onSavedInstanceState);
    }
}
