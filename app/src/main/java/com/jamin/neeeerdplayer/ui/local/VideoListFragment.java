package com.jamin.neeeerdplayer.ui.local;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.ui.player.PlayerActivity;
import com.jamin.neeeerdplayer.ui.upload.UploadDialogFragment;
import com.jamin.neeeerdplayer.utils.VideoUtils;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-5.
 */
public class VideoListFragment extends ListFragment {
    private static final String TAG = VideoListFragment.class.getSimpleName();

    private static final String VIDEO_LIST = "video list";
    public static final String SELECTED_VIDEO = "selected Video";


    private ArrayList<FooVideo> mVideoList;
    private Activity mActivity;

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

        mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getListView().setDivider(null);
        String upperDirectorName = VideoUtils.getSimperUpperDirectorName(mVideoList.get(0).getVideoPath());
        getActivity().setTitle(upperDirectorName);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showUploadOptions(position);
                return true;
            }
        });
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


    private void showUploadOptions(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        //builder.setTitle("请选择操作");
        builder.setTitle("选择操作")
                .setItems(new CharSequence[]{"上传该视频"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                            case 1:	//删除操作
                                UploadDialogFragment dialogFragment = UploadDialogFragment.getInstance(mVideoList.get(position));
                                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                                break;
                            default:
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
