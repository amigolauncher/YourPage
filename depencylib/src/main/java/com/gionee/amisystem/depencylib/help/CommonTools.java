package com.gionee.amisystem.depencylib.help;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.gionee.amisystem.depencylib.Constant;

import java.io.File;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lee on 11/23/15.
 */
public class CommonTools {
    public static String getDeviceModel() {
        String model = getSystemProp(Constant.PROP_MODEL, Constant.NULL);
        if (!Constant.NULL.equals(model)) {
            model = model.trim().replaceAll(" ", "+");
        }
        return model;
    }

    public static String getEncodeIMEI(Context context) {
        return EncodeImeiUtils.getInstance().getEncodeImei(context);
    }

    public static String getSystemProp(String key, String defVal) {
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class, String.class);
            Object object = method.invoke(null, key, defVal);
            return (String) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static String getGioneeRomVersion() {
        String version = getSystemProp(Constant.PROP_GNROMVER, Constant.NULL);
        if (!Constant.NULL.equals(version)) {
            Matcher matcher = Pattern.compile("[0-9]").matcher(version);
            if (matcher.find()) {
                version = version.substring(matcher.start());
            }
        }
        return version;
    }

    public static String getAndroidVersion() {
        String version = getSystemProp(Constant.PROP_ANDROIDVER, Constant.NULL);
        return version;
    }

    private static String sVersionName = null;

    public static String getVersionName(Context context) {
        if (sVersionName != null) {
            return sVersionName;
        }
        PackageManager manager = context.getPackageManager();
        String versionName = null;
        try {
            PackageInfo pi = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        sVersionName = versionName;
        return sVersionName;
    }


    public static final String CHECK_URL_FILE_PATH = "promotionApp123";

    public static boolean isTestEnvironment() {
        String path = Environment.getExternalStorageDirectory() + File.separator + CHECK_URL_FILE_PATH;
        File file = new File(path);
        return file.exists();
    }

    private static final String SP_NAME = "yourpage_sp";

    public static void saveInfoToSharedPreferences(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getInfoFromSharedPreferences(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void saveInfoToSharedPreferences(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInfoFromSharedPreferences(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void saveInfoToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getInfoFromSharedPreferences(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }


    public static void saveInfoToSharedPreferences(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getInfoFromSharedPreferences(Context context, String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }


}
