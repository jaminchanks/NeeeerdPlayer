package com.jamin.neeeerdplayer.ui.local;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.jamin.neeeerdplayer.bean.FooFolder;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-7.
 */
public class VideoListActivity extends SingleFragmentActivity{
    private ArrayList<FooVideo> mVideos;

    @Override
    protected Fragment onCreateFragment() {
        FooFolder folder = (FooFolder) getIntent().getSerializableExtra(FolderListFragment.SELECTED_FOLDER);
        return VideoListFragment.newInstance(folder.getVideos());
    }






}
