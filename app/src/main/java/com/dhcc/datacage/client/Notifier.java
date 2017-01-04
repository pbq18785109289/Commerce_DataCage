/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dhcc.datacage.client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.dhcc.datacage.R;
import com.dhcc.datacage.utils.MyApp;

import java.io.File;
import java.util.Random;

/**
 * This class is to notify the user of messages with NotificationManager.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class Notifier {

    private static final String LOGTAG = LogUtil.makeLogTag(Notifier.class);

    private static final Random random = new Random(System.currentTimeMillis());

    private Context context;

    private SharedPreferences sharedPrefs;

    private NotificationManager notificationManager;

    public Notifier(Context context) {
        this.context = context;
        this.sharedPrefs = context.getSharedPreferences(
                Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notify(String notificationId, String apiKey, String title,
                       String message, String uri,String imageUrl) {
        Log.d(LOGTAG, "notify()...");

        Log.d(LOGTAG, "notificationId=" + notificationId);
        Log.d(LOGTAG, "notificationApiKey=" + apiKey);
        Log.d(LOGTAG, "notificationTitle=" + title);
        Log.d(LOGTAG, "notificationMessage=" + message);
        Log.d(LOGTAG, "notificationUri=" + uri);
        Log.d(LOGTAG, "notificationImageUri=" + imageUrl);
        if (isNotificationEnabled()) {
            // Show the toast
            if (isNotificationToastEnabled()) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }

            Intent intent = new Intent(context,
                    NotificationDetailsActivity.class);
            intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
            intent.putExtra(Constants.NOTIFICATION_API_KEY, apiKey);
            intent.putExtra(Constants.NOTIFICATION_TITLE, title);
            intent.putExtra(Constants.NOTIFICATION_MESSAGE, message);
            intent.putExtra(Constants.NOTIFICATION_URI, uri);
            intent.putExtra(Constants.NOTIFICATION_IMAGE_URL, imageUrl);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            /**
             * 第4个参数是用于确认PendingIntent的行为:
             * FLAG_ONE_SHOT:获取的PendingIntent只能使用一次，再使用PendingIntent也将失败
             * FLAG_NO_CREATE:获取的PendingIntent若描述的Intent不存在则返回NULL值
             * FLAG_CANCEL_CURRENT:使用第一次的intent 更新extras
             * FLAG_UPDATE_CURRENT:new处一个新的Intent 更新extras
             */
            PendingIntent contentIntent = PendingIntent.getActivity(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            if (isNotificationVibrateEnabled()){
//                //设置手机震动
//                //第一个，0表示手机静止的时长，第二个，1000表示手机震动的时长
//                //第三个，1000表示手机震动的时长，第四个，1000表示手机震动的时长
//                //此处表示手机先震动1秒，然后静止1秒，然后再震动1秒
//                long[] vibrates = {0, 1000, 1000, 1000};
//                notification.vibrate = vibrates;
//            }
//            if (isNotificationSoundEnabled()){
//                //设置在通知发出的时候的音频
//                Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));
//                notification.sound = soundUri;
//            }

            NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
            builder.setContentTitle(title);
            builder.setContentText(message);
            builder.setWhen(System.currentTimeMillis());
            builder.setSmallIcon(getNotificationIcon());
            builder.setTicker(title);
            builder.setAutoCancel(true);
            builder.setContentIntent(contentIntent);
            builder.setColor(Color.parseColor("#EAA935"));
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            int defaults=0;
            if (isNotificationSoundEnabled()) {
                defaults |= Notification.DEFAULT_SOUND;
            }
            if (isNotificationVibrateEnabled()) {
                defaults |= Notification.DEFAULT_VIBRATE;
            }
            defaults |= Notification.DEFAULT_LIGHTS;
            builder.setDefaults(defaults);
            Notification notification=builder.build();

//            Notification notification= new NotificationCompat.Builder(context)
//                    .setContentTitle(title)
//                    .setContentText(message)
//                    .setWhen(System.currentTimeMillis())
//                    .setSmallIcon(getNotificationIcon())
////                    .setDefaults(NotificationCompat.DEFAULT_ALL)
//                    .setVibrate(new long[]{0, 1000, 1000, 1000})
//                    .setLights(Color.GREEN,1000,1000)
//                    .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg")))
//                    .setTicker(title)
//                    .setAutoCancel(true)
//                    .setContentIntent(contentIntent)
//                    .setColor(Color.parseColor("#EAA935"))
//                    .setPriority(NotificationCompat.PRIORITY_MAX)
//                    .build();
            notificationManager.notify(random.nextInt(), notification);

            //            Intent clickIntent = new Intent(
            //                    Constants.ACTION_NOTIFICATION_CLICKED);
            //            clickIntent.putExtra(Constants.NOTIFICATION_ID, notificationId);
            //            clickIntent.putExtra(Constants.NOTIFICATION_API_KEY, apiKey);
            //            clickIntent.putExtra(Constants.NOTIFICATION_TITLE, title);
            //            clickIntent.putExtra(Constants.NOTIFICATION_MESSAGE, message);
            //            clickIntent.putExtra(Constants.NOTIFICATION_URI, uri);
            //            //        positiveIntent.setData(Uri.parse((new StringBuilder(
            //            //                "notif://notification.adroidpn.org/")).append(apiKey).append(
            //            //                "/").append(System.currentTimeMillis()).toString()));
            //            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(
            //                    context, 0, clickIntent, 0);
            //
            //            notification.setLatestEventInfo(context, title, message,
            //                    clickPendingIntent);
            //
            //            Intent clearIntent = new Intent(
            //                    Constants.ACTION_NOTIFICATION_CLEARED);
            //            clearIntent.putExtra(Constants.NOTIFICATION_ID, notificationId);
            //            clearIntent.putExtra(Constants.NOTIFICATION_API_KEY, apiKey);
            //            //        negativeIntent.setData(Uri.parse((new StringBuilder(
            //            //                "notif://notification.adroidpn.org/")).append(apiKey).append(
            //            //                "/").append(System.currentTimeMillis()).toString()));
            //            PendingIntent clearPendingIntent = PendingIntent.getBroadcast(
            //                    context, 0, clearIntent, 0);
            //            notification.deleteIntent = clearPendingIntent;
            //
            //            notificationManager.notify(random.nextInt(), notification);

        } else {
            Log.w(LOGTAG, "Notificaitons disabled.");
        }
    }

    private int getNotificationIcon() {
        return sharedPrefs.getInt(Constants.NOTIFICATION_ICON, 0);
    }

    private boolean isNotificationEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED,
                true);
    }

    private boolean isNotificationSoundEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_SOUND_ENABLED, true);
    }

    private boolean isNotificationVibrateEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_VIBRATE_ENABLED, true);
    }

    private boolean isNotificationToastEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_TOAST_ENABLED, false);
    }

}
