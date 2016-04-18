package com.jamin.neeeerdplayer.ui.user;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.utils.ImageCacheHelper;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by jamin on 16-3-23.
 */
public class UserInfoFragment extends Fragment {
    public static final String USER_INFO = "userInfo";

//    @ViewInject(R.id.user_info_avatar)
    private ImageView mIvAvatar;

    @ViewInject(R.id.user_info_marks)
    private ImageView mTvMarks;

    @ViewInject(R.id.tv_user_info_user_name)
    private TextView mTvUserName;

    @ViewInject(R.id.tv_user_info_email)
    private TextView mTvEmail;

    @ViewInject(R.id.tv_user_info_birthday)
    private TextView mTvBirthday;



    public static UserInfoFragment newInstance(User user) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_INFO, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);

        mCollapsingToolbarLayout.setTitle("韩战");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        //加载圆形头像
        mIvAvatar = (ImageView) view.findViewById(R.id.user_info_avatar);
        User user = ((BaseApplication)x.app()).getUser();
        String avatarUri = user.getAvatar();
        ImageCacheHelper.getImageLoader().displayImage(avatarUri, mIvAvatar, ImageCacheHelper.getMyAcountAvatarOptions());




    }


}
