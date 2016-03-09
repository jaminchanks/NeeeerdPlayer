package com.jamin.neeeerdplayer.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.mainPage.BillboardFragment;
import com.jamin.neeeerdplayer.ui.mainPage.CategoryFragment;
import com.jamin.neeeerdplayer.ui.mainPage.CommendFragment;
import com.jamin.neeeerdplayer.ui.mainPage.DynamicFragment;

/**
 * Created by jamin on 16-3-9.
 */
public class FooVariant {
    public  enum  Category {
        TECHNOLOGY,
        COMIC,
        LITERATURE
    }

    public static int getCategoryCount() {
        return 3;
    }

    public static String getCategoryName(Context context, Category category) {
        int stringId;
        switch (category) {
            case TECHNOLOGY:
                stringId = R.string.technology;
                break;
            case COMIC:
                stringId = R.string.comic;
                break;
            case LITERATURE:
                stringId = R.string.literature;
                break;
            default:
                throw new RuntimeException("No such resource Id");
        }
        return context.getResources().getString(stringId);
    }

    public static Category getCategoryByInt(int position) {
        switch (position) {
            case 0:
                return Category.TECHNOLOGY;
            case 1:
                return Category.COMIC;
            case 2:
                return Category.LITERATURE;
            default:
                return Category.TECHNOLOGY;
        }
    }



    public enum HomePage {
        COMMEND,    //推荐
        BILLBOARD,  //排行
        CATEGORY,   //分类
        DYNAMIC,   //动态
    }

    public static int getHomePageCount() {
        return 4;
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
