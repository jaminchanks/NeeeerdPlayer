package com.jamin.neeeerdplayer.ui.video_comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.Comment;
import com.jamin.neeeerdplayer.bean.CommentWithUser;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.bean.UserInfo;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jamin on 16-4-18.
 */
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommentWithUser> mComments;


    public CommentAdapter(Context context, List<CommentWithUser> comments) {
        this.mContext = context;
        this.mComments = comments;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_comment, null);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        CommentWithUser commentWithUser = mComments.get(position);
        Comment comment = commentWithUser.getComment();
        UserInfo user = commentWithUser.getCommentFrom();

        viewHolder = (MyViewHolder) convertView.getTag();
        Glide.with(mContext).load(user.getUserAvatar()).
                transform(new GlideCircleTransform(mContext)).into(viewHolder.ivAvatar);
        viewHolder.tvUserName.setText(user.getUserName());
        viewHolder.tvCommentTime.setText(comment.getComment_time());
        viewHolder.tvCommentContent.setText(comment.getContent());

        return convertView;
    }

    public class MyViewHolder{
        ImageView ivAvatar;
        TextView tvUserName;
        TextView tvCommentTime;
        TextView tvCommentContent;

        public MyViewHolder(View view) {
            this.ivAvatar = (ImageView) view.findViewById(R.id.iv_comment_user_avatar);
            this.tvUserName = (TextView) view.findViewById(R.id.tv_comment_user_user_name);
            this.tvCommentTime = (TextView) view.findViewById(R.id.tv_comment_time);
            this.tvCommentContent = (TextView) view.findViewById(R.id.tv_comment_content);
        }
    }

}
