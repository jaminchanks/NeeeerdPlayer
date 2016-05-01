package com.jamin.neeeerdplayer.ui.user.modify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

/**
 * Created by jamin on 16-5-1.
 */
public class UserModifyUserNameFragment extends XBaseFragment {
    private static final String MODIFY_CODE1 = "modify code";

    private EditText mModifyInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_user_name, null);
        mModifyInfo = (EditText) view.findViewById(R.id.et_modify_user_info);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_confirm, menu);

        MenuItem confirmItem = menu.findItem(R.id.action_confirm);
        confirmItem.setVisible(true).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        confirmItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), "点击确认", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

}

