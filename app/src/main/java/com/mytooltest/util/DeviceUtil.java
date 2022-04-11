package com.mytooltest.util;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import com.mytooltest.DTApplication;

public class DeviceUtil {

    private static final String TAG = "DeviceUtil";

    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        return displaysMetrics;
    }

    /** dp转px */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /** px转dp */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static String getDeviceId() {

        WifiManager wifiMgr = (WifiManager) DTApplication.getInstance().getSystemService(Context.WIFI_SERVICE);

        String macAddress = "";

        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        if (wifiInfo != null) {
            macAddress = wifiMgr.getConnectionInfo().getMacAddress();
            Log.d(TAG, "mac address : " + macAddress);
        } else {

            Log.e(TAG, "WifiInfo is null can't get wifi address through wifiINfo");
        }

        // get IMEI
        String IMEI = "";
        if (IMEI != null) {
            macAddress += IMEI;
            Log.d(TAG, "IMEI : " + IMEI);
        } else {
            Log.e(TAG, "can't get IMEI");
        }

        // get Android id
        String android_id = Settings.Secure.getString(DTApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (android_id != null) {
            macAddress += android_id;
            Log.d(TAG, "android id: " + android_id);
        } else {
            Log.e(TAG, "can't get android id");
        }

        Log.d(TAG, String.format("mac address is %s", macAddress));

        return macAddress;
    }
}
