package com.gionee.amisystem.yourpage.statistics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;

import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.yourpage.statistics.bi.StatisticParams;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.UMAnalyticsConfig;

public class UmengStatistics extends StatisticsChannel implements IStatistics {

    private static final String ROM_APP_KEY = "57849a2f67e58e4c8900009a";

    @Override
    public void init(Context context) {
        UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(context, ROM_APP_KEY, getStatisticsChannel());
        LogUtils.d("UmengStatistics", "init");
        MobclickAgent.startWithConfigure(config);
        MobclickAgent.setDebugMode(false);
    }

    @Override
    public void onResumeForStatistics(Context context) {
        LogUtils.d("UmengStatistics", "onResumeForStatistics");
        MobclickAgent.onResume(context);
    }

    @Override
    public void onPauseForStatistics(Context context) {
        LogUtils.d("UmengStatistics", "onPauseForStatistics");
        MobclickAgent.onPause(context);
    }

    @Override
    public void onStartForStatistics(Context context) {
        LogUtils.d("UmengStatistics", "onStartForStatistics");
    }

    @Override
    public void onStopStatistics(Context context) {
        LogUtils.d("UmengStatistics", "onStopStatistics");
    }

    @Override
    public void setReportUncaughtExceptionsForStatistics() {
        LogUtils.d("UmengStatistics", "setReportUncaughtExceptionsForStatistics");
        MobclickAgent.setCatchUncaughtExceptions(true);
    }

    @Override
    public void onErrorForStatistics(Context context, Throwable throwable) {
        LogUtils.d("UmengStatistics", "onErrorForStatistics");
        MobclickAgent.reportError(context, throwable);
    }

    @Override
    public void setAssociateUserImprovementPlanForStatistics(Context context) {

    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel, long id) {
        LogUtils.d("UmengStatistics", "onEventForStatistics");
        // MobclickAgent.onEvent(context, eventId, (int) id);
    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel,
                                     Map<String, Object> map) {
        LogUtils.d("UmengStatistics", "onEventForStatistics");
        HashMap<String, String> umengStatisMap = new HashMap<String, String>();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            umengStatisMap.put(key, String.valueOf(value));
        }

        MobclickAgent.onEvent(context, eventId, umengStatisMap);
    }


    @Override
    public void onEventForStatistics(Context context, StatisticParams params, String eventLabel, Map<String, Object> map) {
        LogUtils.d("UmengStatistics", "onEventForStatistics");
        HashMap<String, String> umengStatisMap = new HashMap<String, String>();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            umengStatisMap.put(key, String.valueOf(value));
        }
        MobclickAgent.onEvent(context, params.eventName, umengStatisMap);
    }
}
