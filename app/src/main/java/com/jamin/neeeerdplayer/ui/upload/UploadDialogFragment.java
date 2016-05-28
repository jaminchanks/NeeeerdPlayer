package com.jamin.neeeerdplayer.ui.upload;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.ui.MainActivity;
import com.jamin.neeeerdplayer.ui.service.UploadService;

/**
 * Created by jamin on 16-5-3.
 */
public class UploadDialogFragment extends DialogFragment implements View.OnClickListener{
    private static final String TAG = UploadDialogFragment.class.getSimpleName();
    public static final String LOCAL_VIDEO_SELECTED ="local video selected";

    private Activity mActivity;
    private EditText mEtVideoName;
    private RadioButton mRbPublic;
    private RadioButton mRbPrivate;
    private Spinner mSpCategory;
    private EditText mEtVideoMarks;
    private Button mBtnUploadSubmit;
    private int mCategoryId;

    FooVideo mFooVideo;


    public static UploadDialogFragment getInstance(FooVideo fooVideo) {
        UploadDialogFragment fragment = new UploadDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LOCAL_VIDEO_SELECTED, fooVideo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mFooVideo = (FooVideo) getArguments().getSerializable(LOCAL_VIDEO_SELECTED);
        String videoName = mFooVideo != null ? mFooVideo.getDisplayName() : null;

        getDialog().setTitle(videoName);
        View view = inflater.inflate(R.layout.dialog_fragment_upload, container, false);
        mEtVideoName = (EditText) view.findViewById(R.id.et_upload_video_name);
        mRbPublic = (RadioButton) view.findViewById(R.id.rb_upload_video_public);
        mRbPrivate = (RadioButton) view.findViewById(R.id.rb_upload_video_private);
        mSpCategory = (Spinner) view.findViewById(R.id.spr_upload_video_category);
        mEtVideoMarks = (EditText) view.findViewById(R.id.et_upload_video_marks);
        mBtnUploadSubmit = (Button) view.findViewById(R.id.bnt_upload_submit);

        mSpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategoryId = position;
                Log.i(TAG + ": categoryId =>", mCategoryId + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBtnUploadSubmit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String videoName = mEtVideoName.getText().toString();
        String videoMarks = mEtVideoMarks.getText().toString();
        switch (v.getId()) {
            case R.id.bnt_upload_submit:
                if (TextUtils.isEmpty(videoName)) {
                    mEtVideoName.setError("不能为空!");
                    break;
                }
                if (TextUtils.isEmpty(videoMarks)) {
                    mEtVideoMarks.setError("不能为空!");
                    break;
                }
                if (mCategoryId == 0) {
                    Toast.makeText(mActivity, "请选择分类", Toast.LENGTH_SHORT).show();
                    break;
                }

                Log.i(TAG + ": categoryId =>", mCategoryId + "");
                Toast.makeText(mActivity, "正在上传...", Toast.LENGTH_SHORT).show();
                startUploadService();
                dismiss();
                break;
        }
    }

    private void startUploadService() {
        Intent intent = new Intent();
        intent.setClass(mActivity, UploadService.class);
        mActivity.startService(intent);
    }

}
