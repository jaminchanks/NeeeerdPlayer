package com.jamin.neeeerdplayer.ui.base;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.jamin.neeeerdplayer.utils.LocalFileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jamin on 16-3-9.
 */
public class BasePathConfig {

    private static final String TAG = BasePathConfig.class.getSimpleName();

    private static String APP_DIR;  //完整路径名
    private static String APP_FOLDER = File.separator + "NeeerdPlayer" + File.separator; //路径名
    private static String CACHE_DIR;


    private static BasePathConfig sInstance;

    public static BasePathConfig getInstance() {
        synchronized (TAG) {
            if (sInstance == null) {
                sInstance = new BasePathConfig();
            }
            if (TextUtils.isEmpty(APP_DIR)) {
                boolean sdCardExist = Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED);
                if (sdCardExist) {
                    APP_DIR = Environment.getExternalStorageDirectory() + APP_FOLDER;
                } else {
                    String path = getInternalPath();
                    if (!TextUtils.isEmpty(path)) {
                        APP_DIR = path + APP_FOLDER;
                    } else {
                        APP_DIR = CACHE_DIR;
                    }
                }
            }
            return sInstance;
        }
    }

    /**
     * 获取内部存储的地址, 不同手机会使用不同名称的目录, 此处是先获得挂载点然后进行判断筛选
     * */
    private static String getInternalPath(){
        //所有挂载的地址
        ArrayList<String> mountList = getDevMountList();
        String internalPath = "";
        for(String mountPth : mountList){
            File dictory = new File(mountPth);
            if(dictory.isDirectory()&& dictory.canWrite()
                    && dictory.canRead() && dictory.canExecute()){
                internalPath = dictory.getAbsolutePath();
                break;
            }
        }

        return internalPath;
    }

    /**
     * 遍历/etc/vold.fstab 获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        byte[] contentByte = LocalFileUtil.readFile("/etc/vold.fstab");
        String[] toSearch = new String(contentByte).split(" ");
        ArrayList<String> out = new ArrayList<>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * ImageLoader图片缓存路径
     * */
    public String getImageDiskCacheDir(){
        return getFileMkdirPath(APP_DIR + "DISK_CACHE");
    }

    public String getImageReserveDiskCacheDir(){
        return getFileMkdirPath(APP_DIR + "DISK_CACHE" + File.separator + "reserve_disk_cache");
    }


    private String getFileMkdirPath(String path){
        File file=new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return  path;
    }


    public void initAppFilePath(Context context) {
        CACHE_DIR = context.getCacheDir() + APP_FOLDER;
    }

}
