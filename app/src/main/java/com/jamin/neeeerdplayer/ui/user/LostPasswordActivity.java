package com.jamin.neeeerdplayer.ui.user;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.R;
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
