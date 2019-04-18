package com.mytooltest.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.io.Serializable;
import java.util.Map;

public class AlarmTimerUtil {

    private static final String TAG = "AlarmTimerUtil";

    /**
     * 设置定时闹钟
     *
     * @param context
     * @param alarmId
     * @param action
     * @param map     要传递的参数
     */
    public static void setAlarmTimer(Context context, int alarmId, long time, String action, Map<String, Serializable> map) {
        Intent myIntent = new Intent();
        myIntent.setAction(action);
        if (map != null) {
            for (String key : map.keySet()) {
                myIntent.putExtra(key, map.get(key));
            }
        }

        // 如果是广播
        myIntent.setClass(context, AlarmReceiver.class); // 8.0 需要显式调用，否则收不到广播
        PendingIntent sender = PendingIntent.getBroadcast(context, alarmId, myIntent, 0);


        // 如果是服务
//        PendingIntent sender = PendingIntent.getService(context, alarmId, myIntent, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            return;
        }

        Log.d(TAG, "Alarm, setAlarmTimer sending sdk version=" + Build.VERSION.SDK_INT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // MARSHMALLOW 23 OR ABOVE

            Log.d(TAG, "Alarm, 版本 >= 6.0");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, sender);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // LOLLIPOP 21 OR ABOVE

            Log.d(TAG, "Alarm, 6.0 > 版本 >= 5.0");
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(time, sender);
            alarmManager.setAlarmClock(alarmClockInfo, sender);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // KITKAT 19 OR ABOVE

            Log.d(TAG, "Alarm, 5.0 > 版本 >= 4.4");
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, sender);
        } else { // FOR BELOW KITKAT ALL DEVICES

            Log.d(TAG, "Alarm, 版本 < 4.4");
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, sender);
        }
    }

    /**
     * 取消闹钟
     *
     * @param context
     * @param action
     */
    public static void cancelAlarmTimer(Context context, String action, int alarmId) {
        Intent myIntent = new Intent();
        myIntent.setAction(action);

        // 如果是广播
        myIntent.setClass(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, alarmId, myIntent, 0);

        // 如果是服务
//        PendingIntent sender = PendingIntent.getService(context, alarmId, myIntent, 0);

        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarm == null) {
            return;
        }

        alarm.cancel(sender);
    }


    /**
     * 设置周期性闹钟
     *
     */
//    public static void setRepeatingAlarmTimer(Context context, long firstTime,
//                                              long cycTime, String action, int AlarmManagerType) {
//        Intent myIntent = new Intent();
//        myIntent.setAction(action);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, myIntent, 0);
//
//        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        if (alarm == null) {
//            return;
//        }
//
//        // param1：闹钟类型，param2：闹钟首次执行时间，param3：闹钟两次执行的间隔时间，param4：闹钟响应动作。
//        alarm.setRepeating(AlarmManagerType, firstTime, cycTime, sender);
//    }

    /**
     * 设置定时闹钟
     *
     */
//    public static void setAlarmTimer(Context context, long cycTime,
//                                     String action, int AlarmManagerType) {
//        Intent myIntent = new Intent();
//        myIntent.setAction(action);
//
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, myIntent, 0);
//
//        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        if (alarm == null) {
//            return;
//        }
//
//        alarm.set(AlarmManagerType, cycTime, sender);
//    }

}
