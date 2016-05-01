package com.jamin.neeeerdplayer.ui.user.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

/**
 * Created by jamin on 16-4-25.
 */
public class LostPasswordFragment extends XBaseFragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lost_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
