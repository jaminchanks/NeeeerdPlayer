package com.jamin.neeeerdplayer.ui.local;



import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;

import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;


import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooFolder;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.utils.FooUtils;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-6.
 */
public class FolderListFragment extends ListFragment {
    private static final String TAG = FolderListFragment.class.getSimpleName();
    private static final String FOLDER_LIST = "folder list";
    public static final String SELECTED_FOLDER = "selected folder";

    private ArrayList<FooFolder> mFolderList;
    private SearchView mSearchView;
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

        mFolderList = (ArrayList<FooFolder>) getArguments().getSerializable(FOLDER_LIST);

        setListAdapter(new FolderAdapter(getActivity(), mFolderList));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = getListView();
        listView.setDivider(null);
        getActivity().setTitle(getActivity().getResources().getString(R.string.local_video));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FooFolder folder = (FooFolder) getListAdapter().getItem(position);

        Intent intent = new Intent();
        intent.putExtra(SELECTED_FOLDER, folder);
        intent.setClass(getActivity(), VideoListActivity.class);
        startActivity(intent);
        //界面切换动画
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
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

        mSearchView = (SearchView)searchItem.getActionView();
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        ComponentName name = getActivity().getComponentName();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(name);

        mSearchView.setSearchableInfo(searchableInfo);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mSearchView != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(
                                mSearchView.getWindowToken(), 0);
                    }
                    mSearchView.clearFocus();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        switch (id) {
//            case android.R.id.home:
//                if (NavUtils.getParentActivityName(getActivity()) != null) {
//                    NavUtils.navigateUpFromSameTask(getActivity());
//                }
//                return true;
//            case R.id.action_refresh:
//                return true;
//            case R.id.action_search:
//                getActivity().onSearchRequested();
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
