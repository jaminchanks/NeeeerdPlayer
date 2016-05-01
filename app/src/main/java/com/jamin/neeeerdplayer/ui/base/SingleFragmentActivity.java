package com.jamin.neeeerdplayer.ui.base;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jamin.neeeerdplayer.R;


/**
 * Created by jamin on 16-3-5.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    private Fragment mFragment;
    protected abstract Fragment onCreateFragment();

    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }


    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setContentView(getLayoutResId());
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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


    /**
     * 菜单栏选项事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
            case R.id.action_refresh:
                return true;
            case R.id.action_search:
                onSearchRequested();
                return true;

            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
