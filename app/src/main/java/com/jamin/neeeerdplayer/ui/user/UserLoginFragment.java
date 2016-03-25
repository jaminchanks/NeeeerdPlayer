package com.jamin.neeeerdplayer.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by jamin on 16-3-24.
 */
@ContentView(R.layout.fragment_user_login)
public class UserLoginFragment extends XBaseFragment {
    @ViewInject(R.id.bnt_user_login_submit)
    private Button mBtnSubmit;
    @ViewInject(R.id.tv_user_lostPass)
    private TextView mLostPass;
    @ViewInject(R.id.tv_user_login_register)
    private TextView mRegister;
    @ViewInject(R.id.et_user_login_account)
    private EditText mUserAccount;
    @ViewInject(R.id.et_user_login__password)
    private EditText mUserPassword;


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

        getActivity().setTitle("Sign In");
    }


    @Event(value = {R.id.bnt_user_login_submit, R.id.tv_user_lostPass, R.id.tv_user_login_register},
            type = View.OnClickListener.class)
    private void registerPage(View view){
        switch (view.getId()) {
            case R.id.tv_user_lostPass:

                break;
            case R.id.tv_user_login_register:
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }



}
