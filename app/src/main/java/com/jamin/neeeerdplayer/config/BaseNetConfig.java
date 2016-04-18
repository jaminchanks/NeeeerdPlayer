package com.jamin.neeeerdplayer.config;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jamin on 16-4-4.
 */
public class BaseNetConfig {
    //网络访问的url
    public static String WEB_URL;

    private static final String TAG = BaseNetConfig.class.getSimpleName();

    static {
        final Properties properties = new Properties();
        InputStream inputStream = BaseNetConfig.class.getResourceAsStream("/assets/foo.properties");

        try {
            properties.load(inputStream);
            WEB_URL = properties.getProperty("WEB_URL");
            Log.e("WEB_URL", WEB_URL);
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
