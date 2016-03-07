package com.jamin.neeeerdplayer.ui.local;



import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooFolder;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.ui.adapter.FolderAdapter;
import com.jamin.neeeerdplayer.utils.FooUtils;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-6.
 */
public class FolderListFragment extends ListFragment {
    private static final String TAG = FolderListFragment.class.getSimpleName();
    private static final String FOLDER_LIST = "folder list";
    public static final String SELECTED_FOLDER = "selected folder";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<FooFolder> mFolderlist;

    public static FolderListFragment newInstance(ArrayList<FooVideo> fooVideos) {
        Bundle args = new Bundle();
        ArrayList<FooFolder> fooFolders = FooUtils.videoCategoryByFolder(fooVideos);
        args.putSerializable(FOLDER_LIST, fooFolders);
        FolderListFragment fragment = new FolderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);
        setHasOptionsMenu(true);

        mFolderlist = (ArrayList<FooFolder>) getArguments().getSerializable(FOLDER_LIST);

        setListAdapter(new FolderAdapter(getActivity(), mFolderlist));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setDivider(null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        swipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        swipeRefreshLayout.addView(view);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing())
                            swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        return swipeRefreshLayout;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_refresh).setVisible(true);
        //设置actionbar的搜索按钮
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(true).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = (SearchView)searchItem.getActionView() ;
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName name = getActivity().getComponentName();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(name);

        searchView.setSearchableInfo(searchableInfo);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                return true;
            case R.id.action_search:
                getActivity().onSearchRequested();
                return true;

            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}