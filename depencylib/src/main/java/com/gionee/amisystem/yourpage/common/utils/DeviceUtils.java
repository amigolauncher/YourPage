package com.gionee.amisystem.yourpage.common.utils;

import android.app.Service;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.gionee.amisystem.yourpage.common.APP;
import com.gionee.amisystem.yourpage.common.executors.YourPageExecutor;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * Created by orgcheng on 17-4-21.
 */

public class DeviceUtils {
    private static final String SP_KEY = "deviceInfo";
    private static String sDeviceId;
    private static String sAndroidId;
    private static String sMacAddress;

    public static void init() {
        YourPageExecutor.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                if (getDeviceInfo()) {
                    return;
                }
                // 1. device_id
                int retryCount = 5;
                while (TextUtils.isEmpty(sDeviceId) && retryCount > 0) {
                    sDeviceId = obtainDeviceId();
                    --retryCount;
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        LogUtils.d("init is interrupted");
                    }
                }

                // 2. android_id
                sAndroidId = obtainAndroidId();

                // 3. mac_address
                sMacAddress = obtainMacAddress();

                setDeviceInfo();
                LogUtils.d(SPUtils.get(SP_KEY, ""));
            }
        });
    }

    public static String getDeviceId() {
        return sDeviceId;
    }

    public static String getAndroidId() {
        return sAndroidId;
    }

    public static String getMacAddress() {
        return sMacAddress;
    }

    public static String obtainDeviceId() {
        String result = "";
        try {
            TelephonyManager tm = (TelephonyManager) APP.getAppContext().getSystemService(Service.TELEPHONY_SERVICE);
            result = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String obtainAndroidId() {
        String result = "9774d56d682e549c";
        try {
            result = Settings.System.getString(APP.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String obtainMacAddress() {
        String result = "02:00:00:00:00:00";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = getHardwareAddressForWLAN0();

            } else {
                WifiManager wifi = (WifiManager) APP.getAppContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                result = info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int dp2px(float dp) {
        final float scale = APP.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        final float scale = APP.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getScreenWidth() {
        return APP.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return APP.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    private static String getHardwareAddressForWLAN0() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iF = interfaces.nextElement();
            if (!"wlan0".equals(iF.getName())) {
                continue;
            }

            byte[] addr = iF.getHardwareAddress();
            if (addr == null || addr.length == 0) {
                continue;
            }

            StringBuilder buf = new StringBuilder();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            String mac = buf.toString();
            return mac;
        }
        return "02:00:00:00:00:00";
    }

    private static boolean getDeviceInfo() {
        if (SPUtils.contains(SP_KEY)) {
            String value = SPUtils.get(SP_KEY, "");
            if (TextUtils.isEmpty(value)) {
                return false;
            }

            String[] splits = value.split("&");
            if (splits == null || splits.length != 3) {
                return false;
            }
            sDeviceId = splits[0];
            sAndroidId = splits[1];
            sMacAddress = splits[2];
            return true;
        }
        return false;
    }

    private static void setDeviceInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(sDeviceId == null ? "" : sDeviceId)
                .append("&")
                .append(sAndroidId == null ? "" : sAndroidId)
                .append("&")
                .append(sMacAddress == null ? "" : sMacAddress);
        SPUtils.put(SP_KEY, sb.toString());
    }
}
