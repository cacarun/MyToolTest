package com.mytooltest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jarvis on 2018/4/19.
 */

public class ToolsForTime {

    public static long dateToMillis(String date) {
        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date formatDate = format.parse(date);
            return formatDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getGapDays(long endTime, long beginTime) {

        Date endDate = new Date();
        endDate.setTime(endTime);

        Date beginDate = new Date();
        beginDate.setTime(beginTime);

        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(beginDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }
}
