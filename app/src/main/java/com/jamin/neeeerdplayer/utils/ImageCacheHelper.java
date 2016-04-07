package com.jamin.neeeerdplayer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.base.AppPathConfig;
import com.jamin.neeeerdplayer.ui.base.BaseApplication;
import com.jamin.neeeerdplayer.ui.widget.RoundBitmapDisplayer;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jamin on 16-4-7.
 */
public class ImageCacheHelper {

    private static ImageLoader sImageLoader;

    //内存缓存大小 10M
    private static final int MEMORY_CACHE_SIZE = 20 * 1024 * 1024;
    //本地缓存大小 50M
    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    //线程启动数
    private static final int THREAD_POOL_SIZE = 5;
    //缓存保存一个月
    private static final long AGE_DISK_CACHE = 30 * 24 * 3600;


    //内存size
    private static final LruMemoryCache sMemoryCache = new LruMemoryCache(MEMORY_CACHE_SIZE);

    private static LimitedAgeDiskCache sDiskCache;

    //文件路径与命名
    private static MD5FileNameGenerator sMd5FileNameGenerator = new MD5FileNameGenerator();


    public static ImageLoader getImageLoader(){
        return sImageLoader;
    }


    public static void initImageLoader(Context context) {
        File cacheDir = new File(AppPathConfig.getInstance().getImageDiskCacheDir());
        File reserveCacheDir = new File(AppPathConfig.getInstance().getImageReserveDiskCacheDir());
        sDiskCache = new LimitedAgeDiskCache(cacheDir, reserveCacheDir, sMd5FileNameGenerator, AGE_DISK_CACHE);
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        builder.threadPriority(Thread.NORM_PRIORITY);
        builder.memoryCache(sMemoryCache);
        builder.memoryCacheSize(MEMORY_CACHE_SIZE);
        builder.diskCacheSize(DISK_CACHE_SIZE);
        builder.diskCache(sDiskCache);
        builder.denyCacheImageMultipleSizesInMemory();
        builder.threadPoolSize(THREAD_POOL_SIZE);
        ImageLoaderConfiguration config = builder.build();
        sImageLoader = ImageLoader.getInstance();
        sImageLoader.init(config);
    }


    /**
     * @see #displayImage(String, ImageView, DisplayImageOptions, ImageLoadedListener)
     * */
    public static void displayImageByMediaId(String mediaId, ImageView imageView, DisplayImageOptions options){
        displayImageByMediaId(mediaId, imageView, options);
    }




    /**
     * 显示图片
     * @param uri 图片文件URI， 如http://WWW.BAIDU.COM/32443.JPG  OR file:///sdcard/qwertt.jpg
     * @param imageView 要显示图片的imageview控件
     * @param options  option控制，通过 ImageCacheHelper.getXXXImageOptions获得，不同的option使用方式不同
     */
    public static void displayImage(String uri, final ImageView imageView, final DisplayImageOptions options, final ImageLoadedListener listener) {
        uri = handleUri(uri);
        sImageLoader.displayImage(uri, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                //bitmap空的时候返回的错误
                if (FailReason.FailType.DECODING_ERROR == failReason.getType()) {
                    try {
                        int targetWidth = view.getWidth();
                        int targetHeight = view.getHeight();
                        ImageSize imageSize = new ImageSize(targetWidth, targetHeight);
                        String key = MemoryCacheUtils.generateKey(imageUri, imageSize);
                        Drawable drawable = options.getImageOnFail(BaseApplication.baseContext.getResources());
                        if (null != drawable) {
                            sImageLoader.getMemoryCache().put(key, ImageCompressUtil.drawableToBitmap(drawable));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                if (null != listener) {
                    listener.onImageLoadedFail();
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (null != listener) {
                    listener.onImageLoadedComplete(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });
    }


    /**
     * 获取人员默认头像option
     * @return
     */
    public static DisplayImageOptions getUserAvatarImageOptions() {
        return getCustomerOptions(R.mipmap.default_image);
    }



    /**
     * @see #getCustomerOptions(int, int, boolean)
     * */
    public static DisplayImageOptions getCustomerOptions(int resId) {
        return getCustomerOptions(resId, resId, false);
    }

    public static DisplayImageOptions getCustomerOptions(int resId, int loadingId, boolean isRoundBitmap) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheOnDisk(true);
        builder.cacheInMemory(true);
        if(-1 == loadingId){
            builder.showImageOnLoading(null);
        } else {
            builder.showImageOnLoading(loadingId);
        }

        if(-1 != resId){
            builder.showImageForEmptyUri(resId);
            builder.showImageOnFail(resId);
        }
        if (isRoundBitmap) {
            builder.displayer(new RoundedBitmapDisplayer(10));
        }
        return builder.build();
    }



    /**
     * 获取"我"的界面的头像, 这里不使用加载中的头像占位
     * @return
     */
    public static DisplayImageOptions getMyAcountAvatarOptions() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheOnDisk(true);
        builder.cacheInMemory(true);
        builder.showImageForEmptyUri(R.mipmap.default_image);
        builder.showImageOnLoading(null);
        builder.displayer(new RoundBitmapDisplayer());
        builder.showImageOnFail(R.mipmap.default_image);
        return builder.build();
    }





    private static class MD5FileNameGenerator implements FileNameGenerator {

        private static final String HASH_ALGORITHM = "MD5";
        private static final int RADIX = 36;

        public MD5FileNameGenerator() {
        }
        @Override
        public String generate(String imageUri) {
            int lastIndex = imageUri.lastIndexOf("=");
            String fileName = imageUri;
            if(-1 != lastIndex){
                fileName = imageUri.substring(0, lastIndex);
            }
            byte[] md5 = this.getMD5(fileName.getBytes());
            BigInteger bi = (new BigInteger(md5)).abs();
            return bi.toString(RADIX);
        }

        private byte[] getMD5(byte[] data) {
            byte[] hash = null;

            try {
                MessageDigest e = MessageDigest.getInstance(HASH_ALGORITHM);
                e.update(data);
                hash = e.digest();
            } catch (NoSuchAlgorithmException var4) {
                L.e(var4);
            }

            return hash;
        }
    }


    public static String handleUri(String uri) {
        if (TextUtils.isEmpty(uri)) {
            return uri;
        }
        if (!uri.startsWith("http") && !uri.startsWith("file:")) {
            uri = "file://" + uri;
        }
        return uri;
    }


    public interface ImageLoadedListener {
        void onImageLoadedComplete(Bitmap bitmap);
        void onImageLoadedFail();
    }
}
