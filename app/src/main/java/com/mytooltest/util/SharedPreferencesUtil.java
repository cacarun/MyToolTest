package com.mytooltest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mytooltest.DTApplication;


public class SharedPreferencesUtil {

    private static final String SHARED_PREFERENCE_NAME ="LOCAL_SHARED_PREFERENCE_INFO";

    private static final SharedPreferences prefs = DTApplication.getInstance()
            .getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

    public static void toDeActive() {
        prefs.edit().clear().apply();
    }

    public static void toRemove(String keyId) {
        prefs.edit().remove(keyId).apply();
    }

    public static final String KEY_MAX_ALARM_ID = "KEY_MAX_ALARM_ID";


    public static int getKeyMaxAlarmId() {
        return prefs.getInt(KEY_MAX_ALARM_ID, 0);
    }

    public static void setKeyMaxAlarmId(int keyMaxAlarmId) {
        prefs.edit().putInt(KEY_MAX_ALARM_ID, keyMaxAlarmId).apply();
    }




}
