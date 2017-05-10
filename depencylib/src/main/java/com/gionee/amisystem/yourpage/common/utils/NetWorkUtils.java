package com.gionee.amisystem.yourpage.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.gionee.amisystem.yourpage.common.APP;

public class NetWorkUtils {
    public static final String NETWORKTYPE_WAP = "WAP";
    public static final String NETWORKTYPE_2G = "2G";
    public static final String NETWORKTYPE_3G = "3G";
    public static final String NETWORKTYPE_4G = "4G";
    public static final String NETWORKTYPE_WIFI = "WIFI";
    public static final String NETWORKTYPE_UNKNOWN = "UNKONWN";

    public static String getMobileNetWorkType() {
        TelephonyManager telephonyManager = (TelephonyManager) APP.getAppContext().getSystemService(Context.TELEPHONY_SERVICE);

        //check if is wifi .
        ConnectivityManager connec = (ConnectivityManager) APP.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connec.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NETWORKTYPE_WIFI;
        }

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return NETWORKTYPE_2G;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return NETWORKTYPE_2G;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return NETWORKTYPE_2G;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return NETWORKTYPE_2G;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORKTYPE_3G;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORKTYPE_2G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORKTYPE_4G;
            default:
                return NETWORKTYPE_UNKNOWN;
        }
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) APP.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) APP.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}