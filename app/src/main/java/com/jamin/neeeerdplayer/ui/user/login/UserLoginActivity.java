package com.jamin.neeeerdplayer.ui.user.login;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-3-24.
 */
public class UserLoginActivity extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        return new UserLoginFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //完全退出App
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
