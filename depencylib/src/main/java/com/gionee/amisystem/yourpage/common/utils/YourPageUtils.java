package com.gionee.amisystem.yourpage.common.utils;

import android.os.SystemClock;

/**
 * Created by orgcheng on 17-4-22.
 */

public class YourPageUtils {
    private static long mLastClickTime = 0;
    private static final int SPACE_TIME = 500;

    public static boolean isFastDoubleClick() {
        long time = SystemClock.elapsedRealtime();
        if (time - mLastClickTime <= SPACE_TIME) {
            return true;
        } else {
            mLastClickTime = time;
            return false;
        }
    }
}
