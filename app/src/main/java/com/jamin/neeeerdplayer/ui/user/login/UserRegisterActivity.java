package com.jamin.neeeerdplayer.ui.user.login;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-3-24.
 */
public class UserRegisterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        return new UserRegisterFragment();
    }
}
