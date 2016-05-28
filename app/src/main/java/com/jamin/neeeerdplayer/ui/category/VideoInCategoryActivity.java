package com.jamin.neeeerdplayer.ui.category;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-5-2.
 */
public class VideoInCategoryActivity extends SingleFragmentActivity {
    public static final String CATEGORY_ID = "category id";

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        int categoryId = intent.getIntExtra(CATEGORY_ID, -1);
        return VideoInCategoryFragment.getInstance(categoryId);
    }
}
