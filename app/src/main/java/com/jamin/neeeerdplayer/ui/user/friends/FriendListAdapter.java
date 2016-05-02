package com.jamin.neeeerdplayer.ui.user.friends;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.UserInfo;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

import java.util.List;

/**
 * Created by jamin on 16-5-2.
 */
public class FriendListAdapter extends BaseAdapter{
    private Context mContext;
    private List<UserInfo> mUserInfos;

    public FriendListAdapter(Context context, List<UserInfo> userInfos) {
        this.mContext = context;
        this.mUserInfos = userInfos;
    }


    @Override
    public int getCount() {
        return mUserInfos.size();
    }

    @Override
    public UserInfo getItem(int position) {
        return mUserInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_friend, parent, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (MyViewHolder) convertView.getTag();
        final UserInfo userInfo = getItem(position);
        Glide.with(mContext).load(userInfo.getUserAvatar()).
                transform(new GlideCircleTransform(mContext)).into(viewHolder.avatar);
        viewHolder.userName.setText(userInfo.getUserName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(FriendInfoActivity.USER_INFO_EXTRA, userInfo);
                intent.setClass(mContext, FriendInfoActivity.class);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }


    class MyViewHolder {
        ImageView avatar;
        TextView userName;

        public MyViewHolder(View view) {
            this.avatar = (ImageView) view.findViewById(R.id.item_user_avatar);
            this.userName = (TextView) view.findViewById(R.id.item_user_user_name);
        }

    }
}
