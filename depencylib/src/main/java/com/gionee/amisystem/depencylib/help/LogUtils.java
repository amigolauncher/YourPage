package com.gionee.amisystem.depencylib.help;


import android.util.Log;

/**
 * Created by ke on 16-12-15.
 */
public class LogUtils {

    private static final String TAG = "yourpage";

    public static void d(String tag, String log) {
        Log.d(TAG, ":" + tag + ", " + log);
    }

    public static void e(String tag, String log) {
        Log.e(TAG, ":" + tag + ", " + log);
    }

    public static void i(String tag, String log) {
        Log.i(TAG, ":" + tag + ", " + log);
    }

    public static void w(String tag, String log) {
        Log.w(TAG, ":" + tag + ", " + log);
    }

    public static void printTrace(String tag) {
        Log.d(TAG, ":" + tag + " begin");
        Thread.dumpStack();
        Log.d(TAG, ":" + tag + " end");
    }

}
