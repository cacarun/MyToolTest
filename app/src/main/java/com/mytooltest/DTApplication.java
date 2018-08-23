package com.mytooltest;

import android.app.Application;
import android.content.Context;

/**
 * Created by jarvis on 2018/8/23.
 */

public class DTApplication extends Application {

    private static DTApplication sInstance;

    public static DTApplication getInstance() {
        if (sInstance == null) {
            System.exit(0);
        }
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        sInstance = this;
    }
}
