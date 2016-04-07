package com.jamin.neeeerdplayer.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lingen on 15/4/9.
 * Description:
 */
public class ImageCompressUtil {

    /**
     * bitmap转成byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte[] 转成BITMAP
     *
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        try{
            if (b!=null && b.length != 0) {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     *  图片压缩，按质量就行压缩
     *  @see #compressImageForQuality(Bitmap, int)
     * */
    public static byte[] compressImageForQuality(Bitmap image, int size) {
        return compressImageForQuality(image, Bitmap.CompressFormat.JPEG, size);
    }

    /**
     * 图片压缩，按质量就行压缩
     * @param image
     * @param format
     * @param size
     * @return
     */
    public static byte[] compressImageForQuality(Bitmap image, Bitmap.CompressFormat format, int size) {

        if(image==null){
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            image.compress(format, 90, baos);
            // 循环判断如果压缩后图片是否大于指定大小,大于继续压缩
            int max = 90;
            while ((baos.toByteArray().length / 1024) > size) {
                // 重置baos即清空baos
                baos.reset();
                if (max <= 10) {
                    break;
                }
                max = max - 10;
                image.compress(format, max, baos);
            }

        } finally {
            image.recycle();
            image = null;
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }


    /**
     * 获取调整角度后的图片
     *
     * @param imgPath
     * @return
     */
    public static Bitmap getBitmapAdjustOritation(String imgPath) {
        Bitmap bm = getBitmap(imgPath);
        return AdjustOritation(bm, imgPath);
    }

    public static Bitmap getThumbAdjustOritation(String imgPath) {
        Bitmap bm = getBitmapFromFile(imgPath,400, 200, 15);
        return AdjustOritation(bm, imgPath);
    }

    public static Bitmap AdjustOritation(Bitmap bm, String path) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (bm == null || exif == null) {
            return bm;
        }
        int digree = 0;
        // 读取图片中相机方向信息
        int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        // 计算旋转角度
        switch (ori) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                digree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                digree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                digree = 270;
                break;
            default:
                digree = 0;
                break;
        }
        if (digree != 0) {
            // 旋转图片
            Matrix m = new Matrix();
            m.postRotate(digree);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        }
        return bm;
    }

    /**
     * 获取调整角度后的图片
     *
     * @param imgPath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapAdjustOritation(String imgPath, int width, int height) {
        Bitmap bm = getBitmap(imgPath, width, height);
        return AdjustOritation(bm, imgPath);
    }

    /**
     * 根据图片路径从文件中缩放的获取图片
     */
    public static Bitmap getBitmap(String imgPath, int requiredWidth, int requiredHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try {
            BitmapFactory.decodeFile(imgPath, options);
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
            Log.i("getBitmap by imgPath", "options.inSampleSize==" + options.inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imgPath, options);
        } catch (Exception e) {
            Log.e("error!", e.getMessage(), e);
        }
        return bitmap;
    }


    /**
     * 获取调整角度后的图片
     *
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapAdjustOritation(Context context, Uri uri, int width, int height) {
        Bitmap bm = getBitmap(context, uri, width, height);
        return AdjustOritation(bm, uri.getPath());

    }

    /**
     * 根据uri从文件中缩放的获取图片
     */
    public static Bitmap getBitmap(Context context, Uri uri, int requiredWidth, int requiredHeight) {
        ContentResolver cr = context.getContentResolver();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try {
            BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            Log.e("error!", e.getMessage(), e);
        }
        return bitmap;

    }

    public static Bitmap getBitmap(String imgPath) {
        return getBitmapFromFile(imgPath,1200,1600,600);
    }

    private static Bitmap getBitmapFromFile(String imagePath,int width,int height,int quality) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, opts);
        opts.inSampleSize = ImageCompressUtil.computeSampleSize(opts, -1, width * height);
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);
        byte[] thus = ImageCompressUtil.compressImageForQuality(bitmap, quality);
        return ImageCompressUtil.Bytes2Bimap(thus);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        int wScale = (int) Math.ceil(1.0 * width / reqWidth);
        int yScale = (int) Math.ceil(1.0 * height / reqHeight);
        inSampleSize = wScale > yScale ? wScale : yScale;

        if (inSampleSize < 1) {
            inSampleSize = 1;
        }
        return inSampleSize;
    }

    /**
     * 质量压缩图片
     *
     * @param bitmap
     * @param toByteLen  压缩到的字节数
     * @param outputPath 压缩后保存路径，为null将不会保存
     * @return
     */
    public static void compressBitmap(Bitmap bitmap, double toByteLen, String outputPath) {
        if (bitmap == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        int len = baos.toByteArray().length;
        Log.i("compressBitmap", "压缩前byte.length==" + len + "(byte),==" + 1.0f * len / 1024 + "kb");
        while (len > toByteLen) {
            quality -= 20;
            if (quality < 0) {
                quality = 0;
                break;
            }
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            len = baos.toByteArray().length;
        }
        Log.i("compressBitmap", "压缩图片后，JPEG格式压缩图片，质量=" + quality + ",压缩后byte.length==" + len + "(byte),==" + 1.0f * len / 1024 + "kb");
        FileOutputStream fos;
        try {
            if (!TextUtils.isEmpty(outputPath)) {
                fos = new FileOutputStream(new File(outputPath));
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }


    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
