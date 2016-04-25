package com.jamin.neeeerdplayer.ui.video_comment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.Comment;
import com.jamin.neeeerdplayer.bean.CommentWithUser;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.config.BaseNetConfig;
import com.jamin.neeeerdplayer.ui.base.HomePage;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-4-18.
 */
public class VideoWithCommentFragment extends XBaseFragment{
    public static final String ONLINE_VIDEO_SELECTED = "online video selected";
    private VideoView mVideoView;
    private VideoWithUser mCurrentPlayingVideo;
    private int mVideoId;

    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        setRetainInstance(true);    //保留fragment
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

        //设置播放的视频内容
        mCurrentPlayingVideo = (VideoWithUser) getArguments().getSerializable(ONLINE_VIDEO_SELECTED);
        mVideoId = mCurrentPlayingVideo.getVideo().getVideo_id();
        String url = mCurrentPlayingVideo.getVideo().getVideo_src();
        Uri uri = Uri.parse(url);
        mVideoView.setVideoURI(uri);
        mVideoView.setMediaController(new MediaController(getActivity()));


//        mLvComment.setAdapter(mCommentAdapter);

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
//        if (mVideoPlayedTime != 0)
//            mVideoView.seekTo(mVideoPlayedTime);
    }

    @Override
    public void onPause() {
        super.onPause();
//        mVideoPlayedTime = mVideoView.getCurrentPosition();
//        mVideoView.pause();
    }

}
