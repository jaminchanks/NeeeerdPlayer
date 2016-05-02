package com.jamin.neeeerdplayer.ui.user.friends;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-5-2.
 */
public class FriendListActivity extends SingleFragmentActivity {

    public static final String RELATIONSHIP_TYPE = "relationship_type";
    public static final int FOLLOW_TYPE = 0;
    public static final int BE_FOLLOWED_TYPE = 1;

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        int relationShipType = getIntent().getIntExtra(RELATIONSHIP_TYPE, -1);
        if (relationShipType == FOLLOW_TYPE) {
            setTitle("关注者列表");
        }
        if (relationShipType == BE_FOLLOWED_TYPE) {
            setTitle("粉丝列表");
        }

        return FriendListFragment.getInstance(relationShipType);
    }
}
