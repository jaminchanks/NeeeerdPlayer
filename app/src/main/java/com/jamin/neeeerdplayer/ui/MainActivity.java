package com.jamin.neeeerdplayer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.bean.VideoLab;
import com.jamin.neeeerdplayer.ui.local.FolderListFragment;
import com.jamin.neeeerdplayer.ui.local.VideoListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment mCurrentFragment;
    private ArrayList<FooVideo> mVideoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        FragmentManager fm = getSupportFragmentManager();
        mCurrentFragment = fm.findFragmentById(R.id.fragmentContainer);
        if (null == mCurrentFragment) {
            //// TODO: 16-3-6 改变获取视频的代码位置
            //获取所有本地视频的列表fragment
            mCurrentFragment = FolderListFragment.newInstance(mVideoList);
            fm.beginTransaction().add(R.id.fragmentContainer, mCurrentFragment).commit();
        }
//        if (savedInstanceState != null) {
//            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mCurrentFragment");
//        } else {
//            mCurrentFragment = new VideoListFragment();
//        }


        initView();

    }

    private void initData() {
        mVideoList = VideoLab.getInstance(this).getAllVideos();
    }


    private void initView() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * 菜单栏的设置全部交给子fragment去处理
     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
////        menu.findItem(R.id.action_refresh).setVisible(false);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        switch (id) {
////            case R.id.action_refresh:
////
////                return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_online_video) {
            // Handle the camera action
        } else if (id == R.id.nav_offline_video) {
            //todo 应该将VideoFragment至于另一个activity中
//            mCurrentFragment = VideoListFragment.newInstance(mVideoList);

        } else if (id == R.id.nav_upload_video) {
            //// TODO: 16-3-7  此处仅作测试
            mCurrentFragment = FolderListFragment.newInstance(mVideoList);

        } else if (id == R.id.nav_message) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about_us) {

        }

        //// TODO: 16-3-7 当fragment中有线程未被销毁时，替换fragment会有bug
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mCurrentFragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onNewIntent(Intent intent) {
        Toast.makeText(this, "ok!", Toast.LENGTH_LONG).show();
    }

}
