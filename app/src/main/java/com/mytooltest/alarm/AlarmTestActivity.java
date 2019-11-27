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

        findViewById(R.id.btn_alarm_test_start).setOnClickListener(this);
        findViewById(R.id.btn_timer_test_stop).setOnClickListener(this);



    }

    /**
     * 定时提醒（预埋所有时间）
     */
    private void alarmTest() {
        long now = System.currentTimeMillis();
//        long now = SystemClock.elapsedRealtime();
//        long interval[] = {5000, 10000, 15000, 20000};
        long interval[] = {10000, 20000, 35000, 55000};
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
            calendar.add(Calendar.DATE, -1);

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

            startTimer();

        } else if (id == R.id.btn_alarm_end) {

            // 取消所有通知 同时取消定时闹钟
            NotificationUtil.clearAllNotifyMsg(this);

        } else if (id == R.id.btn_timer_stop) {

            cancelTimer();

        } else if (id == R.id.btn_alarm_test_start) {

//            testData("09:30:00", "1555833023000"); // 2019/4/21 15:50:23
//            testData("18:30:00", "1555833023000"); // 2019/4/21 15:50:23
//
//            testData("09:30:00", "1555919423000"); // 2019/4/22 15:50:23
//            testData("18:30:00", "1555919423000"); // 2019/4/22 15:50:23
//
//            testData("09:30:20", "1556005823000"); // 2019/4/23 15:50:23
//            testData("18:30:00", "1556005823000"); // 2019/4/23 15:50:23
//
            testData("09:30:00", "1556092223000"); // 2019/4/24 15:50:23
//            testData("18:30:00", "1556092223000"); // 2019/4/24 15:50:23
//
//            testData("09:30:00", "1556265023000"); // 2019/4/26 15:50:23
//            testData("18:30:00", "1556265023000"); // 2019/4/26 15:50:23

        } else if (id == R.id.btn_timer_test_stop) {

            // 取消所有通知 同时取消定时闹钟
            NotificationUtil.clearAllNotifyMsgTest(this);
        }
    }

    /**
     * 从 明天 开始到 截止时间 在 每天推送时间 推送
     *
     * @param pushTimeStr    每天推送时间
     * @param endMillisStr   截止时间
     */
    private void testData(String pushTimeStr, String endMillisStr) {

        long endMillis = Long.parseLong(endMillisStr);
        Date endDate = new Date(endMillis);

        long nowMillis = System.currentTimeMillis();

        // 判断当前时间在活动截止日期之前
        if (nowMillis >= endMillis) {
            Log.d(TAG, "Alarm, nowMillis >= endMillis and return");
            return;
        }

        // 相隔几天
        int gapDays = ToolsForTime.getGapDays(endMillis, nowMillis);
        Log.d(TAG, "Alarm, gapDays=" + gapDays);

        boolean isEndDayNeeded = false; // 截止时间那天是否需要推送

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm:ss");
        Date pushTimeDate = null;
        Date endTimeDate = null;

        try {
            pushTimeDate = dateTimeFormat.parse(pushTimeStr);
            endTimeDate = dateTimeFormat.parse(dateTimeFormat.format(endDate));

            Calendar pushTimeCalendar = Calendar.getInstance();
            pushTimeCalendar.setTime(pushTimeDate);

            Calendar endTimeCalendar = Calendar.getInstance();
            endTimeCalendar.setTime(endTimeDate);

            isEndDayNeeded = endTimeCalendar.after(pushTimeCalendar);

            if (!isEndDayNeeded) {
                --gapDays;
            }
            Log.d(TAG, "Alarm, isEndDayNeeded = " + isEndDayNeeded + " update gapDays = " + gapDays);

            if (gapDays <= 0) {
                Log.d(TAG, "Alarm, gapDays <= 0, no push needed");
                return;
            }

            // 最后一天的推送时间点，可能在截止时间那一天，也可能在之前一天
            Calendar lastPushTimeCalendar = Calendar.getInstance();
            lastPushTimeCalendar.setTimeInMillis(endMillis);

            Log.d(TAG, "Alarm, lastPushTimeCalendar before    = " + lastPushTimeCalendar.get(Calendar.YEAR) + "年 "
                    + (lastPushTimeCalendar.get(Calendar.MONTH) + 1) + "月 "
                    + lastPushTimeCalendar.get(Calendar.DATE) + "日 "
                    + lastPushTimeCalendar.get(Calendar.HOUR_OF_DAY) + "时 "
                    + lastPushTimeCalendar.get(Calendar.MINUTE) + "分 "
                    + lastPushTimeCalendar.get(Calendar.SECOND) + "秒 ");

            lastPushTimeCalendar.set(Calendar.HOUR_OF_DAY, pushTimeCalendar.get(Calendar.HOUR_OF_DAY));
            lastPushTimeCalendar.set(Calendar.MINUTE, pushTimeCalendar.get(Calendar.MINUTE));
            lastPushTimeCalendar.set(Calendar.SECOND, pushTimeCalendar.get(Calendar.SECOND));

            Log.d(TAG, "Alarm, lastPushTimeCalendar after     = " + lastPushTimeCalendar.get(Calendar.YEAR) + "年 "
                    + (lastPushTimeCalendar.get(Calendar.MONTH) + 1) + "月 "
                    + lastPushTimeCalendar.get(Calendar.DATE) + "日 "
                    + lastPushTimeCalendar.get(Calendar.HOUR_OF_DAY) + "时 "
                    + lastPushTimeCalendar.get(Calendar.MINUTE) + "分 "
                    + lastPushTimeCalendar.get(Calendar.SECOND) + "秒 ");

            if (!isEndDayNeeded) {
                // 调整到截止日期之前一天
                lastPushTimeCalendar.add(Calendar.DATE, -1);

                Log.d(TAG, "Alarm, lastPushTimeCalendar after - 1 = " + lastPushTimeCalendar.get(Calendar.YEAR) + "年 "
                        + (lastPushTimeCalendar.get(Calendar.MONTH) + 1) + "月 "
                        + lastPushTimeCalendar.get(Calendar.DATE) + "日 "
                        + lastPushTimeCalendar.get(Calendar.HOUR_OF_DAY) + "时 "
                        + lastPushTimeCalendar.get(Calendar.MINUTE) + "分 "
                        + lastPushTimeCalendar.get(Calendar.SECOND) + "秒 ");
            }

            // 根据最后一天的推送时间点，往前推时间点，预埋每天的推送时间点
            Map<Integer, NotifyObject> notifyObjects = new HashMap<>();
            SimpleDateFormat smf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

            while (--gapDays >= 0) {

                int alarmId = gapDays + 1; // 从 1 开始
                Log.d(TAG, "Alarm, alarmId=" + alarmId);

                Log.d(TAG, "Alarm, add day to alarm: " + lastPushTimeCalendar.get(Calendar.YEAR) + "年 "
                        + (lastPushTimeCalendar.get(Calendar.MONTH) + 1) + "月 "
                        + lastPushTimeCalendar.get(Calendar.DATE) + "日 "
                        + lastPushTimeCalendar.get(Calendar.HOUR_OF_DAY) + "时 "
                        + lastPushTimeCalendar.get(Calendar.MINUTE) + "分 "
                        + lastPushTimeCalendar.get(Calendar.SECOND) + "秒 ");

                NotifyObject obj = new NotifyObject();
                obj.title = "标题" + alarmId;
                obj.subText = "理论提醒时间:" + smf.format(lastPushTimeCalendar.getTime());
                obj.content = "类型:" + alarmId + ", 间隔一天";
                obj.firstTime = lastPushTimeCalendar.getTime().getTime();
                obj.type = alarmId;
                obj.activityClass = PendingTestActivity.class;
                obj.icon = R.mipmap.ic_launcher;

                notifyObjects.put(obj.type, obj);

                lastPushTimeCalendar.add(Calendar.DATE, -1);
            }

            NotificationUtil.notifyByAlarmTest(this, notifyObjects);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Alarm, ==============================================");

    }

    private void startTimer() {
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
