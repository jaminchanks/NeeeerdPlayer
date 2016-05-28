package com.jamin.neeeerdplayer.ui.user.info;



import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-3-8.
 */
public class UserInfoActivity extends SingleFragmentActivity{
    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        User user = ((BaseApplication)getApplication()).getUser();
        return UserInfoFragment.newInstance(user);
    }
}
