package com.jamin.neeeerdplayer.ui.user.modify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jamin on 16-5-1.
 */
public class UserModifyMarksFragment extends XBaseFragment{

    private static final String MODIFY_CODE1 = "modify code";

    private EditText mModifyInfo;
    private User mUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUser = ((BaseApplication) x.app()).getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_user_name, null);
        mModifyInfo = (EditText) view.findViewById(R.id.et_modify_user_info);
        mModifyInfo.setText(mUser.getMarks());
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
                String marks = mModifyInfo.getText().toString();
                if (!TextUtils.isEmpty(marks)) {
                    requestModifyUserName(marks);
                }
                return false;
            }
        });
    }


    private void requestModifyUserName(String marks) {

        RequestParams requestParams = new RequestParams(BaseNetConfig.WEB_URL + "/user/marks");
        requestParams.addParameter("id", mUser.getId());
        requestParams.addParameter("marks", marks);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getActivity().finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
