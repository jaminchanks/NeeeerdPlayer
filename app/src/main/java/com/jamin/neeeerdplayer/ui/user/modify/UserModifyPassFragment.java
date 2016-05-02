package com.jamin.neeeerdplayer.ui.user.modify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.config.NetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;
import com.jamin.neeeerdplayer.ui.user.login.UserLoginActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by jamin on 16-5-1.
 */
public class UserModifyPassFragment  extends XBaseFragment implements View.OnClickListener{

    private EditText mEtOrignPassword;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;
    private Button mSubmitButton;
    private User mUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUser = ((BaseApplication) x.app()).getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_password, null);
        mEtOrignPassword = (EditText) view.findViewById(R.id.et_original_password);
        mEtPassword = (EditText) view.findViewById(R.id.et_modify_password_to);
        mEtConfirmPassword = (EditText) view.findViewById(R.id.et_confirm_modify_password_to);
        mSubmitButton = (Button) view.findViewById(R.id.bnt_modify_password_submit);
        mSubmitButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

        String orignPassword = mEtOrignPassword.getText().toString();
        String password = mEtPassword.getText().toString();
        String confirmPassword = mEtConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(orignPassword)) {
            mEtOrignPassword.setError("不能为空");
            return;
        }

        if (!orignPassword.equals(mUser.getPassword())) {
            mEtOrignPassword.setError("密码错误");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError("不能为空");
            return;
        }

        if (!confirmPassword.equals(password)) {
            mEtConfirmPassword.setError("密码不一致");
            return;
        }

        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/user/password");
        params.addParameter("id", mUser.getId());
        params.addParameter("password", password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //清除已登陆信息
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(NetConfig.UID, -1);
                editor.apply();
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserLoginActivity.class);
                startActivity(intent);
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
