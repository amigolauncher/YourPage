package com.gionee.amisystem.yourpage.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class GsonUtils {

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String str, Type type) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(str, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(str, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map, LinkedHashMap.class);
        return jsonStr;
    }

    public static Map<String, String> jsonToMap(String data) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(data, new TypeToken<Map<String, String>>() {
        }.getType());
        return map;
    }
}
