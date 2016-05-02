package com.jamin.neeeerdplayer.ui.user.friends;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.UserInfo;
import com.jamin.neeeerdplayer.bean.UserRelationship;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by jamin on 16-5-2.
 */
public class FriendInfoFragment extends XBaseFragment implements View.OnClickListener{
    private static final String TAG = FriendInfoFragment.class.getSimpleName();

    public static final String FRIEND_INFO = "friendInfo";

    private Activity mGetActivity;
    private ImageView mIvAvatar;
    private TextView mTvMarks;
    private TextView mTvUserName;
    private TextView mTvEmail;
    private TextView mTvBirthday;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private LinearLayout mPasswordLayout;


    private Button mBtnLogOut;
    private UserInfo mUserInfo;
    private User mLoginUser;
    private User mFriend;

    //用户基本信息栏
    private ImageView mModifyUserName;
    private ImageView mModifyBirthday;
    private ImageView mModifyPassword;
    private ImageView mIvModifyMarks;

    //好友关系
    private LinearLayout mFollowLayout;
    private LinearLayout mBeFollowLayout;
    private TextView mFollowedNum;
    private TextView mBeFollowedNum;

    private UserRelationship mRelationship;
    private boolean mIsfollowedUser = false;  //是否关注过该用户

    private MenuItem mMenuItem;


    public static FriendInfoFragment newInstance(UserInfo userInfo) {
        FriendInfoFragment fragment = new FriendInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FRIEND_INFO, userInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
        mGetActivity = getActivity();
        mRelationship = ((BaseApplication) x.app()).getUserRelationship();
        mLoginUser = ((BaseApplication) x.app()).getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findView(view);
        initView(view);
        hideUserOptions();
        requestFriendInfo();
        setOnClickListener();
    }

    private void findView(View view) {
        mIvAvatar = (ImageView) view.findViewById(R.id.user_info_avatar);
        mTvMarks = (TextView) view.findViewById(R.id.user_info_marks);
        mTvUserName = (TextView) view.findViewById(R.id.tv_user_info_user_name);
        mTvEmail = (TextView) view.findViewById(R.id.tv_user_info_email);
        mTvBirthday = (TextView) view.findViewById(R.id.tv_user_info_birthday);

        mBtnLogOut = (Button) view.findViewById(R.id.logout_btn);

        mModifyUserName = (ImageView) view.findViewById(R.id.iv_modify_user_name);
        mModifyBirthday = (ImageView) view.findViewById(R.id.iv_modify_birthday);
        mModifyPassword = (ImageView) view.findViewById(R.id.iv_modify_password);
        mIvModifyMarks = (ImageView) view.findViewById(R.id.iv_modify_marks);

        mFollowedNum = (TextView) view.findViewById(R.id.tv_user_follow_num);
        mBeFollowedNum = (TextView) view.findViewById(R.id.tv_user_be_followed_num);

        mFollowLayout = (LinearLayout) view.findViewById(R.id.ly_user_follow);
        mBeFollowLayout = (LinearLayout) view.findViewById(R.id.ly_user_be_followed);
        mPasswordLayout = (LinearLayout) view.findViewById(R.id.ly_user_password);
    }


