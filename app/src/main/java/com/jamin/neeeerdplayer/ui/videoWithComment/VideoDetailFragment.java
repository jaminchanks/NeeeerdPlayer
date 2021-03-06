package com.jamin.neeeerdplayer.ui.videoWithComment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.OnlineVideo;
import com.jamin.neeeerdplayer.bean.UserInfo;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.user.friends.FriendInfoActivity;
import com.jamin.neeeerdplayer.ui.user.info.UserInfoActivity;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

import org.xutils.x;

/**
 * Created by jamin on 16-4-19.
 */
public class VideoDetailFragment extends Fragment {
    private static final String TAG = CommentListFragment.class.getSimpleName();

    private static final String ONLINE_VIDEO = "online video id";

    public static VideoDetailFragment newInstance(VideoWithUser videoWithUser) {
        Bundle args = new Bundle();
        args.putSerializable(ONLINE_VIDEO, videoWithUser);
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_comment_detail, container, false);
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView videoName = (TextView) view.findViewById(R.id.tv_video_comment_title);
        TextView videoIntroduc = (TextView) view.findViewById(R.id.tv_video_comment_introduction);
        TextView videoUploadTime = (TextView) view.findViewById(R.id.tv_video_comment_upload_time);
        ImageView avatar = (ImageView) view.findViewById(R.id.iv_video_comment_avatar);
        TextView userName = (TextView) view.findViewById(R.id.tv_video_comment_user_name);

        VideoWithUser videoWithUser = (VideoWithUser) getArguments().getSerializable(ONLINE_VIDEO);
        OnlineVideo video = videoWithUser.getVideo();
        final UserInfo user = videoWithUser.getUserInfo();
        videoName.setText(video.getVideo_name());
        videoIntroduc.setText(video.getVideo_introduction() == null ? "没有简介~" : video.getVideo_introduction());
        videoUploadTime.setText(video.getUp_time());
        Glide.with(getActivity()).load(user.getUserAvatar()).
                transform(new GlideCircleTransform(getActivity()))
                .into(avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //点击的是自己的头像
                if (user.getUserId() == ((BaseApplication)x.app()).getUser().getId()) {
                    intent.setClass(getActivity(), UserInfoActivity.class);
                } else {
                    intent.putExtra(FriendInfoActivity.USER_INFO_EXTRA, user);
                    intent.setClass(getActivity(), FriendInfoActivity.class);
                }
                startActivity(intent);
            }
        });


        userName.setText(user.getUserName());
    }

}
