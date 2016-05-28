package com.jamin.neeeerdplayer.ui.myUploaded;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-5-1.
 */
public class UploadedActivity extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return new UploadedVideoFragment();
    }
}
