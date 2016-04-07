package com.jamin.neeeerdplayer.utils;

import android.content.Context;

import com.jamin.neeeerdplayer.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by jamin on 16-4-7.
 */
public class ImageLoaderHelper {
    private static ImageLoaderHelper sInstance;

    public static ImageLoaderHelper getsInstance() {
        if (sInstance == null) {
            sInstance = new ImageLoaderHelper();
        }

        return sInstance;
    }

    public void initImageLoader(Context context) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc().build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }



}
