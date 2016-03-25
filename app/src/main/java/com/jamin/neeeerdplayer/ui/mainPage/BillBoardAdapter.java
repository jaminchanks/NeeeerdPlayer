package com.jamin.neeeerdplayer.ui.mainPage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.BillboardItem;
import com.jamin.neeeerdplayer.ui.base.Category;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-21.
 */
public class BillBoardAdapter extends BaseAdapter {
    ArrayList<BillboardItem> mBillboardList;
    Context mContext;

    public BillBoardAdapter(Context context, ArrayList<BillboardItem> list) {
        mContext = context;
        mBillboardList = list;
    }

    @Override
    public int getCount() {
        return mBillboardList.size();
    }

    @Override
    public BillboardItem getItem(int position) {
        return mBillboardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.component_billboard, null);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        viewHolder = (MyViewHolder) convertView.getTag();

        BillboardItem item = getItem(position);
        viewHolder.categoryName.setText(item.getCategoryName());

        int iconResId = Category.parseCategory(mContext, item.getCategoryName()).getCategoryIconResId();
        Bitmap categoryIcon = BitmapFactory.decodeResource(mContext.getResources(), iconResId);
        viewHolder.categoryIcon.setImageBitmap(categoryIcon);
        viewHolder.no1.setText(item.getNo1());
        viewHolder.no2.setText(item.getNo2());
        viewHolder.no3.setText(item.getNo3());

        return convertView;
    }


    private class MyViewHolder {
        TextView categoryName;
        ImageView categoryIcon;
        TextView no1;
        TextView no2;
        TextView no3;

        public MyViewHolder(View view) {
            categoryName = (TextView) view.findViewById(R.id.tv_video_online_category);
            categoryIcon = (ImageView) view.findViewById(R.id.iv_billboard_category_icon);
            no1 = (TextView) view.findViewById(R.id.tv_billboard_no1);
            no2 = (TextView) view.findViewById(R.id.tv_billboard_no2);
            no3 = (TextView) view.findViewById(R.id.tv_billboard_no3);
        }


    }


}
