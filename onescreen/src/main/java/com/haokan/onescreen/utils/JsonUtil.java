package com.haokan.onescreen.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于解析json工具类
 */
public class JsonUtil {
	private static final String TAG = "haokantag";

	/**
	 * 对象转换成json字符串,bean或者集合
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * json字符串转成对象，用于转化复杂的结构，如果map或者list集合
	 */
	public static <T> T fromJson(String str, Type type) {
		// Gson gson = new Gson();
		// return gson.fromJson(str, type);
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(str, type);
		} catch (Exception e) {
			Log.e(TAG, str);
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * json字符串转成对象，转换简单的bean
	 */
	public static <T> T fromJson(String str, Class<T> type) {

		// Gson gson = new Gson();
		// return gson.fromJson(str, type);
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(str, type);
		} catch (Exception e) {
			Log.e(TAG, str);
			e.printStackTrace();
		}

		return t;
	}

	/**
	 * 将Map转化为Json
	 *
	 * @param map
	 * @return String
	 */
	public static <T> String mapToJson(Map<String, T> map) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(map, LinkedHashMap.class);
		return jsonStr;
	}

	/**
	 * 将json转为Map
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, String> jsonToMap(String data) {
		Gson gson = new Gson();
		Map<String, String> map = gson.fromJson(data, new TypeToken<Map<String, String>>() {
		}.getType());
		return map;
	}

}
