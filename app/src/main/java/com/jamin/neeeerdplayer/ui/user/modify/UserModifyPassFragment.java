package com.jamin.neeeerdplayer.ui.user.modify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

/**
 * Created by jamin on 16-5-1.
 */
public class UserModifyPassFragment  extends XBaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_password, null);
        return view;
    }


}
