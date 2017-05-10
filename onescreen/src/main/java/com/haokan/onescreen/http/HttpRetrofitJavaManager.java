package com.haokan.onescreen.http;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.text.TextUtils;

import com.haokan.onescreen.utils.JsonUtil;
import com.haokan.onescreen.utils.MD5Util;
import com.haokan.onescreen.utils.UrlsUtil;


public class HttpRetrofitJavaManager {
    public static final int CONNECT_TIME_OUT = 15;
    public static final int READ_TIME_OUT = 30;
    public static final int WRITE_TIME_OUT = 30;
    private Retrofit retrofit;
    private RetrofitHttpService mRetrofitHttpService;


    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpRetrofitJavaManager INSTANCE = new HttpRetrofitJavaManager();
    }

    public RetrofitHttpService getRetrofitService() {
        return mRetrofitHttpService;
    }

    //获取单例
    public static HttpRetrofitJavaManager getInstance() {
        return HttpRetrofitJavaManager.SingletonHolder.INSTANCE;
    }

    private HttpRetrofitJavaManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (builder.interceptors() != null) {
            builder.interceptors().clear();
        }
        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);//出错是否进行重连
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(UrlsUtil.Urls.URL_HOST_TO_JAVA)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRetrofitHttpService = retrofit.create(RetrofitHttpService.class);
    }

    public static AtomicLong seed = new AtomicLong(0);

    public static String map2Json(final String transactionType, final String bodyData) {
        if (TextUtils.isEmpty(transactionType) || TextUtils.isEmpty(bodyData)) {
            return "";
        }
        Map<String, Object> headMap = new HashMap<String, Object>();
        Map<String, Object> requestMap = new HashMap<String, Object>();

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
        sdf.applyPattern("yyyyMMddHHmmss");
        String timeStamp = sdf.format(date);

        String messageID = timeStamp + String.format("%012d", seed.incrementAndGet());

        StringBuilder sb = new StringBuilder();
        String sign = MD5Util.MD5Encode(sb.append(messageID).append(timeStamp)
                .append(transactionType).append(UrlsUtil.Urls.SEC_KEY_ONE_SCREEN)
                .append(bodyData).toString());//MD5加密

        headMap.put("resCode", "0");
        headMap.put("pageStartIndex", "0");
        headMap.put("pageEndIndex", "0");
        headMap.put("messageID", messageID);
        headMap.put("timeStamp", timeStamp);
        headMap.put("transactionType", UrlsUtil.Urls.APP_GIONEE_NUMBER);
        headMap.put("sign", sign);
        headMap.put("terminal", "1");
        headMap.put("version", "1.0");//厂商id
        headMap.put("companyId", "10047");//厂商id
        
        requestMap.put("header", headMap);
        requestMap.put("body", JsonUtil.jsonToMap(bodyData));
        return JsonUtil.mapToJson(requestMap);

    }


    /**
     * 请求json转RequestBody
     * @param jsonData
     * @return
     */
   public static RequestBody getRequestBody(String jsonData){
       RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonData);
       return body;
   }

}
