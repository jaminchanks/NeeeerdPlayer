package com.jamin.neeeerdplayer.ui.local;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.database.VideoLab;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-8.
 */
public class FolderListActivity  extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return FolderListFragment.newInstance(getVideos());
    }


    private ArrayList<FooVideo> getVideos() {
        return VideoLab.getInstance(this).getAllVideos();
    }


}
