package com.jamin.neeeerdplayer.ui.user;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.config.NetConfig;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by jamin on 16-3-24.
 */
@ContentView(R.layout.fragment_user_register)
public class UserRegisterFragment extends XBaseFragment implements View.OnClickListener{

    @ViewInject(R.id.et_register_account)
    EditText etRegisterAccount;
    @ViewInject(R.id.et_register_password)
    EditText etRegisterPassword;
    @ViewInject(R.id.et_register_confirm_password)
    EditText etRegisterComfirmPasseord;
    @ViewInject(R.id.et_register_nickname)
    EditText etRegisterUserName;
    @ViewInject(R.id.btn_user_register_submit)
    Button btRegister;

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        getActivity().setTitle("Sign Up");
        btRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        String account = etRegisterAccount.getText().toString();
        String userName = etRegisterUserName.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String comfirmPassword = etRegisterComfirmPasseord.getText().toString();

        if (account.equals("")) {
            etRegisterAccount.setError("请输入账号");
            return;
        }
        if(userName.equals("")) {
            etRegisterUserName.setError("请输入昵称");
            return;
        }
        if (password.equals("")) {
            etRegisterPassword.setError("请输入密码");
            return;
        }
        if (comfirmPassword.equals("")) {
            etRegisterComfirmPasseord.setError("请重复输入密码");
            return;
        }else if (!comfirmPassword.equals(password)) {
            etRegisterComfirmPasseord.setError("密码不一致");
            return;
        }

        RequestParams requestParams = new RequestParams(BaseNetConfig.WEB_URL + "/user/register");
        requestParams.addQueryStringParameter("account", account);
        requestParams.addQueryStringParameter("password", password);
        requestParams.addQueryStringParameter("userName", userName);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //注册成功，返回登陆界面
                if (result.equals("ok")) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserLoginActivity.class);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "账号名已存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
