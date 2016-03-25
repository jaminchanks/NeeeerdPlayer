package com.jamin.neeeerdplayer.ui.local;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.ui.player.PlayerActivity;
import com.jamin.neeeerdplayer.utils.FooUtils;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-5.
 */
public class VideoListFragment extends ListFragment {
    private static final String TAG = VideoListFragment.class.getSimpleName();

    private static final String VIDEO_LIST = "video list";
    public static final String SELECTED_VIDEO = "selected Video";


    ArrayList<FooVideo> mVideoList;

    public static VideoListFragment newInstance(ArrayList<FooVideo> list) {
        Bundle args = new Bundle();
        args.putSerializable(VIDEO_LIST, list);
        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);
        mVideoList = (ArrayList<FooVideo>) getArguments().getSerializable(VIDEO_LIST);
        setListAdapter(new VideoAdapter(getActivity(), mVideoList));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setDivider(null);

        String upperDirectorName = FooUtils.getSimperUpperDirectorName(mVideoList.get(0).getVideoPath());
        getActivity().setTitle(upperDirectorName);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FooVideo video = (FooVideo) getListAdapter().getItem(position);
//        Toast.makeText(getActivity(), video.getDisplayName(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent();
        intent.putExtra(SELECTED_VIDEO, video);
        intent.setClass(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_refresh).setVisible(true);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.action_refresh:
//
//                return true;
//            case R.id.action_search:
//                return true;
//
//            case R.id.action_settings:
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


}
