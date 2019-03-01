package com.mytooltest.thread;

import android.util.Log;

import com.mytooltest.DTApplication;
import com.mytooltest.thread.th.DBThread;

public class ThreadTestManager {

    private static final String TAG = "ThreadTestManager";

    private static final class ThreadTestManagerHolder {

        private static final ThreadTestManager INSTANCE = new ThreadTestManager();

    }

    public static ThreadTestManager getInstance() {
        return ThreadTestManagerHolder.INSTANCE;
    }

    public LoadingListener loadingListener;

    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public interface LoadingListener {
        void onLoadingListener();
    }

    public void loadingData() {

        DBThread.getInstance().post(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d(TAG, "thread loading...");
                    Thread.sleep(5000);
                    Log.d(TAG, "thread loading end!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DTApplication.getInstance().executeInMainthread(new Runnable(){
                    @Override
                    public void run() {

                        if (loadingListener != null) {
                            loadingListener.onLoadingListener();
                        }
                    }
                });

            }
        });
    }


}
