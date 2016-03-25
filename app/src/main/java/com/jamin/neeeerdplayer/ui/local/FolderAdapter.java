package com.jamin.neeeerdplayer.ui.local;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooFolder;

import java.util.ArrayList;

/**
 * Created by jamin on 16-3-7.
 */
public class FolderAdapter extends ArrayAdapter<FooFolder> {

    private Activity mActivity;
    private ArrayList<FooFolder> mFolders;

    public FolderAdapter(Activity activity, ArrayList<FooFolder> folders) {
        super(activity, 0, folders);
        mFolders = folders;
        mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater().inflate(R.layout.list_item_folder, null);
            viewHolder = new MyViewHolder();
//            viewHolder.folderIcon = (ImageView) convertView.findViewById(R.id.folder_icon);
            viewHolder.folderNameView = (TextView) convertView.findViewById(R.id.tv_folder_name);
            viewHolder.videoCountView = (TextView) convertView.findViewById(R.id.tv_video_count);
            viewHolder.hasNewViewTip = (TextView) convertView.findViewById(R.id.tv_has_new_video);
            convertView.setTag(viewHolder);
        }
        viewHolder = (MyViewHolder) convertView.getTag();
        FooFolder folder = getItem(position);
        viewHolder.folderNameView.setText(folder.getFolderSimpleName());
        viewHolder.videoCountView.setText(mActivity.getResources().getString(R.string.video_count,
                String.valueOf(folder.getFileCount())));

        if (position == 1) {
            viewHolder.hasNewViewTip.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    @Override
    public FooFolder getItem(int position) {
        return mFolders.get(position);
    }


    class MyViewHolder {
        ImageView folderIcon;
        TextView folderNameView;
        TextView videoCountView;
        TextView hasNewViewTip;
    }

}