    private void initView(View view) {
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)mGetActivity).setSupportActionBar(mToolbar);
        ((AppCompatActivity)mGetActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetActivity.onBackPressed();
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);

        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        //加载圆形头像
        mUserInfo = (UserInfo) getArguments().getSerializable(FRIEND_INFO);
        List<UserInfo> userInfos1 = mRelationship.getFollowUsers();
        //判断是否是已关注用户
        for (UserInfo userInfo : userInfos1) {
            if(userInfo.getUserId() == mUserInfo.getUserId()) {
                mIsfollowedUser = true;
            }
        }

        mCollapsingToolbarLayout.setTitle(null == mUserInfo.getUserName() ? "" : mUserInfo.getUserName());
        String avatarUri = mUserInfo.getUserAvatar();
        Glide.with(this)
                .load(avatarUri)
                .transform(new GlideCircleTransform(mGetActivity))
                .into(mIvAvatar);
    }

    private void setOnClickListener() {
        mFollowedNum.setOnClickListener(this);
        mBeFollowedNum.setOnClickListener(this);
        mFollowLayout.setOnClickListener(this);
        mBeFollowLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_user_follow:
            case R.id.tv_user_follow_num:
                break;
            case R.id.ly_user_be_followed:
            case R.id.tv_user_be_followed_num:
                break;
            default:
                break;
        }
    }

    private void hideUserOptions() {
        mBtnLogOut.setVisibility(View.GONE);
        mIvModifyMarks.setVisibility(View.GONE);
        mModifyBirthday.setVisibility(View.GONE);
        mModifyPassword.setVisibility(View.GONE);
        mModifyUserName.setVisibility(View.GONE);
        mPasswordLayout.setVisibility(View.GONE);
    }

    private void requestFriendInfo() {
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/user/id");
        params.addParameter("id", mUserInfo.getUserId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mFriend = new Gson().fromJson(result, User.class);

                mTvMarks.setText(mFriend.getMarks() == null ? "这家伙很懒，什么都没留下" : mFriend.getMarks());
                mTvUserName.setText(mFriend.getUserName());
                mTvEmail.setText(mFriend.getEmail() == null ? "未填写" : mFriend.getEmail());
                mTvBirthday.setText(mFriend.getBirthday() == null ? "未填写" : mFriend.getBirthday());

                //请求好友列表信息
                requestFriendRelationship();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) {  }

            @Override
            public void onFinished() { }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_follow, menu);
        if (mIsfollowedUser) {
            mMenuItem = menu.findItem(R.id.action_cancel_follow);
        } else {
            mMenuItem = menu.findItem(R.id.action_follow);
        }

        mMenuItem.setVisible(true).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                followFriendOrNot();
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 关注该好友
     */
    private void followFriendOrNot() {
        RequestParams params;
        if (mIsfollowedUser) {
            params = new RequestParams(BaseNetConfig.WEB_URL + "/relationship/cancel_follow");
        } else {
            params = new RequestParams(BaseNetConfig.WEB_URL + "/relationship/add_follow");
        }

        params.addParameter("userId", mLoginUser.getId());
        params.addParameter("followUserId", mFriend.getId());
        Log.i("followUserId", mFriend.getId() + "\n");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (mIsfollowedUser) {
                    mMenuItem.setTitle(mGetActivity.getResources().getString(R.string.action_follow));
                    Toast.makeText(mGetActivity, "取消关注成功！", Toast.LENGTH_SHORT).show();
                    mIsfollowedUser = false;
                } else {
                    mMenuItem.setTitle(mGetActivity.getResources().getString(R.string.action_cancel_follow));
                    Toast.makeText(mGetActivity, "关注成功！", Toast.LENGTH_SHORT).show();
                    mIsfollowedUser = true;
                }

                requestLoginUserRelationship();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) { }

            @Override
            public void onFinished() { }
        });
    }


    private void requestFriendRelationship() {
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/relationship/user");
        params.addParameter("userId", mFriend.getId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserRelationship relationship = new Gson().fromJson(result, UserRelationship.class);
                mFollowedNum.setText(relationship.getFollowUsers().size() + "");
                mBeFollowedNum.setText(relationship.getBeFollowedUsers().size() + "");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) { }

            @Override
            public void onFinished() {  }
        });
    }


    private void requestLoginUserRelationship () {
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/relationship/user");
        params.addParameter("userId", mLoginUser.getId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserRelationship relationship = new Gson().fromJson(result, UserRelationship.class);

                //保存好友列表
                ((BaseApplication) x.app()).setUserRelationship(relationship);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) { }

            @Override
            public void onCancelled(CancelledException cex) { }

            @Override
            public void onFinished() {  }
        });
    }


}
