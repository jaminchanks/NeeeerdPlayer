package com.jamin.neeeerdplayer.ui.user.modify;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.ui.base.SingleFragmentActivity;

/**
 * Created by jamin on 16-5-1.
 */
public class UserModifyActivity extends SingleFragmentActivity {
    public static final String MODIFY_TYPE = "modify_type";
    public static final int MODIFY_USER_NAME = 0;
    public static final int MODIFY_BIRTHDAY = 1;
    public static final int MODIFY_PASSWORD = 2;
    public static final int MODIFY_MARKS = 3;

    @Override
    protected Fragment onCreateFragment() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        int modifyCode = intent.getIntExtra(MODIFY_TYPE, -1);

        if (modifyCode == MODIFY_USER_NAME) {
            setTitle("修改昵称");
            return new UserModifyUserNameFragment();
        }

        if (modifyCode == MODIFY_BIRTHDAY) {
            setTitle("修改生日");
            return new UserModifyBirthdayFragment();
        }

        if (modifyCode == MODIFY_PASSWORD) {
            setTitle("修改密码");
            return new UserModifyPassFragment();
        }

        if (modifyCode == MODIFY_MARKS) {
            setTitle("修改简介");
            return new UserModifyMarksFragment();
        }


        return null;
    }
}
