package com.mytooltest.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.mytooltest.util.SharedPreferencesUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 消息通知工具
 */
public class NotificationUtil {

    private static final String TAG = "NotificationUtil";

    /**
     * 通过定时闹钟发送通知
     *
     * @param context
     * @param notifyObjectMap
     */
    public static void notifyByAlarm(Context context, Map<Integer, NotifyObject> notifyObjectMap) {
        //将数据存储起来
        int count = 0;

        Set<Integer> keySet = notifyObjectMap.keySet();
        for (Integer key0 : keySet) {
            if (!notifyObjectMap.containsKey(key0)) {
                break;
            }

            NotifyObject obj = notifyObjectMap.get(key0);
            if (obj == null) {
                break;
            }

            if (obj.times.size() <= 0) {
                if (obj.firstTime > 0) {
                    try {

                        Log.d(TAG, "Alarm, notifyByAlarm firstTime > 0");

                        Map<String, Serializable> map = new HashMap<>();
                        map.put(GlobalValues.KEY_NOTIFY_ID, obj.type);
                        map.put(GlobalValues.KEY_NOTIFY, NotifyObject.to(obj));
                        AlarmTimerUtil.setAlarmTimer(context, ++count, obj.firstTime, GlobalValues.TIMER_ACTION, map);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (long time : obj.times) {
                    if (time > 0) {
                        try {

                            Log.d(TAG, "Alarm, notifyByAlarm times...");

                            Map<String, Serializable> map = new HashMap<>();
                            map.put(GlobalValues.KEY_NOTIFY_ID, obj.type);
                            map.put(GlobalValues.KEY_NOTIFY, NotifyObject.to(obj));
                            AlarmTimerUtil.setAlarmTimer(context, ++count, time, GlobalValues.TIMER_ACTION, map);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        Log.d(TAG, "Alarm, notifyByAlarm SP KEY_MAX_ALARM_ID=" + count);
        SharedPreferencesUtil.setKeyMaxAlarmId(count);
    }

    /**
     * 通知
     *
     * @param context
     * @param obj
     */
    public static void notifyByAlarmByReceiver(Context context, NotifyObject obj) {
        if (context == null || obj == null) return;
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMsg(context, obj, obj.type, System.currentTimeMillis(), manager);
    }

    /**
     * 实现通知
     *
     * @param context
     * @param obj
     */
    private static void notifyMsg(Context context, NotifyObject obj, int nid, long time, NotificationManager mNotifyMgr) {
        if (context == null || obj == null) return;
        if (mNotifyMgr == null) {
            mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (time <= 0) return;

        //准备intent
        Intent intent = new Intent(context, obj.activityClass);
        if (obj.param != null && obj.param.trim().length() > 0) {
            intent.putExtra("param", obj.param);
        }

        //notification
        Notification notification = null;
        String contentText = obj.content;
        // 构建 PendingIntent
        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //版本兼容

        Log.d(TAG, "Alarm, notifyMsg sdk version=" + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//兼容 Android 8.0 26
            Log.d(TAG, "Alarm, notifyMsg 版本 >= 8.0");
            String id = "my_channel_01";
            int importance = NotificationManager.IMPORTANCE_LOW;
            CharSequence name = "notice";
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.enableLights(true);
            mChannel.setDescription("just show notice");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.GREEN);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotifyMgr.createNotificationChannel(mChannel);

            Notification.Builder builder = new Notification.Builder(context, id);
            builder.setAutoCancel(true)
                    .setContentIntent(pi)
                    .setContentTitle(obj.title)
                    .setContentText(obj.content)
                    .setOngoing(false)
                    .setSmallIcon(obj.icon)
                    .setWhen(System.currentTimeMillis());
            if (obj.subText != null && obj.subText.trim().length() > 0) {
                builder.setSubText(obj.subText);
            }
            notification = builder.build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 23
            Log.d(TAG, "Alarm, notifyMsg 版本 >= 6.0");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(obj.title)
                    .setContentText(contentText)
                    .setSmallIcon(obj.icon)
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setWhen(System.currentTimeMillis());
            if (obj.subText != null && obj.subText.trim().length() > 0) {
                builder.setSubText(obj.subText);
            }
            notification = builder.build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) { // 16
            Log.d(TAG, "Alarm, notifyMsg 版本 >= 4.1");
            Notification.Builder builder = new Notification.Builder(context);
            builder.setAutoCancel(true)
                    .setContentIntent(pi)
                    .setContentTitle(obj.title)
                    .setContentText(obj.content)
                    .setOngoing(false)
                    .setSmallIcon(obj.icon)
                    .setWhen(System.currentTimeMillis());
            if (obj.subText != null && obj.subText.trim().length() > 0) {
                builder.setSubText(obj.subText);
            }
            notification = builder.build();
        } else {
            Log.e(TAG, "Alarm, notifyMsg sdk ERROR!!!");
        }

        if (notification != null && mNotifyMgr != null) {
            Log.d(TAG, "Alarm, notifyMsg show!");
            mNotifyMgr.notify(nid, notification);
        } else {
            Log.e(TAG, "Alarm, notifyMsg notify ERROR!!!");
        }
    }

    /**
     * 取消所有通知 同时取消定时闹钟
     *
     * @param context
     */
    public static void clearAllNotifyMsg(Context context) {
        try {

            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (mNotifyMgr != null) {
                mNotifyMgr.cancelAll();
            }

            int max_id = SharedPreferencesUtil.getKeyMaxAlarmId();
            for (int i = 1; i <= max_id; i++) {
                Log.d(TAG, "Alarm, clearAllNotifyMsg SP KEY_MAX_ALARM_ID=" + i);
                AlarmTimerUtil.cancelAlarmTimer(context, GlobalValues.TIMER_ACTION, i);
            }
            // 清除数据
            SharedPreferencesUtil.toRemove(SharedPreferencesUtil.KEY_MAX_ALARM_ID);

        } catch (Exception e) {
            Log.e(TAG, "Alarm, 取消通知失败", e);
        }
    }

}
