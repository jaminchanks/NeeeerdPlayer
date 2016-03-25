package com.jamin.neeeerdplayer.ui.local;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.bean.FooFolder;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-3-7.
 */
public class VideoListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FooFolder folder = (FooFolder) getIntent().getSerializableExtra(FolderListFragment.SELECTED_FOLDER);
        return VideoListFragment.newInstance(folder.getVideos());
    }


}
