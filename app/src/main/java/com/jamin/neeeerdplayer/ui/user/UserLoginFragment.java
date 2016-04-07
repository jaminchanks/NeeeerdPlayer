package com.jamin.neeeerdplayer.ui.user;

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
import com.jamin.neeeerdplayer.ui.MainActivity;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.NetConfig;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;
import com.jamin.neeeerdplayer.utils.ImageCacheHelper;
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
    @ViewInject(R.id.et_user_login_account)
    private EditText mEtUserAccount;
    @ViewInject(R.id.et_user_login__password)
    private EditText mEtUserPassword;

    private static final String ACCOUNT_PARAM = "account";
    private static final String PASSWORD_PARAM = "password";
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

        getActivity().setTitle("Sign In");
    }


    @Event(value = {R.id.bnt_user_login_submit, R.id.tv_user_lostPass, R.id.tv_user_login_register},
            type = View.OnClickListener.class)
    private void loginEvent(View view){
        switch (view.getId()) {
            case R.id.tv_user_lostPass:

                break;
            case R.id.tv_user_login_register:
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserRegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.bnt_user_login_submit:
                String account = mEtUserAccount.getText().toString();
                String password = mEtUserPassword.getText().toString();

                if (StringUtils.isEmpty(account)) {
                    mEtUserAccount.setError("账号不能为空");
                    mEtUserAccount.setFocusable(true);
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
                askForLogin(account, password);

                break;

            default:
                break;
        }
    }


    private void askForLogin(String account, String password) {
        RequestParams requestParams = new RequestParams(NetConfig.USER_LOGIN_URL);
        requestParams.addQueryStringParameter(ACCOUNT_PARAM, account);
        requestParams.addQueryStringParameter(PASSWORD_PARAM, password);
        requestParams.setConnectTimeout(5000);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                User user = gson.fromJson(result, User.class);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(x.app());
                SharedPreferences.Editor editor = preferences.edit();

                //把这两个重要信息放入本地中，表示用户已经登陆过了
                editor.putString(NetConfig.ACCOUNT_NAME, user.getAccount());
                //// TODO: 16-4-4  改变user_token的值
                editor.putInt(NetConfig.USER_TOKEN, user.getId());
                
                editor.apply();

               //todo 把用户头像存在本地




                progressDialog.dismiss();
                //跳转至主页面
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);

                ((BaseApplication)x.app()).setUser(user);
                Log.i("user_to_be_set", user.getHead());

                startActivity(intent);
                
                //// TODO: 16-4-7
                Toast.makeText(getActivity(), ACCOUNT_PARAM + PASSWORD_PARAM, Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof HttpException) {
                    Log.e("onError", ((HttpException) ex).getErrorCode() + ((HttpException) ex).getResult());
                } else {
                    ex.printStackTrace();
                }
                Toast.makeText(x.app(), "onError", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "onCancel", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFinished() {
                Toast.makeText(x.app(), "onFinish", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }




}
