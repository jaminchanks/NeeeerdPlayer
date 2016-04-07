package com.jamin.neeeerdplayer.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lingen on 15/4/21.
 * Description:
 */
public class LocalFileUtil {


    /**
     * 读取Assert目录下的文件
     * @param context
     * @param fileName
     * @return
     */
    public static String getFromAssets(Context context, String fileName) {
        String Result = "";
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null)
                Result += line;
        } catch (Exception e) {
            Log.e("error!", e.getMessage(), e);
        } finally {

            try {
                if (bufReader != null) {
                    bufReader.close();
                }
                if(inputReader!=null){
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Result;
    }

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static byte[] readFile(String path) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream((new File(path)));
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            Log.d("FILE", path);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (byteArrayOutputStream == null) {
            return new byte[0];
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void saveFile(String path, byte[] content) {
        if (content != null && content.length == 1) {
            throw new IllegalArgumentException("invalid image content");
        }
        File file = new File(path);
        if (file.getParentFile().exists() == false) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(content);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
