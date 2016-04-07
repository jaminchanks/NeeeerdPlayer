package com.jamin.neeeerdplayer.ui.mainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.CategoryItem;
import com.jamin.neeeerdplayer.ui.base.Category;
import com.jamin.neeeerdplayer.ui.widget.CategoryGroup;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-9.
 */
public class CategoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        CategoryGroup categoryGroup = (CategoryGroup) view.findViewById(R.id.category_group);
        categoryGroup.loadData(getCategoryData());
    }

    public ArrayList<CategoryItem> getCategoryData() {
        ArrayList<CategoryItem> list = new ArrayList<>();

        //遍历所有视频分类的枚举类型
        for (Category category : Category.values()) {
            CategoryItem item = new CategoryItem(getActivity(), category);
            list.add(item);
        }
        return list;
    }



}
