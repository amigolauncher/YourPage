package com.gionee.amisystem.yourpage.statistics;


import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;

import com.gionee.amisystem.depencylib.IYourpageService;
import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.yourpage.statistics.bi.StatisticParams;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsUtil {
    private static final String TAG = "StatisticsUtil";
    private static final boolean ISDEBUG = true;


    private static final String STATISTICS_EVENT_LABEL_ROM = "amisystem-rom";

    private static final String PARAMETERS_KEY_DEFAULT = "default_parameter";
    private static final String PARAMETERS_VALUE_DEFAULT = "1";


    private static final Map<String, Object> STATISTICS_MAP = new HashMap<String, Object>();


    private static void executeSendStatisticsData(Context context, Map<String, Object> map,
                                                  String statisticsEvent) {
        String eventLabel = STATISTICS_EVENT_LABEL_ROM;
        StatisticsFactory.getStatisticsServer().onEventForStatistics(context, statisticsEvent, eventLabel, map);
    }

    private static void executeSendStatisticsData(Context context, Map<String, Object> map,
                                                  StatisticParams params) {
        String eventLabel = STATISTICS_EVENT_LABEL_ROM;
        StatisticsFactory.getStatisticsServer().onEventForStatistics(context, params, eventLabel, map);
    }

    private static void clearStatisticsMap() {
        if (!STATISTICS_MAP.isEmpty()) {
            STATISTICS_MAP.clear();
        }
    }

    private static void printLog(String msg) {
        if (ISDEBUG) {
            LogUtils.i(TAG, msg);
        }
    }

    private static String getCardViewTitle(Context context, int type) {
        String resName = "title_of_" + type;
        String result = null;
        try {
            int id = getStringId(context, resName);
            result = context.getString(id);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static int getStringId(Context context, String paramString) {
        return context.getResources().getIdentifier(paramString, "string", context.getPackageName());
    }

    public static void Statistics_Click_Card_Times(Context context, IYourpageService service, int cardType) {
        Statistics_Event(context, service, StatisticConstant.Op.OP_CLICK_CARD_CONTENT,
                StatisticConstant.EVENT_YOURPAGE_CARD_CLICK_TIMES, cardType);
    }

    public static void Statistics_Click_Card_Refresh_Button_Times(Context context, IYourpageService service, int cardType) {
        Statistics_Event(context, service, StatisticConstant.Op.OP_CLICK_REFRESH_BUTTON,
                StatisticConstant.EVENT_YOURPAGE_CARD_CLICK_REFRESH_BUTTON_TIMES, cardType);
    }

    public static void Statistics_Click_Card_More_Button_Times(Context context, IYourpageService service, int cardType) {
        Statistics_Event(context, service, StatisticConstant.Op.OP_CLICK_MORE_BUTTON,
                StatisticConstant.EVENT_YOURPAGE_CARD_CLICK_MORE_BUTTON_TIMES, cardType);
    }

    public static void Statistics_Click_Card_Download_Apk_Times(Context context, IYourpageService service, int cardType) {
        Statistics_Event(context, service, StatisticConstant.Op.OP_CLICK_DOWNLOAD_BUTTON,
                StatisticConstant.EVENT_YOURPAGE_CARD_CLICK_DOWNLOAD_APK_TIMES, cardType);
    }

    private static void Statistics_Event(Context context, IYourpageService service, int op, String eventName, int cardType) {
        String cardTitle = getCardViewTitle(context, cardType);
        StatisticParams params = new StatisticParams();
        params.op = op;
        params.eventName = eventName;
        params.cardId = cardType;
        params.cardName = cardTitle;
        Statistics_Event_Common(context, service, params);
    }

    public static void Statistics_Event_Common(Context context, IYourpageService service, StatisticParams params) {
        String processName = getProcessName(context, android.os.Process.myPid());
        if (service != null) {
            if (processName != null && processName.contains(context.getPackageName())) {
                if (!processName.equals(context.getPackageName())) {
                    StatisticsFactory.getStatisticsServer().init(context);
                }
                Statistics_Event(context, params);
            } else {
                Statistics_Event_Remote(service, params);
            }
        } else {
            if (processName != null && processName.equals(context.getPackageName())) {
                Statistics_Event(context, params);
            }
        }
    }

    public static void Statistics_Event_Common(Context context, IYourpageService service, String eventName, int cardType) {
        String cardTitle = getCardViewTitle(context, cardType);
        String processName = getProcessName(context, android.os.Process.myPid());
        if (service != null) {
            if (processName != null && processName.contains(context.getPackageName())) {
                if (!processName.equals(context.getPackageName())) {
                    StatisticsFactory.getStatisticsServer().init(context);
                }
                Statistics_Event(context, cardTitle + "_" + eventName);
            } else {
                Statistics_Event_Remote(service, cardTitle + "_" + eventName);
            }
        } else {
            if (processName != null && processName.equals(context.getPackageName())) {
                Statistics_Event(context, cardTitle + "_" + eventName);
            }
        }
    }

    public static void Statistics_Event_Common(Context context, IYourpageService service, String eventName) {
        String processName = getProcessName(context, android.os.Process.myPid());
        if (service != null) {
            if (processName != null && processName.contains(context.getPackageName())) {
                if (!processName.equals(context.getPackageName())) {
                    StatisticsFactory.getStatisticsServer().init(context);
                }
                Statistics_Event(context, eventName);
            } else {
                Statistics_Event_Remote(service, eventName);
            }
        } else {
            if (processName != null && processName.equals(context.getPackageName())) {
                Statistics_Event(context, eventName);
            }
        }
    }

    public static void onResume(Context context) {
        StatisticsFactory.getStatisticsServer().onResumeForStatistics(context);
    }

    public static void onPause(Context context) {
        StatisticsFactory.getStatisticsServer().onPauseForStatistics(context);
    }

    private static void Statistics_Event(Context context, String eventKey) {
        clearStatisticsMap();
        STATISTICS_MAP.put(PARAMETERS_KEY_DEFAULT, PARAMETERS_VALUE_DEFAULT);
        executeSendStatisticsData(context, STATISTICS_MAP, eventKey);
        printLog(eventKey + " : " + STATISTICS_MAP);
    }

    private static void Statistics_Event_Remote(IYourpageService service, String eventKey) {
        try {
            LogUtils.d(TAG, "Statistics_Event_Remote : eventKey = " + eventKey);
            service.onEventForStatistics(eventKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Statistics_Event(Context context, StatisticParams params) {
        clearStatisticsMap();
        STATISTICS_MAP.put(PARAMETERS_KEY_DEFAULT, params.cardName);
        executeSendStatisticsData(context, STATISTICS_MAP, params);
        printLog(params.eventName + " : " + STATISTICS_MAP + ", params = " + params);
    }

    private static void Statistics_Event_Remote(IYourpageService service, StatisticParams params) {
        try {
            LogUtils.d(TAG, "Statistics_Event_Remote : eventKey = " + params);
            Gson gson = new Gson();
            service.onEventForStatisticsForParams(gson.toJson(params));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Statistics_Event_For_Value_Common(Context context, IYourpageService service,
                                                         String eventName, String value, int cardType) {
        String cardTitle = getCardViewTitle(context, cardType);
        String processName = getProcessName(context, android.os.Process.myPid());

        if (service != null) {
            if (processName != null && processName.contains(context.getPackageName())) {
                if (!processName.equals(context.getPackageName())) {
                    StatisticsFactory.getStatisticsServer().init(context);
                }
                Statistics_Event_For_Value(context, cardTitle + "_" + eventName, value);
            } else {
                Statistics_Event_For_Value_Remote(service, cardTitle + "_" + eventName, value);
            }
        } else {
            if (processName != null && processName.equals(context.getPackageName())) {
                Statistics_Event_For_Value(context, cardTitle + "_" + eventName, value);
            }
        }
    }

    public static void Statistics_Event_For_Value_Common(Context context, IYourpageService service,
                                                         String eventName, String value) {
        String processName = getProcessName(context, android.os.Process.myPid());
        if (service != null) {
            if (processName != null && processName.contains(context.getPackageName())) {
                if (!processName.equals(context.getPackageName())) {
                    StatisticsFactory.getStatisticsServer().init(context);
                }
                Statistics_Event_For_Value(context, eventName, value);
            } else {
                Statistics_Event_For_Value_Remote(service, eventName, value);
            }
        } else {
            if (processName != null && processName.equals(context.getPackageName())) {
                Statistics_Event_For_Value(context, eventName, value);
            }
        }
    }


    private static void Statistics_Event_For_Value(Context context, String eventKey, String value) {
        clearStatisticsMap();
        STATISTICS_MAP.put(PARAMETERS_KEY_DEFAULT, value);
        executeSendStatisticsData(context, STATISTICS_MAP, eventKey);
        printLog(eventKey + " : " + STATISTICS_MAP);
    }

    private static void Statistics_Event_For_Value_Remote(IYourpageService service, String eventKey, String value) {
        try {
            LogUtils.d(TAG, "Statistics_Event_For_Value_Remote : eventKey = " + eventKey + ", value = " + value);
            service.onEventForStatisticsForValue(eventKey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getProcessName(Context context, int pid) {
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
