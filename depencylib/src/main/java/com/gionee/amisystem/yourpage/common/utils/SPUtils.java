package com.gionee.amisystem.yourpage.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gionee.amisystem.yourpage.common.APP;
import android.support.v4.content.SharedPreferencesCompat.EditorCompat;

import java.util.Map;
import java.util.Set;

/**
 * Created by orgcheng on 17-4-22.
 * 使用全局Context
 */

public class SPUtils {
    private static final String FILE_NAME = "yourpage_sp";

    public static void clear() {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        EditorCompat.getInstance().apply(edit);
    }

    public static void remove(String key) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        EditorCompat.getInstance().apply(edit);
    }

    public static boolean contains(String key) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll() {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    // edit put : int string long boolean float Set<String>
    public static void put(String key, int value) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        EditorCompat.getInstance().apply(edit);
    }

    public static void put(String key, String value) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        EditorCompat.getInstance().apply(edit);
    }

    public static void put(String key, long value) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        EditorCompat.getInstance().apply(edit);
    }

    public static void put(String key, boolean value) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        EditorCompat.getInstance().apply(edit);
    }

    public static void put(String key, float value) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, value);
        EditorCompat.getInstance().apply(edit);
    }

    public static void put(String key, Set<String> values) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key, values);
        EditorCompat.getInstance().apply(edit);
    }

    // edit get : int string long boolean float Set<String>

    public static int get(String key, int defValue) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static String get(String key, String defValue) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static long get(String key, long defValue) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static boolean get(String key, boolean defValue) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static float get(String key, float defValue) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    public static Set<String> get(String key, Set<String> defValues) {
        SharedPreferences sp = APP.getAppContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getStringSet(key, defValues);
    }
}
