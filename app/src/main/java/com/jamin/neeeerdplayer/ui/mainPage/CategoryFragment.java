package com.jamin.neeeerdplayer.ui.mainPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.CategoryItem;
import com.jamin.neeeerdplayer.ui.base.Category;
import com.jamin.neeeerdplayer.ui.category.VideoInCategoryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamin on 16-3-9.
 */
public class CategoryFragment extends Fragment {
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private List<CategoryItem> mCategoryItems = new ArrayList<>();
    private MyRecycleAdapter mAdapter;

    @Override
    public void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_category, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_category);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        mCategoryItems = getAllCategories();
        mAdapter = new MyRecycleAdapter(mCategoryItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<CategoryItem> getAllCategories() {
        List<CategoryItem> list = new ArrayList<>();

        //遍历所有视频分类的枚举类型
        for (Category category : Category.values()) {
            CategoryItem item = new CategoryItem(getActivity(), category);
            list.add(item);
        }
        return list;
    }




    class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<CategoryItem> mCategories;

        public MyRecycleAdapter(List<CategoryItem> categories) {
            this.mCategories = categories;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.component_category_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            CategoryItem item = mCategories.get(position);
            holder.image.setImageBitmap(item.getIcon());
            holder.title.setText(item.getTitle());
            holder.lyCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Category category = mCategories.get(position).getCategory();
                    intent.putExtra(VideoInCategoryActivity.CATEGORY_ID, category.toInt());
                    intent.setClass(mActivity, VideoInCategoryActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }



    private class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lyCategory;
        ImageView image;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            lyCategory = (LinearLayout) itemView.findViewById(R.id.ly_category_item);
            image = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            title = (TextView) itemView.findViewById(R.id.tv_category_name);
        }
    }


}
