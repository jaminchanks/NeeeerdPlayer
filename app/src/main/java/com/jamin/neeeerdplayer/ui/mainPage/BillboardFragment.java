package com.jamin.neeeerdplayer.ui.mainPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.BillboardItem;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-9.
 */
public class BillboardFragment extends Fragment {

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_billboard, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        initTestData(view);
    }


    private void initTestData(View view) {
        BillboardItem item1 = new BillboardItem("动漫", "123", "123", "123");
        BillboardItem item2 = new BillboardItem("科技", "123", "123", "123");
        BillboardItem item3 = new BillboardItem("娱乐", "123", "123", "123");
        ArrayList<BillboardItem> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        ListView listView = (ListView) view.findViewById(R.id.list_view_billboard);
        listView.setAdapter(new BillBoardAdapter(getActivity(), items));
    }


}
