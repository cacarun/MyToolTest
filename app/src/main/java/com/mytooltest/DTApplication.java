package com.mytooltest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.mytooltest.thread.th.DBThread;

/**
 * Created by jarvis on 2018/8/23.
 */

public class DTApplication extends Application {

    private static DTApplication sInstance;

    private Handler mHandler = new Handler();

    public static DTApplication getInstance() {
        if (sInstance == null) {
            System.exit(0);
        }
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (DBThread.getInstance().getState() == Thread.State.NEW) {
            DBThread.getInstance().start();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        sInstance = this;
    }

    public void executeInMainthread(Runnable r) {

        mHandler.post(r);
    }

    public void executeInMainThreadWithDelay(Runnable r, long delayInMillis) {

        mHandler.postDelayed(r, delayInMillis);
    }

    public Handler getHandler() {

        return mHandler;
    }

}
