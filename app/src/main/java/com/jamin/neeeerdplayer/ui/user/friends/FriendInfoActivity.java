package com.jamin.neeeerdplayer.ui.user.friends;

import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.bean.UserInfo;
import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-5-2.
 */
public class FriendInfoActivity extends SingleFragmentActivity {
    public static final String USER_INFO_EXTRA = "userInfo";

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        UserInfo userInfo = (UserInfo) getIntent().getSerializableExtra(USER_INFO_EXTRA);
        return FriendInfoFragment.newInstance(userInfo);
    }

}
