package com.jamin.neeeerdplayer.ui.base;

import android.content.Context;

import com.jamin.neeeerdplayer.R;

/**
 *
 * 这个枚举类型包含了所有视频类型
 * 当有新类型添加时注意更新这个类
 *
 * Created by jamin on 16-3-10.
 */
public enum Category {
    TECHNOLOGY, //科技
    COMIC,  //动漫
    LITERATURE, //文学
    MUSIC,  //音乐
    DANCING,    //舞蹈
    EXERCISE,   //运动
    FASHION,    //时尚
    FUNNY,  //娱乐
    EMOTION;   //情感

    private static final int CATEGORY_COUNT = 9;

    public static int getCategoryCount() {
        return CATEGORY_COUNT;
    }

    /**
     * 根据字符串返回类型
     * @param context
     * @param str
     * @return
     */
    public static Category parseCategory(Context context, String str) {
        for (Category category : Category.values()) {
            if (str.equals(category.toString(context)))
                return category;
        }
        return null;
    }


    public String toString(Context context) {
        int stringId;
        switch (this) {
            case TECHNOLOGY:
                stringId = R.string.technology;
                break;
            case COMIC:
                stringId = R.string.comic;
                break;
            case LITERATURE:
                stringId = R.string.literature;
                break;
            case MUSIC:
                stringId = R.string.music;
                break;
            case DANCING:
                stringId = R.string.dancing;
                break;
            case EXERCISE:
                stringId = R.string.exercise;
                break;
            case FASHION:
                stringId = R.string.fashion;
                break;
            case FUNNY:
                stringId = R.string.funny;
                break;
            case EMOTION:
                stringId = R.string.emotion;
                break;
            default:
                throw new RuntimeException("No such resource Id");
        }
        return context.getResources().getString(stringId);
    }


    public int getCategoryIconResId() {
        switch (this) {
            case TECHNOLOGY:
                return R.mipmap.category_technology_52;
            case COMIC:
                return R.mipmap.category_comic_52;
            case LITERATURE:
                return R.mipmap.category_literature_52;
            case MUSIC:
                return R.mipmap.category_music_52;
            case FASHION:
                return R.mipmap.category_fashion_52;
            case FUNNY:
                return R.mipmap.category_funny_48;
            case EMOTION:
                return R.mipmap.category_emotion_52;
            case EXERCISE:
                return R.mipmap.category_exercise_52;
            case DANCING:
                return R.mipmap.category_dancing_52;
            default:
                throw new RuntimeException("No such Category");
        }
    }

}
