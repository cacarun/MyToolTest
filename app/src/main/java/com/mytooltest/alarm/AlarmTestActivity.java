package com.mytooltest.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.util.ToolsForTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmTestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AlarmTestActivity";

    private int tick;

    private Timer timer = new Timer();
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        findViewById(R.id.btn_alarm_start).setOnClickListener(this);
        findViewById(R.id.btn_alarm_end).setOnClickListener(this);
        findViewById(R.id.btn_timer_stop).setOnClickListener(this);



    }

    /**
     * 预埋所有时间，定时提醒
     */
    private void alarmTest() {
        long now = System.currentTimeMillis();
//        long now = SystemClock.elapsedRealtime();
        long interval[] = {5000, 10000, 15000, 20000};
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
            obj.activityClass = PendingTestActivity.class;
            obj.icon = R.mipmap.ic_launcher;

            count++;

            notifyObjects.put(obj.type, obj);
        }

        NotificationUtil.notifyByAlarm(this, notifyObjects);

    }

    /**
     * 某天之前每天提醒一次（预埋所有时间）
     */
    private void alarmTest2() {
        // 截止时间点
        String endDate = "2019-03-18";
        // 当前时间点
        long nowMillis = System.currentTimeMillis();
        // yyyy-MM-dd
        long endMillis = ToolsForTime.dateToMillis(endDate);
        // 相隔几天
        int gapDays = ToolsForTime.getGapDays(endMillis, nowMillis);
        Log.d(TAG, "gapDays=" + gapDays);

        Map<Integer, NotifyObject> notifyObjects = new HashMap<>();
        SimpleDateFormat smf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        int count = 1;

        while (--gapDays >= 0) {

            Log.d(TAG, "gap day=" + gapDays);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(endMillis);
            calendar.add(Calendar.DATE, -gapDays);

            Log.d(TAG, "Add day: " + calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE));

            NotifyObject obj = new NotifyObject();
            obj.title = "标题" + count;
            obj.subText = "理论提醒时间:" + smf.format(calendar.getTime());
            obj.content = "类型:" + count + ", 间隔一天";
            obj.firstTime = calendar.getTime().getTime();
            obj.type = count;
            obj.activityClass = PendingTestActivity.class;
            obj.icon = R.mipmap.ic_launcher;

            count++;

            notifyObjects.put(obj.type, obj);
        }

        NotificationUtil.notifyByAlarm(this, notifyObjects);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_alarm_start) {

            alarmTest();
//            alarmTest2();


            // 打印时间，方便查看
            tick = 0;
            if (task == null) {
                Log.d(TAG, "Alarm, timer first new");
                task = new TimerTask() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Alarm, tick=" + ++tick);
                    }
                };
                timer = new Timer();
                timer.schedule(task, 0, 1000);
            }


        } else if (id == R.id.btn_alarm_end) {

            // 取消所有通知 同时取消定时闹钟
            NotificationUtil.clearAllNotifyMsg(this);
        } else if (id == R.id.btn_timer_stop) {

            cancelTimer();
        }
    }

    private void cancelTimer() {
        // 取消打印时间
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cancelTimer();
    }
}
