package com.mytooltest.thread.th;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.mytooltest.DTApplication;

import java.util.ArrayList;


public class DTBackgroundThread extends Thread {

    private static final String TAG = "DTBackgroundThread";

    private static class DTBackgroundThreadHolder {
        private static DTBackgroundThread INSTANCE = new DTBackgroundThread();
    }

    public static DTBackgroundThread getInstance() {
        return DTBackgroundThreadHolder.INSTANCE;
    }

    private Handler mHandler;

    private ArrayList<Runnable> mPendingRunnables = new ArrayList<Runnable>();

    private final static int QUIT_THREAD_MESSAGE = 100;

    @Override
    public void run() {

        Log.d(TAG, "begin thread run");
        Looper.prepare();

        synchronized (this) {

            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if (msg.what == QUIT_THREAD_MESSAGE) {

                        Log.d(TAG, "receive quit thread message");
                        this.getLooper().quit();
                    }

                }

            };
        }

        DTApplication.getInstance().executeInMainthread(new Runnable() {

            @Override
            public void run() {
                for (Runnable r : mPendingRunnables) {
                    mHandler.post(r);
                }
                mPendingRunnables.clear();
            }
        });

        Looper.loop();

        Log.d(TAG, "end thread run");
    }

    public void post(Runnable r) {
        synchronized (this) {

            if (mHandler == null) {
                mPendingRunnables.add(r);
            } else {
                if (mPendingRunnables.size() > 0) {
                    for (Runnable pendingR : mPendingRunnables) {
                        mHandler.post(pendingR);
                    }
                    mPendingRunnables.clear();
                }

                mHandler.post(r);
            }
        }
    }

    public void postDelayed(Runnable r, long delayMillis) {

        if (mHandler != null) {
            mHandler.postDelayed(r, delayMillis);
        }
    }

    public void quit() {

        Message msg = new Message();
        msg.what = QUIT_THREAD_MESSAGE;
        mHandler.sendMessage(msg);
    }

}
