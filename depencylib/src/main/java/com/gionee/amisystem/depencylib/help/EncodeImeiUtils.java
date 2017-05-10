package com.gionee.amisystem.depencylib.help;

import java.util.concurrent.TimeUnit;


import android.app.Service;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


/**
 * we should use a Thread to get the device id for it may need 10 second to get the value
 *
 * @author dandy
 */
public class EncodeImeiUtils {

    private static final String TAG = "EncodeImeiUtils";
    private static final String IMEI_RESULT_GETTING = "imei_result_getting";
    private String mEncodeIMEI = "";

    private static final int MAX_RETRY_COUNT = 10;
    private static boolean sFirstTimeRun = true;
    private static final String IMEI = "imei";

    private EncodeImeiUtils() {
    }

    private static class InnerClass {
        private static final EncodeImeiUtils INSTANCE = new EncodeImeiUtils();
    }

    public static EncodeImeiUtils getInstance() {
        return InnerClass.INSTANCE;
    }

    public void initImei(Context context) {
        Log.d(TAG, "initImei ");
        initEncodeImei(context);
    }

    private void initEncodeImei(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isEncodeImeiGot()) {
                    Log.d(TAG, "sEncodeIMEI is not null, no need to get TelephonyManager " + mEncodeIMEI);
                    return;
                }

                mEncodeIMEI = IMEI_RESULT_GETTING;
                long beginTime = System.currentTimeMillis();
                Log.d(TAG, "initEncodeImei begin TelephonyManager.getDeviceId");
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                try {
                    String deviceId = tm.getDeviceId();
                    LogUtils.d(TAG, "deviceId = " + deviceId);
                    int retryCount = 0;
                    while (TextUtils.isEmpty(deviceId) && retryCount <= MAX_RETRY_COUNT && sFirstTimeRun) {
                        deviceId = tm.getDeviceId();
                        retryCount++;
                        if (TextUtils.isEmpty(deviceId)) {
                            try {
                                TimeUnit.MILLISECONDS.sleep(500);
                            } catch (InterruptedException e) {
                                Log.d(TAG, "init imei interrupt ");
                            }
                        }
                    }
                    sFirstTimeRun = false;

                    if (isEncodeImeiGot()) {
                        Log.d(TAG, "sEncodeIMEI is not null ,no need to get value " + mEncodeIMEI);
                    } else {
                        mEncodeIMEI = GNEncodeIMEIUtils.get(deviceId);
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                    Log.d(TAG, "init encode imei fail");
                }

                long deltaEncodeIMEITime = System.currentTimeMillis() - beginTime;
                Log.d(TAG, "deltaEncodeIMEITime=" + deltaEncodeIMEITime);
                LogUtils.d(TAG, "mEncodeIMEI = " + mEncodeIMEI);
            }
        }).start();

    }

    private boolean isEncodeImeiGot() {
        return !TextUtils.isEmpty(mEncodeIMEI)
                && !IMEI_RESULT_GETTING.equals(mEncodeIMEI)
                && !TextUtils.isEmpty(GNEncodeIMEIUtils.decrypt(mEncodeIMEI));
    }

    /**
     * get an encode IMEI number
     * <p/>
     * <pre>
     * if mEncodeIMEI == null ,return  the null encode IMEI,
     * in fact mEncodeIMEI won't be null ,add this condition just in case;
     *
     * if mEncodeIMEI=="" or mEncodeIMEI==IMEI_RESULT_GETTING ,
     * it means we are not got the value and we are on the way to get the value;
     * in this case we'd better add a listener by call EncodeImeiUtils.getInstance().addOnEncodeImeiOkListener(EncodeImeiListener)
     * so that we can update the value if we get the mEncodeIMEI value from the parameter encodeIMEI of the method onEncodeIMEIOk
     *
     * else it means mEncodeIMEI has the right value ,just return the value.
     * </pre>
     *
     * @param context
     * @return
     */
    public String getEncodeImei(Context context) {
        LogUtils.d(TAG, "getEncodeImei");
        if (null == mEncodeIMEI) {// null object,can not judge by GnUtils.isNull()
            Log.d(TAG, "getEncodeImei sEncodeIMEI==null");
            return getNullEncodeImei();
        }
        LogUtils.d(TAG, "mEncodeIMEI = " + mEncodeIMEI);
        if (IMEI_RESULT_GETTING.equals(mEncodeIMEI)
                || "".equals(mEncodeIMEI)
                || TextUtils.isEmpty(GNEncodeIMEIUtils.decrypt(mEncodeIMEI))) { // getting
            Log.d(TAG, "getEncodeImei sEncodeIMEI is getting");
            initEncodeImei(context);
            return IMEI_RESULT_GETTING;
        }
        LogUtils.d(TAG, "getEncodeImei sEncodeIMEI=" + mEncodeIMEI);
        return mEncodeIMEI;
    }

    public String getNullEncodeImei() {
        return GNEncodeIMEIUtils.get(null);
    }

    public boolean isEncodeImeiGetting(String encodeImei) {
        if (encodeImei.equals(IMEI_RESULT_GETTING)) {
            Log.d(TAG, "isEncodeImeiGetting=true");
            return true;
        }
        return false;
    }

}
