package com.jamin.neeeerdplayer.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.jamin.neeeerdplayer.R;
import com.jamin.neeeerdplayer.ui.MainActivity;

/**
 * Created by jamin on 16-5-4.
 */
public class UploadService extends Service {
    private static final int NOTIFICATION_FLAG = 1;
    public static final String UPLOAD_VIDEO_NAME = "upload video name";
    private RemoteViews mRemoteViews;

    private Handler handler = new Handler();

    private static int mProgress = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startNotification();
    }

    private void startNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        Notification notify2 = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.upload_48)
//                .setTicker("正在上传新视频")
//                .setContentTitle("NPlayer")
//                .setContentText("正在上传视频")
                .setContentIntent(pendingIntent2)
                .setNumber(1)
                .build();

        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_upload);
        notify2.contentView = mRemoteViews;
        notify2.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notify2);
        startForeground(NOTIFICATION_FLAG, notify2);

       handler.post(new Runnable() {
           @Override
           public void run() {
                while (mProgress < 100) {
                    mRemoteViews.setProgressBar(R.id.pg_upload, 100, 1, false);
                    mProgress += 10;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
           }
       });

    }
}
