package com.gionee.amisystem.depencylib;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

/**
 * Created by ke on 16-6-8.
 */
public class Utils {

    /**
     *
     * @param context
     * @return is network available
     */
    public static boolean isAvailableNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable() && info.getState() == NetworkInfo.State.CONNECTED;
        }
        return false;
    }

    /**
     *
     * @param context
     * @return is mobile network
     */
    public static boolean isMobileNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if(activeNetworkInfo != null) {
            if(activeNetworkInfo.getType() == 0 && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static String getProcessName(Context context, int pid) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : runningApps) {
            if (pid == info.pid) {
                return info.processName;
            }
        }
        return null;
    }

}
