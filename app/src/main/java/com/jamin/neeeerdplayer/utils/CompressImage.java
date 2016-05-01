package com.jamin.neeeerdplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by jamin on 8/15/15.
 */
public class CompressImage {

    //根据路径获得图片并压缩，返回bitmap用于显示
    private Bitmap getimage(String srcPath, float requestWidth, float requestHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,options);//此时返回bm为空

        options.inJustDecodeBounds = false;
        int finalWidth = options.outWidth;
        int finalHeight = options.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int inSampleSize = 1;//be=1表示不缩放
        if (finalWidth > finalHeight && finalWidth > requestWidth) {//如果宽度大的话根据宽度固定大小缩放
            inSampleSize = (int) (options.outWidth / requestWidth);
        } else if (finalWidth < finalHeight && finalHeight > requestHeight) {//如果高度高的话根据宽度固定大小缩放
            inSampleSize = (int) (options.outHeight / requestHeight);
        }
        if (inSampleSize <= 0)
            inSampleSize = 1;
        options.inSampleSize = inSampleSize;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, options);
        return compressByQuality(bitmap);//压缩好比例大小后再进行质量压缩
    }



    //图片按比例大小压缩方法（根据Bitmap图片压缩）：
    public static Bitmap compressByRate(Bitmap image, float requestWidth, float requestHeight) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, options);
        options.inJustDecodeBounds = false;
        int finalWidth = options.outWidth;
        int finalHeight = options.outHeight;

        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int inSampleSize = 1;//be=1表示不缩放

        if (finalWidth > finalHeight && finalWidth > requestWidth) {//如果宽度大的话根据宽度固定大小缩放
            inSampleSize = (int) (options.outWidth / requestWidth);
        } else if (finalWidth < finalHeight && finalHeight > requestHeight) {//如果高度高的话根据宽度固定大小缩放
            inSampleSize = (int) (options.outHeight / requestHeight);
        }

        if (inSampleSize <= 0)
            inSampleSize = 1;
        options.inSampleSize = inSampleSize;//设置缩放比例
        options.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, options);
        return compressByQuality(bitmap);//压缩好比例大小后再进行质量压缩
    }


    //质量压缩法
    public static Bitmap compressByQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    //把bitmap转化为String
    public static String bitmapToString(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


}
