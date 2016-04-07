package com.jamin.neeeerdplayer.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.utils.DensityUtils;
import com.jamin.neeeerdplayer.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * 根据传入的在线视频数而自动增加表格的行列视图
 *
 * Created by jamin on 16-3-11.
 */
public class OnlineVideoGroup extends FrameLayout{
    private Context mContext;
    private View mVideoGroup;
    private static final int VIDEO_VIEW_SPACE_DP = 10; //两个videoView之间的间隔



    public OnlineVideoGroup(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public OnlineVideoGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        mVideoGroup = LayoutInflater.from(context).inflate(R.layout.component_video_online_group, this);
    }

    public void loadData(ArrayList<FooVideo> videos) {
//        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
//                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
//
//        TableLayout.LayoutParams tableParam = new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.MATCH_PARENT);


        //两个videoView之间的间隔



        int videoViewSpacePx = DensityUtils.dip2px(mContext, VIDEO_VIEW_SPACE_DP);

        int screenWith = ScreenUtils.getScreenWidth(mContext);
        int videoViewWidth = (screenWith -
                (mContext.getResources().getDimensionPixelSize(R.dimen.activity_default_margin)*2)
                - DensityUtils.dip2px(mContext, videoViewSpacePx))/2;

        //类型标题
        TextView tv = (TextView) mVideoGroup.findViewById(R.id.tv_video_online_category);
        tv.setText(videos.get(0).getCategory().toString(mContext));


//        TableLayout tableLayout = (TableLayout) mVideoGroup.findViewById(R.id.table_video_online);
//
//        TableRow tableRow = null;
//        for (int i = 0; i < videos.size(); i++) {
//            //// TODO: 16-3-12 此处有待优化
//            OnlineVideoView videoView = new OnlineVideoView(mContext, videos.get(i));
//
//            if (i % 2 == 0) {
//                tableRow = new TableRow(mContext);
//                videoView.setPadding(0, 0, videoViewSpacePx / 2, 0);
//            } else {
//
//                videoView.setPadding(videoViewSpacePx / 2, 0, 0, 0);
//            }
//
//
//            assert tableRow != null;
//            tableRow.addView(videoView, videoViewWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            if (i % 2 == 1 || i == videos.size()) {
//                tableLayout.addView(tableRow);
//            }
//        }



//
//        TableRow row = new TableRow(mContext);
//        TextView textView = new TextView(mContext);
//        SingleOnlineVideoView tv2 = new SingleOnlineVideoView(mContext);
//        SingleOnlineVideoView tv3 = new SingleOnlineVideoView(mContext);
//        row.addView(tv2, params);
//        row.addView(tv3, params);
//        tableLayout.addView(row);
    }

}
