package com.gionee.amisystem.depencylib.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by lee on 16-5-16.
 */
public class NativeHttpUtils implements GnHttpRequest {
    public static final String TAG = "NativeHttpUtils";

    private static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    private static final String CONTENT_TYPE_FROM = "application/x-www-form-urlencoded";

    private static final String TAG_YU = "&";
    private static final String TAG_EQUALS = "=";
    private static final String TAG_QUESTION = "?";

    private GnHttpConfig mHttpConfig;

    public NativeHttpUtils() {
        mHttpConfig = new GnHttpConfig();
    }

    public NativeHttpUtils(GnHttpConfig httpConfig) {
        this.mHttpConfig = httpConfig;
    }

    @Override
    public GnHttpResponse get(String url) {
        return get(url, mHttpConfig);
    }

    public GnHttpResponse get(String url, Map<String, String> params) {
        String concatUrl = url;
        String paraString = getParamsString(params);
        Log.d(TAG, "paraString = " + paraString);
        if (!TextUtils.isEmpty(paraString)) {
            concatUrl = concatUrl + TAG_QUESTION + getParamsString(params);
        }
        return get(concatUrl, mHttpConfig);
    }

    @Override
    public GnHttpResponse post(String url, String params) {
        return post(url, mHttpConfig, params, CONTENT_TYPE_FROM);
    }

    public GnHttpResponse post(String url, Map<String, String> parms) {
        return post(url, mHttpConfig, getParamsString(parms), CONTENT_TYPE_JSON);
    }

    public void setHttpConfig(GnHttpConfig sHttpConfig) {
        this.mHttpConfig = sHttpConfig;
    }

    @Override
    public GnHttpResponse postWithJson(String url, String params) {
        return post(url, mHttpConfig, params, CONTENT_TYPE_JSON);
    }

    private GnHttpResponse get(String actionUrl, GnHttpConfig config) {
        HttpURLConnection conn = null;
        InputStream is = null;

        try {
            URL url = new URL(actionUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(config.getConnectionTimeOut());
            conn.setReadTimeout(config.getReadtimeOut());
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE_FROM);

            int response = conn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                int ch;
                ByteArrayOutputStream data = new ByteArrayOutputStream();
                while ((ch = is.read()) != -1) {
                    data.write((char) ch);
                }
                Log.d(TAG, "url " + url);
                return new GnHttpResponse(response, data.toString(config.getEncode()));
            }
            Log.d(TAG, "get request error, url " + url + "response code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return new GnHttpResponse();
    }


    private GnHttpResponse post(String actionUrl, GnHttpConfig config, String json, String contentType) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        DataOutputStream op = null;
        InputStreamReader isp = null;
        try {
            URL url = new URL(actionUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(config.getConnectionTimeOut());
            conn.setReadTimeout(config.getReadtimeOut());
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", contentType);
            conn.connect();

            op = new DataOutputStream(conn.getOutputStream());
            op.writeBytes(json);

            isp = new InputStreamReader(conn.getInputStream(), config.getEncode());
            int code = conn.getResponseCode();
            reader = new BufferedReader(isp);
            StringBuilder res = new StringBuilder();
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(config.getEncode()), config.getEncode());
                res.append(lines);
            }

            return new GnHttpResponse(code, res.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (op != null) {
                try {
                    op.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (isp != null) {
                try {
                    isp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                conn.disconnect();
            }
        }
        return new GnHttpResponse();
    }

    private String getParamsString(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        // create params string.
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String val = entry.getValue();
            String key = entry.getKey();
            try {
                val = URLEncoder.encode(val.toString(), mHttpConfig.getEncode());
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "httpUtils encode error", e);
            }
            sb.append(TAG_YU).append(key).append(TAG_EQUALS).append(val);
        }

        //replace fisrt &,
        String paraString = sb.toString();
        paraString = paraString.replaceFirst(TAG_YU, "");
        return paraString;
    }

}
