package com.mytooltest.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class AlarmService extends Service {

    private static final String TAG = "AlarmService";

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Alarm, onStartCommand");
        String str = intent.getStringExtra(GlobalValues.KEY_NOTIFY);
        if (str == null || str.trim().length() == 0)
            return super.onStartCommand(intent, flags, startId);
        try {
            NotifyObject obj = NotifyObject.from(str);
            Log.d(TAG, "Alarm, onStartCommand start notify...");
            NotificationUtil.notifyByAlarmByReceiver(this, obj);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
