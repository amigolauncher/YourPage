package com.gionee.amisystem.yourpage.statistics.bi;


import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.depencylib.http.HttpUtils;

import java.util.List;


/**
 * Created by lee on 11/2/15.
 */
public class StatisticRunnable implements Runnable {
    public static final String TAG = "StatisticRunnable";

    public static final Object LOCK = new Object();

    private String mBaseUrl;
    private String mParams;
    private StatisticDatabase mDb;
    private int mSourceFrom;

    private boolean mOnlySendFailure = false;

    public StatisticRunnable(StatisticDatabase db, String baseUrl, String params, int sourceFrom) {
        mBaseUrl = baseUrl;
        mParams = params;
        mSourceFrom = sourceFrom;
        mDb = db;
    }

    public StatisticRunnable(StatisticDatabase db) {
        mOnlySendFailure = true;
        mDb = db;
    }

    public void run() {
        LogUtils.d(TAG, "send statistics to bi server : " +
                "url " + mBaseUrl +
                ",mParams " + mParams +
                " is only send failure " + mOnlySendFailure);

        if (!mOnlySendFailure) {
            boolean isSuccess = HttpUtils.sendBiStatisticWithNoReply(mBaseUrl, mParams,
                    HttpUtils.SO_TIMEOUT_MILLS, HttpUtils.CONNECT_TIMEOUT_MILLS);
            LogUtils.d(TAG, "send statistic result : " + isSuccess);
            if (!isSuccess) {
                LogUtils.d(TAG, "send fail , insert in to db");
                mDb.insert(new StatisticBiInfo(mSourceFrom, mBaseUrl, mParams));
            }
        }
        executeFailureStatistic();
    }

    private void executeFailureStatistic() {
        synchronized (LOCK) {
            List<StatisticBiInfo> list = mDb.getAllFailureInfo(StatisticBiInfo.SOURCE_ALL);
            LogUtils.d(TAG, "send fail statistics , list " + list);
            if (list.size() > 0) {
                for (StatisticBiInfo info : list) {
                    String url = getUrl(info);
                    boolean failSendSuccess = false;
                    if (url != null) {
                        failSendSuccess = HttpUtils.sendBiStatisticWithNoReply(url, info.getParams(),
                                HttpUtils.SO_TIMEOUT_MILLS, HttpUtils.CONNECT_TIMEOUT_MILLS);
                    }
                    if (failSendSuccess || url == null) {
                        LogUtils.d(TAG, "send success, remove info, " + info);
                        mDb.deleteById(info.getId());
                    }
                }
            }
        }
    }

    private String getUrl(StatisticBiInfo info) {
        String url = info.getUrl();
        if (url == null || url.trim().length() == 0) {
            return null;
        }
        return url;
    }


}
