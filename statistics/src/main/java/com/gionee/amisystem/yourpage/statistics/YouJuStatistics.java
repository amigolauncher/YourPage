package com.gionee.amisystem.yourpage.statistics;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.yourpage.statistics.bi.StatisticParams;

public class YouJuStatistics extends StatisticsChannel implements IStatistics {
    private static final String ROM_YOUJU_AGENT_PACKAGE = "com.gionee.youju.statistics.sdk.YouJuAgent";
    private static final String STATISTICS_ID = "7891965124B2FD18C184E0F6D9616961";
    private Class<?> mClazz;
    private Map<String, Object> mDataMap = null;

    public YouJuStatistics() {
        try {
            mClazz = Class.forName(ROM_YOUJU_AGENT_PACKAGE);
            LogUtils.d("YouJuStatistics", "YouJuStatistics");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Context context) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("init", Context.class, String.class, String.class);
                method.invoke(mClazz, context, STATISTICS_ID, getStatisticsChannel());
                LogUtils.d("YouJuStatistics", "init");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResumeForStatistics(Context context) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("onResume", Context.class);
                method.invoke(mClazz, context);
                LogUtils.d("YouJuStatistics", "onResumeForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPauseForStatistics(Context context) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("onPause", Context.class);
                method.invoke(mClazz, context);

                LogUtils.d("YouJuStatistics", "onPauseForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setReportUncaughtExceptionsForStatistics() {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("setReportUncaughtExceptions", Boolean.TYPE);
                method.invoke(mClazz, false);

                LogUtils.d("YouJuStatistics", "setReportUncaughtExceptionsForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorForStatistics(Context context, Throwable throwable) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("onError", Context.class, Throwable.class);
                method.invoke(mClazz, context, throwable);

                LogUtils.d("YouJuStatistics", "onErrorForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setAssociateUserImprovementPlanForStatistics(Context context) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("setAssociateUserImprovementPlan", Context.class, Boolean.TYPE);
                method.invoke(mClazz, context, true);

                LogUtils.d("YouJuStatistics", "setAssociateUserImprovementPlanForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel, long id) {
        if (mClazz != null) {
            if (null == mDataMap) {
                mDataMap = new HashMap<String, Object>(1);
            }
            if (!mDataMap.isEmpty()) {
                mDataMap.clear();
            }
            mDataMap.put(STATISTICS_ID, id);
            try {
                Method method = mClazz.getMethod("onEvent", Context.class, String.class, String.class,
                        Map.class);
                method.invoke(mClazz, context, eventId, eventLabel, mDataMap);

                LogUtils.d("YouJuStatistics", "onEventForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEventForStatistics(Context context, String eventId, String eventLabel,
                                     Map<String, Object> map) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("onEvent", Context.class, String.class, String.class,
                        Map.class);
                method.invoke(mClazz, context, eventId, eventLabel, map);
                LogUtils.d("YouJuStatistics", "onEventForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartForStatistics(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopStatistics(Context context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEventForStatistics(Context context, StatisticParams params, String eventLabel, Map<String, Object> map) {
        if (mClazz != null) {
            try {
                Method method = mClazz.getMethod("onEvent", Context.class, String.class, String.class,
                        Map.class);
                method.invoke(mClazz, context, params.eventName, eventLabel, map);
                LogUtils.d("YouJuStatistics", "onEventForStatistics");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
