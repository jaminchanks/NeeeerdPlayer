package com.jamin.neeeerdplayer.ui.user.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.MainActivity;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.config.PreferensConfig;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;
import com.jamin.neeeerdplayer.utils.StringUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by jamin on 16-3-24.
 */
@ContentView(R.layout.fragment_user_login)
public class UserLoginFragment extends XBaseFragment {
    @ViewInject(R.id.bnt_user_login_submit)
    private Button mBtnSubmit;
    @ViewInject(R.id.tv_user_lostPass)
    private TextView mTvLostPass;
    @ViewInject(R.id.tv_user_login_register)
    private TextView mTvRegister;
    @ViewInject(R.id.et_user_login_email)
    private EditText mEtUserEmail;
    @ViewInject(R.id.et_user_login__password)
    private EditText mEtUserPassword;

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String ACCOUNT_NO_FOUND = "no user found";
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        getActivity().setTitle("登陆");
    }


    @Event(value = {R.id.bnt_user_login_submit, R.id.tv_user_lostPass, R.id.tv_user_login_register},
            type = View.OnClickListener.class)
    private void loginEvent(View view){

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_user_lostPass:
                intent.setClass(getActivity(), LostPasswordActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_user_login_register:
                intent.setClass(getActivity(), UserRegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.bnt_user_login_submit:
                String email = mEtUserEmail.getText().toString();
                String password = mEtUserPassword.getText().toString();

                if (StringUtils.isEmpty(email)) {
                    mEtUserEmail.setError("账号不能为空");
                    mEtUserEmail.setFocusable(true);
                    break;
                }

                if (StringUtils.isEmpty(password)) {
                    mEtUserPassword.setError("密码不能为空");
                    mEtUserPassword.setFocusable(true);
                    break;
                }
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("正在登陆");
                progressDialog.show();
                askForLogin(email, password);

                break;

            default:
                break;
        }
    }


    /**
     * 用户登陆
     * @param email
     * @param password
     */
    private void askForLogin(String email, String password) {
        RequestParams requestParams = new RequestParams(BaseNetConfig.WEB_URL + "/user/login");
        requestParams.addQueryStringParameter(EMAIL_PARAM, email);
        requestParams.addQueryStringParameter(PASSWORD_PARAM, password);
        requestParams.setConnectTimeout(5000);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals(ACCOUNT_NO_FOUND)) {
                    Toast.makeText(getActivity(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                Gson gson = new Gson();
                User user = gson.fromJson(result, User.class);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(x.app());
                SharedPreferences.Editor editor = preferences.edit();

                //把这两个重要信息放入本地中，表示用户已经登陆过了
                editor.putInt(PreferensConfig.UID, user.getId());
                editor.putString(PreferensConfig.USER_INFO, result);

                //把用户的基本信息保存在本地
                Log.i("user_saved_info", user.toString());
                editor.apply();
                //把用户信息保存到全局
                ((BaseApplication)x.app()).setUser(user);

                progressDialog.dismiss();
                //跳转至主页面
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);

                startActivity(intent);
                getActivity().finish();
                
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) {
                    Log.e("onError", ((HttpException) ex).getErrorCode() + ((HttpException) ex).getResult());
                } else {
                    ex.printStackTrace();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                progressDialog.dismiss();
            }

            @Override
            public void onFinished() {
                progressDialog.dismiss();
            }
        });
    }




}
