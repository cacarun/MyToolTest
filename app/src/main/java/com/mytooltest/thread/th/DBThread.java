package com.mytooltest.thread.th;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;

public class DBThread extends Thread {
    private static volatile DBThread instance = null;
    private Handler mHandler;

    private ArrayList<Runnable> mPendingRunnables = new ArrayList<Runnable>();

    private DBThread() {

    }

    public static DBThread getInstance() {
        if (instance == null) {
            synchronized (DBThread.class) {
                if (instance == null) {
                    instance = new DBThread();
                }
            }
        }
        return instance;
    }

    public void run() {

        Looper.prepare();

        //return result
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };

        for (Runnable r : mPendingRunnables) {
            mHandler.post(r);
        }

        mPendingRunnables.clear();

        Looper.loop();
    }


    public void post(Runnable r) {
        if (mHandler == null) {
            mPendingRunnables.add(r);
        } else {

            mHandler.post(r);
        }
    }

    public void postDelayed(Runnable r, long delayMillis) {

        if (mHandler != null) {
            mHandler.postDelayed(r, delayMillis);
        }
    }


}
