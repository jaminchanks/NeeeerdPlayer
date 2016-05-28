package com.jamin.neeeerdplayer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.bean.FooVideo;
import com.jamin.neeeerdplayer.bean.User;
import com.jamin.neeeerdplayer.ui.about.AboutActivity;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.base.HomePage;
import com.jamin.neeeerdplayer.ui.local.FolderListActivity;
import com.jamin.neeeerdplayer.ui.myUploaded.UploadedActivity;
import com.jamin.neeeerdplayer.ui.user.info.UserInfoActivity;
import com.jamin.neeeerdplayer.ui.widget.GlideCircleTransform;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment mCurrentFragment;
    private ArrayList<FooVideo> mVideoList;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ViewPager viewPager;

    private ImageView mIvAvatar;
    private TextView mTxUserName;
    private TextView mTxUserMarks;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragmentViewPager();
    }

    private void initView() {
        //浮动按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        mIvAvatar = (ImageView) headerView.findViewById(R.id.user_avatar_nav);
        mTxUserName = (TextView) headerView.findViewById(R.id.user_name_nav);
        mTxUserMarks = (TextView) headerView.findViewById(R.id.user_marks_nav);

        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        showUserInfo();

    }


    private void showUserInfo() {
        mUser = ((BaseApplication) getApplication()).getUser();
        String avatarUri = mUser.getAvatar();
        Log.i("user_head",avatarUri );

        //// TODO: 16-4-7 仅是测试, 获取头像
        Glide.with(this).load(avatarUri).transform(new GlideCircleTransform(this))
                .into(mIvAvatar);
        mTxUserName.setText(mUser.getUserName());
        mTxUserMarks.setText(mUser.getMarks());
    }




    //// TODO: 16-3-8 暂作测试 viewpager设置
    /**
     * viewpager的设置为 "首页推荐" + 其他视频分类fragment
     */
    private void initFragmentViewPager() {

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                //根据视频分类名生成指定的在线视频fragment
                return HomePage.getFragmentByHomePosition(position);
            }

            @Override
            public int getCount() {
                return HomePage.getHomePageCount();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                HomePage  homePage = HomePage.getHomePageByPosition(position);
                return HomePage.getHomePageTitle(MainActivity.this, homePage);
            }
        });

        //设置缓存页数
        viewPager.setOffscreenPageLimit(HomePage.getHomePageCount());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent();
        Class<?> clazz = null;
        switch (id) {
            case R.id.nav_online_video:
//                clazz = MainActivity.class;
                break;
            case R.id.nav_offline_video:
                clazz = FolderListActivity.class;
                break;
            case R.id.nav_upload_video:
                clazz = UploadedActivity.class;
                break;
//            case R.id.nav_message:
//
//                break;
//            case R.id.nav_setting:
//
//                break;

            case R.id.nav_about_us:
                clazz = AboutActivity.class;
                break;

            default:
                break;
        }

        //// TODO: 16-3-7 当fragment中有线程未被销毁时，替换fragment可能会有bug
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mCurrentFragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        if (clazz != null) {
            intent.setClass(this, clazz);
            startActivity(intent);
            return true;
        }
        return true;
    }


    @Override
    public void onNewIntent(Intent intent) {
//        Toast.makeText(this, "ok!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        viewPager.setCurrentItem(0);
        MenuItem menuItem= navigationView.getMenu().findItem(R.id.nav_online_video);
        menuItem.setChecked(true);
        mUser = ((BaseApplication)getApplication()).getUser();
        showUserInfo();
    }

}
