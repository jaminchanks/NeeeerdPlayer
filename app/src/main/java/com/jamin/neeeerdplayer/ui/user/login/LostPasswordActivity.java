package com.jamin.neeeerdplayer.ui.user.login;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-4-25.
 */
public class LostPasswordActivity extends SingleFragmentActivity{
    @Override
    protected Fragment onCreateFragment() {
        return new LostPasswordFragment();
    }

}
