package com.jamin.neeeerdplayer.ui.user;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

/**
 * Created by jamin on 16-3-23.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener{
    public static final String USER_INFO = "userInfo";

//    @ViewInject(R.id.user_info_avatar)
    private ImageView mIvAvatar;

//    @ViewInject(R.id.user_info_marks)
    private TextView mTvMarks;

//    @ViewInject(R.id.tv_user_info_user_name)
    private TextView mTvUserName;

//    @ViewInject(R.id.tv_user_info_email)
    private TextView mTvEmail;

//    @ViewInject(R.id.tv_user_info_birthday)
    private TextView mTvBirthday;

    private LinearLayout getHeadLayout;


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
        findView(view);
        initView(view);
        setOnClickListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


    private void findView(View view) {
        mIvAvatar = (ImageView) view.findViewById(R.id.user_info_avatar);
        mTvMarks = (TextView) view.findViewById(R.id.user_info_marks);
        mTvUserName = (TextView) view.findViewById(R.id.tv_user_info_user_name);
        mTvEmail = (TextView) view.findViewById(R.id.tv_user_info_email);
        mTvBirthday = (TextView) view.findViewById(R.id.tv_user_info_birthday);

        //隐藏布局
        getHeadLayout = (LinearLayout) view.findViewById(R.id.get_head_layout);

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


        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        //加载圆形头像
        User user = (User) getArguments().getSerializable(USER_INFO);
        mCollapsingToolbarLayout.setTitle(null == user.getUserName() ? "" : user.getUserName());
        String avatarUri = user.getAvatar();
        Glide.with(this)
                .load(avatarUri)
                .transform(new GlideCircleTransform(getActivity()))
                .into(mIvAvatar);
        mTvMarks.setText(user.getMarks() == null ? "这家伙很懒，什么都没留下" : user.getMarks());
        mTvUserName.setText(user.getUserName());
        mTvEmail.setText(user.getEmail() == null ? "未填写" : user.getEmail());
        mTvBirthday.setText(user.getBirthday() == null ? "未填写" : user.getBirthday());

    }

    private void setOnClickListener() {
        mIvAvatar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_avatar:
                //按需求显示或隐藏设置头像操作栏
                if (getHeadLayout.getVisibility() == View.GONE)
                    setGetHeadLayoutVisible(true);
                else setGetHeadLayoutVisible(false);
                break;
        }
    }

    public void setGetHeadLayoutVisible(boolean visible) {
        if (visible) {
            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getActivity(),
                    R.anim.layout_animate_from_left);
            getHeadLayout.setLayoutAnimation(animationController);
            getHeadLayout.setVisibility(View.VISIBLE);
        } else {
            Animation animation =AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up_2_down);
            getHeadLayout.startAnimation(animation);
            getHeadLayout.setVisibility(View.GONE);
        }
    }


}
