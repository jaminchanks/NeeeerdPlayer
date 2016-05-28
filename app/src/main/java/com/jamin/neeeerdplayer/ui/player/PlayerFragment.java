package com.jamin.neeeerdplayer.ui.player;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.utils.ScreenUtils;

/**
 * Created by jamin on 16-3-6.
 */
public class PlayerFragment extends Fragment {
    public final static String TAG = PlayerFragment.class.getSimpleName();
    private final static String CURRENT_PLAY_VIDEO = "current play video";

    private VideoView mVideoView;
    private FooVideo mCurrentPlayingVideo;
    private int mVideoPlayedTime = 0;

    public static PlayerFragment newInstance(FooVideo video) {
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_PLAY_VIDEO, video);
        PlayerFragment fragment = new PlayerFragment();
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
        return inflater.inflate(R.layout.fragment_player, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        mVideoView = (VideoView)view.findViewById(R.id.video_player);
        //横屏情况下改变videoView至全屏大小
        Configuration configuration = getActivity().getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == configuration.orientation) {
            Toast.makeText(getActivity(), "横屏", Toast.LENGTH_SHORT).show();
            mVideoView.getLayoutParams().width = ScreenUtils
                    .getScreenWidth(getActivity());
            mVideoView.getLayoutParams().height = ScreenUtils
                    .getScreenHeight(getActivity()) + 20;
        }


        mCurrentPlayingVideo = (FooVideo) getArguments().getSerializable(CURRENT_PLAY_VIDEO);
        mVideoView.setVideoPath(mCurrentPlayingVideo.getVideoPath());
        mVideoView.start();

        mVideoView.setMediaController(new MediaController(getActivity()));

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




}
