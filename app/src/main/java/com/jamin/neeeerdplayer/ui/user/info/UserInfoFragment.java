package com.jamin.neeeerdplayer.ui.user.info;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.UserRelationship;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.config.NetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.BasePathConfig;
import com.jamin.neeeerdplayer.ui.user.friends.FriendListActivity;
import com.jamin.neeeerdplayer.ui.user.login.UserLoginActivity;
import com.jamin.neeeerdplayer.ui.user.modify.UserModifyActivity;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;
import com.jamin.neeeerdplayer.utils.CompressImage;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jamin on 16-3-23.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = UserInfoFragment.class.getSimpleName();

    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;

    public static final String USER_INFO = "userInfo";

    private Activity mGetActivity;
    private ImageView mIvAvatar;
    private TextView mTvMarks;
    private TextView mTvUserName;
    private TextView mTvEmail;
    private TextView mTvBirthday;
    private LinearLayout getHeadLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private Button mBtnLogOut;
    private Uri imageUri;
    private User mUser;

    //用户基本信息栏
    private ImageView mModifyUserName;
    private ImageView mModifyBirthday;
    private ImageView mModifyPassword;
    private TextView mTvModifyMarks;
    private ImageView mIvModifyMarks;

    //头像选项
    private TextView mTvChoosePhoto;
    private TextView mTvTakePhoto;
    private TextView mTvCancel;

    //好友关系
    private LinearLayout mFollowLayout;
    private LinearLayout mBeFollowLayout;
    private TextView mFollowedNum;
    private TextView mBeFollowedNum;
    private boolean isSecondTimeResume = false;

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
        mGetActivity = getActivity();
        mUser = ((BaseApplication)mGetActivity.getApplication()).getUser();
        requestUserRelationship();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findView(view);
        initView(view);
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
        mTvModifyMarks = (TextView) view.findViewById(R.id.user_info_marks);
        mIvModifyMarks = (ImageView) view.findViewById(R.id.iv_modify_marks);

        mFollowedNum = (TextView) view.findViewById(R.id.tv_user_follow_num);
        mBeFollowedNum = (TextView) view.findViewById(R.id.tv_user_be_followed_num);

        mFollowLayout = (LinearLayout) view.findViewById(R.id.ly_user_follow);
        mBeFollowLayout = (LinearLayout) view.findViewById(R.id.ly_user_be_followed);

        //隐藏布局, 头像的选项
        getHeadLayout = (LinearLayout) view.findViewById(R.id.get_head_layout);
        if (getHeadLayout != null) {
            mTvChoosePhoto = (TextView) getHeadLayout.findViewById(R.id.tv_choose_photo);
            mTvTakePhoto = (TextView) getHeadLayout.findViewById(R.id.tv_take_photo);
            mTvCancel = (TextView) getHeadLayout.findViewById(R.id.tv_cancel_get_head);
        }

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
        User user = (User) getArguments().getSerializable(USER_INFO);
        mCollapsingToolbarLayout.setTitle(null == user.getUserName() ? "" : user.getUserName());
        String avatarUri = user.getAvatar();
        Glide.with(this)
                .load(avatarUri)
                .transform(new GlideCircleTransform(mGetActivity))
                .into(mIvAvatar);
        mTvMarks.setText(user.getMarks() == null ? "这家伙很懒，什么都没留下" : user.getMarks());
        mTvUserName.setText(user.getUserName());
        mTvEmail.setText(user.getEmail() == null ? "未填写" : user.getEmail());
        mTvBirthday.setText(user.getBirthday() == null ? "未填写" : user.getBirthday());

    }

    private void setOnClickListener() {
        mIvAvatar.setOnClickListener(this);
        mBtnLogOut.setOnClickListener(this);
        mTvChoosePhoto.setOnClickListener(this);
        mTvTakePhoto.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        getHeadLayout.setOnClickListener(this);

        mModifyUserName.setOnClickListener(this);
        mModifyBirthday.setOnClickListener(this);
        mModifyPassword.setOnClickListener(this);
        mIvModifyMarks.setOnClickListener(this);

        mFollowedNum.setOnClickListener(this);
        mBeFollowedNum.setOnClickListener(this);
        mFollowLayout.setOnClickListener(this);
        mBeFollowLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.user_info_avatar:
                //按需求显示或隐藏设置头像操作栏
                if (getHeadLayout.getVisibility() == View.GONE)
                    setGetHeadLayoutVisible(true);
                else setGetHeadLayoutVisible(false);
                break;
            case R.id.logout_btn:
                //清除已登陆信息
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mGetActivity);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(NetConfig.UID, -1);
                editor.apply();
                intent = new Intent();
                intent.setClass(mGetActivity, UserLoginActivity.class);
                startActivity(intent);
                mGetActivity.finish();
                break;

            case R.id.tv_choose_photo:
                // 从相册获取图片
                File outputImageFile = new File(BasePathConfig.getInstance().getImageDiskCacheDir(), "output_img.jpg");
                try {
                    if (outputImageFile.exists())
                        outputImageFile.delete();
                    outputImageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("album", "something wrong when get album");
                }
                imageUri = Uri.fromFile(outputImageFile);
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                intent1.putExtra("crop", true);
                intent1.putExtra("scale", true);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent1, CROP_PHOTO);
                break;

            case R.id.tv_take_photo:
                //从相机获取图片
                File imageFile = new File(BasePathConfig.getInstance().getImageDiskCacheDir(), "tempImage.jpg");
                try {
                    if (imageFile.exists())
                        imageFile.delete();
                    imageFile.createNewFile();  //这里会跑出异常
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(imageFile);
                intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO); //启动相机程序
                break;

            case R.id.tv_cancel_get_head:
                getHeadLayout.setVisibility(View.GONE);
                break;

            case R.id.get_head_layout:
                getHeadLayout.setVisibility(View.GONE);
                break;
            case R.id.iv_modify_user_name:
                toUserModifyActivity(UserModifyActivity.MODIFY_USER_NAME);
                break;
            case R.id.iv_modify_birthday:
                toUserModifyActivity(UserModifyActivity.MODIFY_BIRTHDAY);
                break;
            case R.id.iv_modify_password:
                toUserModifyActivity(UserModifyActivity.MODIFY_PASSWORD);
                break;

            case R.id.iv_modify_marks:
                toUserModifyActivity(UserModifyActivity.MODIFY_MARKS);
                break;

            case R.id.ly_user_follow:
            case R.id.tv_user_follow_num:
                toFriendListActivity(FriendListActivity.FOLLOW_TYPE);
                break;
            case R.id.ly_user_be_followed:
            case R.id.tv_user_be_followed_num:
                toFriendListActivity(FriendListActivity.BE_FOLLOWED_TYPE);
                break;
            default:
                getHeadLayout.setVisibility(View.GONE);
                break;
        }
    }

    public void setGetHeadLayoutVisible(boolean visible) {
        if (visible) {
            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(mGetActivity,
                    R.anim.layout_animate_from_left);
            getHeadLayout.setLayoutAnimation(animationController);
            getHeadLayout.setVisibility(View.VISIBLE);
        } else {
            Animation animation =AnimationUtils.loadAnimation(mGetActivity, R.anim.slide_up_2_down);
            getHeadLayout.startAnimation(animation);
            getHeadLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
            case CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        //从相机获取图片
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                mGetActivity.getContentResolver().openInputStream(imageUri));
                        //从相册获取图片
                        if (bitmap == null) {
                            bitmap = BitmapFactory.decodeStream(
                                    mGetActivity.getContentResolver().openInputStream(data.getData()));
                        }
                        //压缩图片
                        Bitmap compressedBitmap = CompressImage.compressByRate(bitmap, 256f, 256f);
                        //重命名存放到本地的图片
                        String imageName = System.currentTimeMillis() + ".png";
                        //把图片保存至本地
                        saveImage(compressedBitmap, imageName);
                        //把图片上传至网络，上传成功之后还会修改本地的头像信息
                        uploadFile(imageName);
                        //更新左侧信息显示
                        //更新数据库
                        getHeadLayout.setVisibility(View.GONE);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }


    private boolean saveImage(Bitmap bitmap, String fileName) {
        //保存头像图片到本地
        Bitmap.CompressFormat format= Bitmap.CompressFormat.PNG;
        OutputStream stream = null;
        File imageFilePath = new File(BasePathConfig.getInstance().getImageDiskCacheDir(), fileName);
        try {
            if (imageFilePath.exists())
                imageFilePath.delete();
            imageFilePath.createNewFile();  //这里会跑出异常
            stream = new FileOutputStream(BasePathConfig.getInstance().getImageDiskCacheDir() + File.separator + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bitmap.compress(format, 100, stream);
    }


    private void uploadFile(String imageName) {
        //获取图片全路径
        String imagePath = BasePathConfig.getInstance().getImageDiskCacheDir() + File.separator + imageName;

        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/user/avatar");
        params.addParameter("id", 1);
        params.addBodyParameter("avatar", new File(imagePath));
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(mGetActivity, "上传成功" , Toast.LENGTH_LONG).show();
                //刷新已登陆用户的信息
                refreshUser();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mGetActivity, "上传失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(mGetActivity, "上传取消", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "上传结束");
            }
        });
    }


    private void refreshUser() {
        RequestParams requestParams = new RequestParams(BaseNetConfig.WEB_URL + "/user/id");
        requestParams.addParameter("id", mUser.getId());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                mUser = new Gson().fromJson(result, User.class);
                saveUserInfoToLocal();

                ((BaseApplication)mGetActivity.getApplication()).setUser(mUser);

                Glide.with(mGetActivity).load(mUser.getAvatar()).transform(new GlideCircleTransform(getActivity())).into(mIvAvatar);
                mTvMarks.setText(mUser.getMarks());
                mTvUserName.setText(mUser.getUserName());
                mTvBirthday.setText(mUser.getBirthday());
                mCollapsingToolbarLayout.setTitle(null == mUser.getUserName() ? "" : mUser.getUserName());

                Log.i(TAG, "刷新成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "刷新失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "刷新取消");
            }

            @Override
            public void onFinished() {  }
        });
    }

    private void saveUserInfoToLocal() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(x.app());
        SharedPreferences.Editor editor = preferences.edit();
        //把这两个重要信息放入本地中，表示用户已经登陆过了
        editor.putInt(NetConfig.UID, mUser.getId());
        editor.putString(NetConfig.USER_INFO, new Gson().toJson(mUser));
        editor.apply();
    }

    private void toUserModifyActivity(int modifyCode) {
        Intent intent = new Intent();
        intent.setClass(mGetActivity, UserModifyActivity.class);
        intent.putExtra(UserModifyActivity.MODIFY_TYPE,modifyCode);
        startActivity(intent);
    }

    private void toFriendListActivity(int relationshipType) {
        Intent intent = new Intent();
        intent.setClass(mGetActivity, FriendListActivity.class);
        intent.putExtra(FriendListActivity.RELATIONSHIP_TYPE, relationshipType);
        startActivity(intent);
    }


    private void requestUserRelationship () {
        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/relationship/user");
        params.addParameter("userId", mUser.getId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                UserRelationship relationship = new Gson().fromJson(result, UserRelationship.class);
                mFollowedNum.setText(relationship.getFollowUsers().size() + "");
                mBeFollowedNum.setText(relationship.getBeFollowedUsers().size() + "");

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



    @Override
    public void onResume() {
        super.onResume();
        refreshUser();
        saveUserInfoToLocal();
        refreshFollowNum();
    }

    private void refreshFollowNum() {
        if (!isSecondTimeResume) {
            isSecondTimeResume = true;
            return;
        }

        UserRelationship relationship = ((BaseApplication) x.app()).getUserRelationship();
        mFollowedNum.setText(relationship.getFollowUsers().size() + "");
        mBeFollowedNum.setText(relationship.getBeFollowedUsers().size() + "");
    }
}
