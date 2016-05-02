package com.jamin.neeeerdplayer.ui.video_comment;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;
import com.jamin.neeeerdplayer.ui.widget.OnlineVideoGridGroup;

import io.vov.vitamio.Vitamio;

/**
 * Created by jamin on 16-4-18.
 */
public class VideoWithCommentActivity extends SingleFragmentActivity{

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        VideoWithUser videoWithUser = (VideoWithUser) getIntent().getSerializableExtra(OnlineVideoGridGroup.VIDEO_WITH_COMMENT_SELECTED);
        return VideoWithCommentFragment.newInstance(videoWithUser);
    }
}
