package com.jamin.neeeerdplayer.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.jamin.neeeerdplayer.R;


/**
 * Created by jamin on 16-3-5.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    private Fragment mFragment;
    protected abstract Fragment onCreateFragment();

    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }


    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if (null == mFragment) {
            mFragment = onCreateFragment();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer, mFragment).commit();
        }
    }

    public Fragment getFragment() {
        return mFragment;
    }


}
