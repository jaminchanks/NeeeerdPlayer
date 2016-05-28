package com.jamin.neeeerdplayer.ui.videoWithComment;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;
import com.jamin.neeeerdplayer.ui.widget.OnlineVideoGridGroup;

/**
 * Created by jamin on 16-4-18.
 */
public class VideoWithCommentActivity extends SingleFragmentActivity{
    public static final String VIDEO_WITH_COMMENT_SELECTED = "video selected";

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        VideoWithUser videoWithUser = (VideoWithUser) getIntent().getSerializableExtra(VIDEO_WITH_COMMENT_SELECTED);
        return VideoWithCommentFragment.newInstance(videoWithUser);
    }
}
