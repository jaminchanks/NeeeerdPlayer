package com.jamin.neeeerdplayer.ui.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.CategoryItem;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-10.
 */
public class CategoryViewGroup extends FrameLayout {
    private Context mContext;
    private RecyclerView mRecyclerView;
    public CategoryViewGroup(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public CategoryViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.component_common_recycleview, this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
    }

    public void loadData(ArrayList<CategoryItem> categoryItems) {
        mRecyclerView.setAdapter(new MyRecycleAdapter(categoryItems));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));

    }



    public class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private ArrayList<CategoryItem> mCategories;

        public MyRecycleAdapter(ArrayList<CategoryItem> categories) {
            this.mCategories = categories;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.component_category_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CategoryItem item = mCategories.get(position);
            holder.image.setImageBitmap(item.getIcon());
            holder.title.setText(item.getTitle());
            //// TODO: 16-3-10  稍后补充点击事件
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }



    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            title = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }


}
