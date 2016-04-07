package com.jamin.neeeerdplayer.config;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by jamin on 16-4-4.
 */
public class NetConfig {
    //网络访问的url
    public static String APP_URL;

    private static final String TAG = NetConfig.class.getSimpleName();

    static {
        final Properties properties = new Properties();
        InputStream inputStream = NetConfig.class.getResourceAsStream("/assets/foo.properties");

        try {
            properties.load(inputStream);
            APP_URL = properties.getProperty("APP_URL");
            Log.e("APP_URL", APP_URL);
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
