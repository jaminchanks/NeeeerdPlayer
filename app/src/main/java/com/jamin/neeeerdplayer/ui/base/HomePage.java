package com.jamin.neeeerdplayer.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.mainPage.BillboardFragment;
import com.jamin.neeeerdplayer.ui.mainPage.CategoryFragment;
import com.jamin.neeeerdplayer.ui.mainPage.CommendFragment;
import com.jamin.neeeerdplayer.ui.mainPage.DynamicFragment;

/**
 * 这个枚举类型枚举了首页的几个viewpager分页
 * 当需要更改主页的分页时，只需要更新这个枚举类并修改对应的fragment页面就可以了
 *
 * Created by jamin on 16-3-10.
 */
public enum HomePage {
    COMMEND,    //推荐
    BILLBOARD,  //排行
    CATEGORY,   //分类
    DYNAMIC;   //动态


    private static final int HomePageCount = 4;
    public static int getHomePageCount() {
        return HomePageCount;
    }

    public static HomePage getHomePageByPosition(int position) {
        switch (position) {
            case 0:
                return HomePage.COMMEND;
            case 1:
                return HomePage.BILLBOARD;
            case 2:
                return HomePage.CATEGORY;
            case 3:
                return HomePage.DYNAMIC;
            default:
                throw new RuntimeException("No such homePage");
        }
    }



    public static Fragment getFragmentByHomePosition(int position) {
        switch (position) {
            case 0:
                return new CommendFragment();
            case 1:
                return new BillboardFragment();
            case 2:
                return new CategoryFragment();
            case 3:
                return new DynamicFragment();
            default:
                throw new RuntimeException("No such homePage");
        }
    }


    public static String getHomePageTitle(Context context, HomePage homePage) {
        int stringId;
        switch (homePage) {
            case COMMEND:
                stringId = R.string.commend;
                break;
            case BILLBOARD:
                stringId = R.string.billboard;
                break;
            case CATEGORY:
                stringId = R.string.category;
                break;
            case DYNAMIC:
                stringId = R.string.dynamic;
                break;
            default:
                throw new RuntimeException("No such resource Id");
        }
        return context.getResources().getString(stringId);
    }



}
