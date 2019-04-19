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

//    public static final String KEY_PUSH_INFO_ARRAY = "KEY_PUSH_INFO_ARRAY";


    public static int getKeyMaxAlarmId() {
        return prefs.getInt(KEY_MAX_ALARM_ID, 0);
    }

    public static void setKeyMaxAlarmId(int keyMaxAlarmId) {
        prefs.edit().putInt(KEY_MAX_ALARM_ID, keyMaxAlarmId).apply();
    }


//    public static SparseArray<String> getKeyPushInfoArray(int pushKey) {
//        return null;
//    }
//
//    public static SparseArray<String> getKeyPushInfoArray() {
//        String pushInfo = prefs.getString(KEY_PUSH_INFO_ARRAY, "");
//        if (TextUtils.isEmpty(pushInfo)) {
//            return null;
//        }
//
//        Gson gson = new Gson();
//        java.lang.reflect.Type type = new TypeToken<SparseArray<String>>(){}.getType();
//        try {
//            return gson.fromJson(pushInfo, type);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public static void updateKeyPushInfoArray(NotifyObject obj) {
//        if (obj == null) {
//            return;
//        }
//
//        SparseArray<String> pushInfoArray = getKeyPushInfoArray();
//        try {
//            if (pushInfoArray == null) {
//                // 第一次存放
//                pushInfoArray = new SparseArray<>();
//            }
//
//            Gson gson = new Gson();
//            int infoKey = obj.type;
//            pushInfoArray.put(infoKey, gson.toJson(obj));
//
//            prefs.edit().putString(KEY_PUSH_INFO_ARRAY, gson.toJson(pushInfoArray)).apply();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
