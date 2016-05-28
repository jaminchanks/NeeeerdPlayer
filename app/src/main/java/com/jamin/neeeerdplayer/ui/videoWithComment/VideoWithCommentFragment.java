package com.jamin.neeeerdplayer.ui.videoWithComment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by jamin on 16-4-18.
 */
public class VideoWithCommentFragment extends XBaseFragment {
    public static final String ONLINE_VIDEO_SELECTED = "online video selected";
    private VideoView mVideoView;
    private VideoWithUser mCurrentPlayingVideo;
    private int mVideoId;
    private int mVideoPlayedTime = 0;
    private static boolean isCommentLayoutShow = false;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout addCommentLayout;
    private EditText mEtAddComment;
    private Button mBtSendComment;

    private User mUser;


    public static VideoWithCommentFragment newInstance(VideoWithUser video) {
        Bundle args = new Bundle();
        args.putSerializable(ONLINE_VIDEO_SELECTED, video);
        VideoWithCommentFragment fragment = new VideoWithCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);    //保留fragment

        mUser = ((BaseApplication) x.app()).getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_with_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        mVideoView = (VideoView)view.findViewById(R.id.vv_online_video_to_show);
//        mLvComment = (ListView) view.findViewById(R.id.lv_video_comment);
        //横屏情况下改变videoView至全屏大小
//        Configuration configuration = getActivity().getResources().getConfiguration();
//        if (Configuration.ORIENTATION_LANDSCAPE == configuration.orientation) {
//            Toast.makeText(getActivity(), "横屏", Toast.LENGTH_SHORT).show();
//            mVideoView.getLayoutParams().width = ScreenUtils.getScreenWidth(getActivity());
//            mVideoView.getLayoutParams().height = ScreenUtils.getScreenHeight(getActivity()) + 20;
//        }

        //tablyout和viewPager
        tabLayout = (TabLayout) view.findViewById(R.id.tl_comment_video);
        viewPager = (ViewPager) view.findViewById(R.id.vp_comment_video);
        addCommentLayout = (LinearLayout) view.findViewById(R.id.ly_add_comment);
        mEtAddComment = (EditText) view.findViewById(R.id.et_add_comment);
        mBtSendComment = (Button) addCommentLayout.findViewById(R.id.bt_send_comment);

        mBtSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                requestAddComment();
            }
        });

        //设置播放的视频内容
        mCurrentPlayingVideo = (VideoWithUser) getArguments().getSerializable(ONLINE_VIDEO_SELECTED);
        mVideoId = mCurrentPlayingVideo.getVideo().getVideo_id();
        String url = mCurrentPlayingVideo.getVideo().getVideo_src();
        Uri uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);

        MediaController mc = new MediaController(getActivity());
        mVideoView.setMediaController(mc);
        mc.setVisibility(View.VISIBLE);

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return VideoDetailFragment.newInstance(mCurrentPlayingVideo);
                } else {
                    //返回评论列表
                    return CommentListFragment.newInstance(mVideoId);
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "详情";
                } else {
                    return "评论";
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mVideoPlayedTime != 0)
            mVideoView.seekTo(mVideoPlayedTime);
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoPlayedTime = mVideoView.getCurrentPosition();
        mVideoView.pause();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_online_video, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_like:

                break;

            case R.id.action_comment:
                if (!isCommentLayoutShow) {
                    mEtAddComment.setText("");
                    addCommentLayout.setVisibility(View.VISIBLE);
                    isCommentLayoutShow = true;
                } else {
                    addCommentLayout.setVisibility(View.GONE);
                    isCommentLayoutShow = false;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestAddComment() {
        String content = mEtAddComment.getText().toString();
        if (TextUtils.isEmpty(content)) {
            mEtAddComment.setError("无内容");
            return;
        }

        RequestParams params = new RequestParams(BaseNetConfig.WEB_URL + "/comment/add");
        params.addParameter("userId", mUser.getId());
        params.addParameter("videoId", mCurrentPlayingVideo.getVideo().getVideo_id());
        params.addParameter("content", content);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                addCommentLayout.setVisibility(View.GONE);
                isCommentLayoutShow = false;
                Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();
                //通知评论列表更新
                Intent intent = new Intent();
                intent.setAction(CommentListFragment.REFRESH_COMMENT_DATA);
                getActivity().sendBroadcast(intent);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
