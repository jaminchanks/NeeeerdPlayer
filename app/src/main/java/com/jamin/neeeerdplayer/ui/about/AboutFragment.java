package com.jamin.neeeerdplayer.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

/**
 * Created by jamin on 16-5-3.
 */
public class AboutFragment extends XBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_me, container, false);
    }
}
