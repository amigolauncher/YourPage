package com.gionee.amisystem.depencylib.http;


import android.util.Log;


import com.gionee.amisystem.depencylib.Constant;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;


public class HttpUtils {
    public static final String TAG = "HttpUtils";
    private static final boolean DEBUG_HTTPS = true;
    public static final int SO_TIMEOUT_MILLS = 5000;
    public static final int CONNECT_TIMEOUT_MILLS = 3000;

    private static GnHttpRequest sRequest = null;

    public static String sendGet(String baseUrl, Map<String, String> params, int readTimeout, int connecTimeout) {
        if (DEBUG_HTTPS) {
            Log.d(TAG, " sendGet  baseUrl " + baseUrl + " params " + params);
        }
        initIfNeed(readTimeout, connecTimeout);
        GnHttpResponse response = sRequest.get(baseUrl, params);
        if (DEBUG_HTTPS) {
            Log.d(TAG, "sendGet. response is : " + response);
        }
        return response.getResponseString();
    }

    public static String sendGet(String baseUrl) {
        if (DEBUG_HTTPS) {
            Log.d(TAG, " sendGet  baseUrl " + baseUrl);
        }
        initIfNeed(SO_TIMEOUT_MILLS, CONNECT_TIMEOUT_MILLS);
        GnHttpResponse response = sRequest.get(baseUrl);
        if (DEBUG_HTTPS) {
            Log.d(TAG, "sendGet. response is : " + response);
        }
        return response.getResponseString();
    }

    public static String sendPostWithJson(String url, String params, int readTimeout, int connecTimeout) {
        if (DEBUG_HTTPS) {
            Log.d(TAG, " sendPostWithJson " + url + " params " + params);
        }
        initIfNeed(readTimeout, connecTimeout);
        GnHttpResponse response = sRequest.postWithJson(url, params);
        if (DEBUG_HTTPS) {
            Log.d(TAG, "sendPostWithJson . response is : " + response);
        }
        return response.getResponseString();
    }

    public static boolean sendBiStatisticWithNoReply(String baseUrl, String params, int readTimeout, int connecTimeout) {
        if (DEBUG_HTTPS) {
            Log.d(TAG, " sendBiStatisticWithNoReply " + baseUrl + " params " + params);
        }
        String url = baseUrl + Constant.TAG_QUESTION + params;
        initIfNeed(readTimeout, connecTimeout);
        GnHttpResponse response = sRequest.get(url);
        if (DEBUG_HTTPS) {
            Log.d(TAG, " sendBiStatisticWithNoReply .  response is : " + response);
        }
        return response.getStatusCode() == HttpURLConnection.HTTP_OK;
    }

    private static void initIfNeed(int readTimeout, int connectionTimeout) {
        if (sRequest == null) {
            sRequest = GnHttpSimpleFactory.getDefalutHttpRequest();
            sRequest.setHttpConfig(new GnHttpConfig(readTimeout, connectionTimeout));
        }
    }

    public static String getParamsString(Map<String, String> params) {
        // create params string.
        StringBuilder sb = new StringBuilder();

        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();

            try {
                value = URLEncoder.encode(value.toString(), Constant.ENCODE);
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "httpUtils encode error", e);
            }
            sb.append(Constant.TAG_YU).append(key).append(Constant.TAG_EQUALS).append(value);
        }

        //replace first &,
        String paraString = sb.toString();
        paraString = paraString.replaceFirst(Constant.TAG_YU, "");
        return paraString;
    }
}