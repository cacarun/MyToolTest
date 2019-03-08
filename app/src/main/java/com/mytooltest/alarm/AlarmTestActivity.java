package com.mytooltest.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mytooltest.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AlarmTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AlarmTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        findViewById(R.id.btn_alarm_start).setOnClickListener(this);
        findViewById(R.id.btn_alarm_end).setOnClickListener(this);

        alarmTest();

    }

    private void alarmTest() {
        long now = System.currentTimeMillis();
//        long interval[] = {0, 10, 60, 3000, 6000, 12000, 30000, 50000, 60000, 100000};
        long interval[] = {5000};
        int count = 1;
        SimpleDateFormat smf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Map<Integer, NotifyObject> notifyObjects = new HashMap<>();
        for (long inter : interval) {

            Date date = new Date(now + inter);

            NotifyObject obj = new NotifyObject();
            obj.title = "标题" + count;
            obj.subText = "理论提醒时间:" + smf.format(date);
            obj.content = "类型:" + count + ", 间隔:" + inter;
            obj.firstTime = now + inter;
            obj.type = count;

            count++;

            notifyObjects.put(obj.type, obj);
        }

        NotificationUtil.notifyByAlarm(this, notifyObjects);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_alarm_start) {

        } else if (id == R.id.btn_alarm_end) {

        }
    }


}
