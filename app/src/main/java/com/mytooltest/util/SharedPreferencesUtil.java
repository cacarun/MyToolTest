package com.mytooltest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.mytooltest.DTApplication;


public class SharedPreferencesUtil {

    private static final String SHARED_PREFERENCE_NAME ="local_shared_info";


//
//    private static final String FACEBOOK_POST_BONUS_TIMES = "facebookPostBonusTimes";
//
//    private static final String INVITE_MATCH_FIRST_TIME = "inviteMatchFirstTime";

    private static final SharedPreferences prefs = DTApplication.getInstance()
            .getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

    public static void toDeActive() {
        prefs.edit().clear().apply();
    }

//    public static int getFacebookPostBonusTimes() {
//        return prefs.getInt(FACEBOOK_POST_BONUS_TIMES, 0);
//    }
//
//    public static void increaseFacebookPostBonusTimes() {
//        int postTimes = getFacebookPostBonusTimes();
//        prefs.edit().putInt(FACEBOOK_POST_BONUS_TIMES, ++postTimes).apply();
//    }
//
//    public static long getInviteMatchFirstTime() {
//        return prefs.getLong(INVITE_MATCH_FIRST_TIME, 0);
//    }
//
//    public static void setInviteMatchFirstTime(long inviteMatchFirstTime) {
//        prefs.edit().putLong(INVITE_MATCH_FIRST_TIME, inviteMatchFirstTime).apply();
//    }

}
