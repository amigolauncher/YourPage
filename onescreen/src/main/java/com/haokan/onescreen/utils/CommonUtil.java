package com.haokan.onescreen.utils;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class CommonUtil {
	private static final String TAG = "CommonUtil";

	/**
	 * 获取did v3.2之前采用IMEI或MAC或AndroidID的方式进行MD5
	 * v3.3之后采用IMEI+AndroidID+MAC的方式进行MD5
	 *
	 * @param context
	 * @return
	 */
	public static String getDid(Context context) {
		try {
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			String did = sharedPreferences.getString("user_did", "");
			if (TextUtils.isEmpty(did)) {
				did = getIMEI(context) + getAndroid_ID(context) + getMAC(context);
				did = did.replace("null", "");
				did = SecurityUtil.md5(did);
				if (!TextUtils.isEmpty(did)) {
					sharedPreferences.edit().putString("user_did", did).commit();
				}
			}

			return did;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取MAC地址android.os.Build.VERSION.SDK_INT
	 * 需权限android.Manifest.permission.ACCESS_WIFI_STATE
	 *
	 * @return
	 */
	public static String getMAC(Context context) {
		if (checkPermission(context, android.Manifest.permission.ACCESS_WIFI_STATE)) {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			String macAddress = info.getMacAddress();
			if (macAddress == null) {
				return "";
			} else {
				return macAddress;
			}
		} else {
			return "";
		}
	}

	/**
	 * 获取Android_Id
	 *
	 * @param context
	 * @return
	 */
	public static String getAndroid_ID(Context context) {

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String android_id = sharedPreferences.getString("android_id", "");

		if (TextUtils.isEmpty(android_id)) {
			android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
			if (!TextUtils.isEmpty(android_id)) {
				sharedPreferences.edit().putString("android_id", android_id).commit();
			}
		}
		return android_id;
	}

	/**
	 * 获取IMEI号
	 *
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (imei != null) {
				return imei;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/**
	 * 查询应用是否具有某种权限
	 *
	 * @param context
	 * @param permissionStr
	 * @return
	 */
	public static boolean checkPermission(Context context, String permissionStr) {
		PackageManager pm = context.getPackageManager();
		boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(permissionStr, context.getPackageName()));
		return permission;
	}
	
    /**
     * 获取应用程序版本的名称，清单文件中的versionName属性
     */
    public static String getLocalVersionName(Context c) {
        try {
            PackageManager manager = c.getPackageManager();
            PackageInfo info = manager.getPackageInfo(c.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }

}
