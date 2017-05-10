package com.haokan.onescreen.utils;

import android.content.Context;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 用于生成访问网络的url地址的工具类
 */
public class UrlsUtil {

	public static class Urls {
		public static final String COMPANY_NO = "10047";
		public static final String URL_HOST = "http://sapi.levect.com";
		public static final String URL_A_title = "GetButtonBar";
		public static final String URL_C_2 = "apijinli";
		public static final String URL_HOST_TO_JAVA ="http://118.178.174.227:80/hk-protocol/" ; //线上
		public static final String SEC_KEY_ONE_SCREEN = "ZeBd7JhBQYbdKA8GF5";// 测试版本秘钥ZeBd7JhBQYbdKA8GF5
		public static final String APP_RQUEST_LANGUAGE = "zh";
		public static final String APP_GIONEE_NUMBER = "700202";
		public static final String GIONEE_EID = "102022";
		public static final String VERSION = "2.0.0";

	}

	/**
	 * 获取sign
	 */
	private static String getSign(String a, String c, String k, String t, String v, String jsonData) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("a", a);
		map.put("c", c);
		map.put("k", k);
		map.put("t", t);
		map.put("v", v);
		map.put("data", jsonData);

		List<String> list = new ArrayList<String>();
		list.addAll(map.keySet());
		Collections.sort(list);

		String sign = "";
		for (int i = 0; i < list.size(); i++) {
			// 拼接sign
			sign += list.get(i) + map.get(list.get(i));
		}
		String API_SEC = Urls.SEC_KEY_ONE_SCREEN;// 获取密钥
		sign = API_SEC + sign + API_SEC;// 拼接sign
		sign = SecurityUtil.md5(sign).toLowerCase();// 进行md5加密，并转成小写
		return sign;
	}

	/**
	 * 获取title，跳转地址
	 */
	public static String getTitleAddressUrl(Context context) {
		return getOPUrl3(Urls.URL_HOST, Urls.URL_A_title, Urls.URL_C_2, null, "1", context, CommonUtil.getDid(context));
	}

	public static String getOPUrl3(String host, String a, String c, String jsonData, String version, Context context, String did) {
		if (jsonData == null) {
			jsonData = "{}";
		}
		String data = URLEncoder.encode(jsonData); // 获得URLEncoder后的data字符串
		String k = "241";
		String t = String.valueOf(System.currentTimeMillis() / 1000); // 时间戳
		String sign = getSign(a, c, k, t, version, jsonData); // 获得sign字符串
		return host + "/?a=" + a + "&c=" + c + "&data=" + data + "&k=" + k + "&t=" + t + "&v=" + version + "&sign=" + sign + "&pid="
				+ "241" + "&did=" + did;
	}
}
