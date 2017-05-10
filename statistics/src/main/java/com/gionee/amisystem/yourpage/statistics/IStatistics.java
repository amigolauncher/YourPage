package com.gionee.amisystem.yourpage.statistics;

import java.util.Map;

import android.content.Context;

import com.gionee.amisystem.yourpage.statistics.bi.StatisticParams;

public interface IStatistics {

    public void init(Context context);

    public void onResumeForStatistics(Context context);

    public void onPauseForStatistics(Context context);

    /**
     * just used for FlurryStatistics
     *
     * @param context
     */
    public void onStartForStatistics(Context context);

    /**
     * just used for FlurryStatistics
     *
     * @param context
     */
    public void onStopStatistics(Context context);

    public void setReportUncaughtExceptionsForStatistics();

    public void onErrorForStatistics(Context context, Throwable throwable);

    public void setAssociateUserImprovementPlanForStatistics(Context context);

    public void onEventForStatistics(Context context, String eventId, String eventLabel, long id);

    public void onEventForStatistics(Context context, String eventId, String eventLabel,
                                     Map<String, Object> map);

    public void onEventForStatistics(Context context, StatisticParams params, String eventLabel,
                                     Map<String, Object> map);

}
