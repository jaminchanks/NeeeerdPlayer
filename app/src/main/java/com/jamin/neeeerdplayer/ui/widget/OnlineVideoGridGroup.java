package com.jamin.neeeerdplayer.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.VideoWithUser;
import com.jamin.neeeerdplayer.ui.base.Category;
import com.jamin.neeeerdplayer.ui.mainPage.OnlineVideoGridAdapter;
import com.jamin.neeeerdplayer.ui.videoWithComment.VideoWithCommentActivity;

import java.util.List;

/**
 * Created by jamin on 16-3-12.
 */
public class OnlineVideoGridGroup extends FrameLayout{

    public static final String VIDEO_WITH_COMMENT_SELECTED = "video selsected";


    private Context mContext;
    private View mVideoGroup;
    private static final int VIDEO_VIEW_SPACE_DP = 10; //两个videoView之间的间隔

    public OnlineVideoGridGroup(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public OnlineVideoGridGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
         mVideoGroup = LayoutInflater.from(context).inflate(R.layout.component_video_online_group, this);
    }

     public void loadData(final List<VideoWithUser> videos) {
         //改变分类的名字
         TextView tvCategory = (TextView) findViewById(R.id.tv_video_online_category);
         if (videos != null && videos.size() != 0) {
             int categoryIndex = videos.get(0).getVideo().getCategory_id();
             tvCategory.setText(Category.findCategoryByIndex(categoryIndex).toString(mContext));
         }

         GridView gridView = (GridView) findViewById(R.id.grid_video_online);
         gridView.setAdapter(new OnlineVideoGridAdapter(mContext, videos));

         //视频项点击事件
         gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent();
                 intent.putExtra(VideoWithCommentActivity.VIDEO_WITH_COMMENT_SELECTED, videos.get(position));
                 intent.setClass(mContext, VideoWithCommentActivity.class);
                 mContext.startActivity(intent);
             }
         });

     }




}
