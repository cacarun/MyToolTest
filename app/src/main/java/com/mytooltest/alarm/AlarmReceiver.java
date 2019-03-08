package com.mytooltest.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "Alarm, onReceive action=" + intent.getAction());
        if (GlobalValues.TIMER_ACTION.equals(intent.getAction())) {
            String str = intent.getStringExtra("KEY_NOTIFY");
            NotifyObject obj = null;
            try {
                obj = NotifyObject.from(str);
                if (obj != null) {
                    NotificationUtil.notifyByAlarmByReceiver(context, obj); // 立即开启通知
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
