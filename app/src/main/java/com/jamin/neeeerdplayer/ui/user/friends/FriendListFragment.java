package com.jamin.neeeerdplayer.ui.user.friends;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.UserInfo;
import com.jamin.neeeerdplayer.bean.UserRelationship;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.XBaseFragment;

import org.xutils.x;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-5-2.
 */
public class FriendListFragment extends XBaseFragment{
    private static final String RELATIONSHIP_TYPE = "relationship_type";

    private Activity mActivity;
    private List<UserInfo> mUserInfos = new ArrayList<>();
    private FriendListAdapter mAdapter;
    private ListView mListview;
    private UserRelationship mUserRelationship;
    private int mRelationshipType;

    public static FriendListFragment getInstance(int relationshipType) {
        Bundle bundle = new Bundle();
        bundle.putInt(RELATIONSHIP_TYPE, relationshipType);
        FriendListFragment fragment = new FriendListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mUserRelationship = ((BaseApplication) x.app()).getUserRelationship();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, null);
        mListview = (ListView) view.findViewById(R.id.lv_friends);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //判断是关注还是被关注列表
        mRelationshipType = getArguments().getInt(RELATIONSHIP_TYPE);
        if (mRelationshipType == FriendListActivity.FOLLOW_TYPE) {
            mUserInfos.clear();
            mUserInfos.addAll(mUserRelationship.getFollowUsers());
        } else if (mRelationshipType == FriendListActivity.BE_FOLLOWED_TYPE) {
            mUserInfos.clear();
            mUserInfos.addAll(mUserRelationship.getBeFollowedUsers());
        }

        Log.i("friend", mUserInfos.size() + "");
        mAdapter = new FriendListAdapter(mActivity, mUserInfos);
        mListview.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mUserRelationship = ((BaseApplication) x.app()).getUserRelationship();
        if (mRelationshipType == FriendListActivity.FOLLOW_TYPE) {
            mUserInfos.clear();
            mUserInfos.addAll(mUserRelationship.getFollowUsers());
        } else if (mRelationshipType == FriendListActivity.BE_FOLLOWED_TYPE) {
            mUserInfos.clear();
            mUserInfos.addAll(mUserRelationship.getBeFollowedUsers());
        }
        mAdapter.notifyDataSetChanged();
    }


}
