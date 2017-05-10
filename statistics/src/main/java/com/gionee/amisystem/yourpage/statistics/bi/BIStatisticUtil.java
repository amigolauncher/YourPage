package com.gionee.amisystem.yourpage.statistics.bi;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.yourpage.statistics.StatisticConstant;

/**
 * Created by lee on 11/2/15.
 */
public class BIStatisticUtil {
    public static final String TAG = "BIStatisticUtil";
    private StatisticInterface mStatistic;
    private Handler mHandler;
    private HandlerThread mHandlerThread;

    private BIStatisticUtil() {
        mHandlerThread = new HandlerThread("statistic");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    private static final class InnerClass {
        private static final BIStatisticUtil INSTANCE = new BIStatisticUtil();
    }

    public static BIStatisticUtil getInstance() {
        return InnerClass.INSTANCE;
    }

    public void init(Context context) {
        mStatistic = new RedcomStatistic(context);
    }

    public void statisticInfo(StatisticParams params) {
        StatisticRunnable runnable;
        if (params != null && isValidOpCode(params.op)) {
            runnable = mStatistic.createValidStatisticRunnable(params);
        } else {
            LogUtils.d(TAG, " error opcode only send failinfo or null params");
            runnable = mStatistic.createDBStatisticRunnable();
        }
        postStatistic(runnable);
    }

    private void postStatistic(StatisticRunnable runnable) {
        mHandler.post(runnable);
    }

    public void destroy() {
        mHandler.removeCallbacks(null);
        mStatistic.destroy();
    }

    private boolean isValidOpCode(int opCode) {
        return opCode == StatisticConstant.Op.OP_INIT || opCode == StatisticConstant.Op.OP_CLICK_CARD_CONTENT ||
                opCode == StatisticConstant.Op.OP_CLICK_DOWNLOAD_BUTTON || opCode == StatisticConstant.Op.OP_CLICK_REFRESH_BUTTON
                || opCode == StatisticConstant.Op.OP_CARD_SHOW || opCode == StatisticConstant.Op.OP_CLICK_MORE_BUTTON;
    }

}
