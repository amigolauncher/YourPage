package com.gionee.amisystem.yourpage.statistics;

import android.content.Context;

import com.gionee.amisystem.yourpage.statistics.bi.BIStatisticUtil;
import com.gionee.amisystem.yourpage.statistics.bi.StatisticParams;

import java.util.Map;

/**
 * Created by ke on 16-12-9.
 */
public class BIStatistics extends StatisticsChannel implements IStatistics {

    private BIStatisticUtil mBIStatisticUtil;

    @Override
    public void init(Context context) {
        mBIStatisticUtil = BIStatisticUtil.getInstance();
        mBIStatisticUtil.init(context);
    }

    @Override
    public void onResumeForStatistics(Context context) {

    }

    @Override
    public void onPauseForStatistics(Context context) {

    }

    @Override
    public void onStartForStatistics(Context context) {

    }

    @Override
    public void onStopStatistics(Context context) {
        mBIStatisticUtil.destroy();
    }

    @Override
    public void setReportUncaughtExceptionsForStatistics() {

    }

    @Override
    public void onErrorForStatistics(Context context, Throwable throwable) {

    }

    @Override
    public void setAssociateUserImprovementPlanForStatistics(Context context) {

    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel, long id) {
        // do nothing
    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel, Map<String, Object> map) {
        // do nothing
    }

    @Override
    public void onEventForStatistics(Context context, StatisticParams params, String eventLabel, Map<String, Object> map) {
        mBIStatisticUtil.statisticInfo(params);
    }
}
