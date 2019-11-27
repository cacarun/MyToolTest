package com.mytooltest.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;

public class AlarmReceiverTest extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiverTest";

    @Override
    public void onReceive(Context context, Intent intent) {

        int alarmId = intent.getIntExtra(GlobalValues.KEY_NOTIFY_ID, 0);
        String actionWithAlarmId = GlobalValues.TIMER_ACTION + alarmId;

        Log.d(TAG, "Alarm, onReceive action=" + intent.getAction());
        Log.d(TAG, "Alarm, onReceive actionWithAlarmId=" + actionWithAlarmId);

        if (actionWithAlarmId.equals(intent.getAction())) {

            String str = intent.getStringExtra(GlobalValues.KEY_NOTIFY);

            NotifyObject obj = null;
            try {
                obj = NotifyObject.from(str);
                if (obj != null) {
                    Log.d(TAG, "Alarm, onReceive start notify...");
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
