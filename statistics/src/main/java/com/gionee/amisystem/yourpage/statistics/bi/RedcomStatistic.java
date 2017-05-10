package com.gionee.amisystem.yourpage.statistics.bi;

import android.content.Context;

import com.gionee.amisystem.depencylib.help.CommonTools;
import com.gionee.amisystem.depencylib.help.LogUtils;
import com.gionee.amisystem.depencylib.help.NetWorkUtils;
import com.gionee.amisystem.yourpage.statistics.StatisticConstant;

import java.net.URLEncoder;

/**
 * Created by lee on 11/17/15.
 */
public class RedcomStatistic implements StatisticInterface {
    private static final String TAG = "RedcomStatistic";
    private static final String TEST_BASE_URL = "http://test1.gionee.com/wantapp/act";
    private static String sBaseURL = "http://red.gionee.com/wantapp/act";

    private Context mContext;
    private StatisticDatabase mDb;

    public RedcomStatistic(Context context) {
        mContext = context;
        mDb = new StatisticDatabase(context);
        setUrlIfTestEnvironment();
    }

    private static void setUrlIfTestEnvironment() {
        if (CommonTools.isTestEnvironment()) {
            sBaseURL = TEST_BASE_URL;
        }
    }

    @Override
    public StatisticRunnable createValidStatisticRunnable(StatisticParams params) {
        String urlParams = getParams(params.op, params);
        LogUtils.d(TAG, "createStatisticRunnable , params.op " + params.op + ", urlParams " + urlParams);
        StatisticRunnable runnable = new StatisticRunnable(mDb, sBaseURL, urlParams, StatisticConstant.SOURCE_FORM_BI);
        return runnable;
    }

    @Override
    public StatisticRunnable createDBStatisticRunnable() {
        return new StatisticRunnable(mDb);
    }

    private String getParams(int op, StatisticParams params) {
        StringBuilder sb = new StringBuilder();
        appendCommon(sb);

        switch (op) {
            case StatisticConstant.Op.OP_CLICK_CARD_CONTENT:
            case StatisticConstant.Op.OP_CLICK_REFRESH_BUTTON:
            case StatisticConstant.Op.OP_CLICK_DOWNLOAD_BUTTON:
            case StatisticConstant.Op.OP_CLICK_MORE_BUTTON:
            case StatisticConstant.Op.OP_CARD_SHOW:
                if (params.cardId != StatisticParams.INVALID) {
                    sb.append(StatisticConstant.Param.CARD_ID).append(StatisticConstant.TAG_EQUALS).append(params.cardId);
                    sb.append(StatisticConstant.TAG_YU);
                }
                if (params.cardName != null) {
                    String encodeName = params.cardName;
                    try {
                        encodeName = URLEncoder.encode(encodeName, StatisticConstant.ENCODE);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "get ParamsString is error " + e);
                    }
                    sb.append(StatisticConstant.Param.NAME).append(StatisticConstant.TAG_EQUALS).append(encodeName);
                    sb.append(StatisticConstant.TAG_YU);
                }
                break;
            case StatisticConstant.Op.OP_INIT:
                break;
        }
        sb.append(StatisticConstant.Param.OP_TYPE).append(StatisticConstant.TAG_EQUALS).append(op);
        return sb.toString();
    }

    private void appendCommon(StringBuilder sb) {
        String model = CommonTools.getDeviceModel();
        String mNetType = NetWorkUtils.getMobileNetWorkType(mContext);
        String rom = CommonTools.getGioneeRomVersion();
        String androidVersion = CommonTools.getAndroidVersion();
        String imei = CommonTools.getEncodeIMEI(mContext);
        String appVersion = CommonTools.getVersionName(mContext);

        sb.append(StatisticConstant.Param.IMEI).append(StatisticConstant.TAG_EQUALS)
                .append(imei).append(StatisticConstant.TAG_YU);

        sb.append(StatisticConstant.Param.NET_TYPE).append(StatisticConstant.TAG_EQUALS)
                .append(mNetType).append(StatisticConstant.TAG_YU);

        sb.append(StatisticConstant.Param.ANDROID_VERSION).append(StatisticConstant.TAG_EQUALS)
                .append(androidVersion).append(StatisticConstant.TAG_YU);

        sb.append(StatisticConstant.Param.ROM_VERSION).append(StatisticConstant.TAG_EQUALS)
                .append(rom).append(StatisticConstant.TAG_YU);

        sb.append(StatisticConstant.Param.YOURPAGE_VERSION).append(StatisticConstant.TAG_EQUALS).append(appVersion);
        sb.append(StatisticConstant.TAG_YU);

        sb.append(StatisticConstant.Param.MODEL).append(StatisticConstant.TAG_EQUALS).append(model);

        sb.append(StatisticConstant.TAG_YU);
    }

    @Override
    public void destroy() {
        mDb.closeDb();
        mDb = null;
    }
}
