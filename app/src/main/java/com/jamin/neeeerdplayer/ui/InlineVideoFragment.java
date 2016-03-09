package com.jamin.neeeerdplayer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.FooVariant;

/**
 * Created by jamin on 16-3-9.
 */
public class InlineVideoFragment extends Fragment {
    private static final String VIDEO_CATEGORY = "video category";

    public static InlineVideoFragment newInstance(FooVariant.Category category) {
        InlineVideoFragment fragment = new InlineVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VIDEO_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inline_videos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        TextView textView = (TextView) view.findViewById(R.id.inline_video_category);
        FooVariant.Category category = (FooVariant.Category) getArguments().getSerializable(VIDEO_CATEGORY);
        textView.setText(FooVariant.getCategoryName(getActivity(), category));
    }

}
