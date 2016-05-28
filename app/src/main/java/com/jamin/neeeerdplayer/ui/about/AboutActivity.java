package com.jamin.neeeerdplayer.ui.about;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-5-3.
 */
public class AboutActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return new AboutFragment();
    }
}
