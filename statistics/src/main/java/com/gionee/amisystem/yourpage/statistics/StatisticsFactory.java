package com.gionee.amisystem.yourpage.statistics;

import java.io.File;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.gionee.amisystem.yourpage.statistics.bi.StatisticParams;


public class StatisticsFactory implements IStatistics {
    private YouJuStatistics mYouJuStatistics = null;
    private UmengStatistics mUmengStatistics = null;
    private BIStatistics mBiStatistics = null;

    private boolean mSupportStatistic = true;
    private boolean mSupporthirdPartyStatistics = true;
    private boolean mHasInit = false;

    private static final String STATISTIC_LOCK_PATH = Environment.getExternalStorageDirectory().toString()
            + "/youju1234567890";

    private StatisticsFactory() {
        mYouJuStatistics = new YouJuStatistics();
        mUmengStatistics = new UmengStatistics();
        mBiStatistics = new BIStatistics();
    }

    private static final class InnerClass {
        private static final StatisticsFactory INSTANCE = new StatisticsFactory();
    }

    public static StatisticsFactory getStatisticsServer() {
        return InnerClass.INSTANCE;
    }

    /**
     * Statistics Test Mode
     *
     * @param context
     */
    private void initIsSupportStatistic(Context context) {
        File file = new File(STATISTIC_LOCK_PATH);
        if (file.exists()) {
            mSupportStatistic = false;
            Log.i("StatisticsFactory", " mSupportStatistic = " + mSupportStatistic);
        }
    }

    /**
     * Unauthorized network,Can not consume traffic for Gionee Rom.
     * YouJu ROM SDK does not consume our application traffic in Gionee Rom.
     */
    private void initIsSupporthirdPartyStatistics(Context context) {

    }

    @Override
    public void init(Context context) {
        if (mHasInit) {
            return;
        }
        initIsSupportStatistic(context);
        initIsSupporthirdPartyStatistics(context);
        mHasInit = true;
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.init(context);
        mBiStatistics.init(context);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.init(context);
    }

    @Override
    public void onResumeForStatistics(Context context) {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.onResumeForStatistics(context);
        mBiStatistics.onResumeForStatistics(context);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.onResumeForStatistics(context);

    }

    @Override
    public void onPauseForStatistics(Context context) {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.onPauseForStatistics(context);
        mBiStatistics.onPauseForStatistics(context);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.onPauseForStatistics(context);
    }

    @Override
    public void onStartForStatistics(Context context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStopStatistics(Context context) {
        // TODO Auto-generated method stub
        mBiStatistics.onStopStatistics(context);
    }

    @Override
    public void setReportUncaughtExceptionsForStatistics() {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.setReportUncaughtExceptionsForStatistics();
        mBiStatistics.setReportUncaughtExceptionsForStatistics();
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.setReportUncaughtExceptionsForStatistics();

    }

    @Override
    public void onErrorForStatistics(Context context, Throwable throwable) {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.onErrorForStatistics(context, throwable);
        mBiStatistics.onErrorForStatistics(context, throwable);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.onErrorForStatistics(context, throwable);
    }

    @Override
    public void setAssociateUserImprovementPlanForStatistics(Context context) {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.setAssociateUserImprovementPlanForStatistics(context);
        mBiStatistics.setAssociateUserImprovementPlanForStatistics(context);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.setAssociateUserImprovementPlanForStatistics(context);

    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel, long id) {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.onEventForStatistics(context, eventId, eventLabel, id);
        mBiStatistics.onEventForStatistics(context, eventId, eventLabel, id);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.onEventForStatistics(context, eventId, eventLabel, id);
    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel,
                                     Map<String, Object> map) {
        // TODO Auto-generated method stub
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.onEventForStatistics(context, eventId, eventLabel, map);
        mBiStatistics.onEventForStatistics(context, eventId, eventLabel, map);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.onEventForStatistics(context, eventId, eventLabel, map);
    }

    @Override
    public void onEventForStatistics(Context context, StatisticParams params, String eventLabel, Map<String, Object> map) {
        if (!mSupportStatistic) {
            return;
        }
        mYouJuStatistics.onEventForStatistics(context, params, eventLabel, map);
        mBiStatistics.onEventForStatistics(context, params, eventLabel, map);
        if (!mSupporthirdPartyStatistics) {
            return;
        }
        mUmengStatistics.onEventForStatistics(context, params, eventLabel, map);
    }
}
