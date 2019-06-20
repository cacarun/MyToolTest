package com.mytooltest.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class ToolUtil {

    public static boolean isPackageInstalled(String packagename, Context context) {

        if(context == null) {
            return false;
        }

        if(packagename == null || packagename.isEmpty()) {
            return false;
        }

        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public static boolean isAppInstalled(String packageName, Context context) {

        if (context == null) {
            return false;
        }

        if (packageName == null || packageName.isEmpty()) {
            return false;
        }

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        if (pInfo != null) {

            for (int i = 0; i < pInfo.size(); i++) {

                String pn = pInfo.get(i).packageName;
                if (packageName.equals(pn)) {

                    return true;
                }
            }
        }
        return false;
    }

}
