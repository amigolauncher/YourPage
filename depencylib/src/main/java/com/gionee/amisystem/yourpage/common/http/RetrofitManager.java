package com.gionee.amisystem.yourpage.common.http;

import android.text.TextUtils;

import com.gionee.amisystem.yourpage.common.APP;
import com.gionee.amisystem.yourpage.common.utils.LogUtils;
import com.gionee.amisystem.yourpage.common.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitManager {
    private static RetrofitManager sInstance = new RetrofitManager();

    private RetrofitManager() {
    }

    public static RetrofitManager getsInstance() {
        return sInstance;
    }

    private HashMap<String, Object> mServiceMap = new HashMap<>();

    public <S> S getService(Class<S> serviceClass) {
        return getService(serviceClass, null);
    }

    public <S> S getService(Class<S> serviceClass, Builder builder) {
        S result = null;
        if (mServiceMap.containsKey(serviceClass.getName())) {
            result = (S) mServiceMap.get(serviceClass.getName());
        } else {
            synchronized (this) {
                if (result == null) {
                    result = createService(serviceClass, builder);
                    mServiceMap.put(serviceClass.getName(), result);
                }
            }
        }
        return result;
    }

    private <S> S createService(Class<S> serviceClass, Builder builder) {
        return createService(serviceClass, getOkHttpClient(builder));
    }

    private static <S> S createService(Class<S> serviceClass, OkHttpClient client) {
        String baseUrl = "";
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalArgumentException("BASE_URL is not allow empty for " + serviceClass.getSimpleName());
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(serviceClass);
    }

    public <S> void removeService(Class<S> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getName())) {
            synchronized (this) {
                mServiceMap.remove(serviceClass.getName());
            }
        }
    }

    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            if (!NetWorkUtils.isNetworkAvailable()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("network not available");
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            LogUtils.i(String.format(Locale.getDefault(), "Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            LogUtils.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    };

    private static final int DEFAULT_TIMEOUT = 10;
    private static final long DEFAULT_HTTP_CACHE_SIZE = 1024 * 1024 * 100;

    public OkHttpClient getOkHttpClient(Builder builder) {
        long connectTimeout = DEFAULT_TIMEOUT;
        long readTimeout = DEFAULT_TIMEOUT;
        long writeTimeout = DEFAULT_TIMEOUT;
        long httpCacheSize = DEFAULT_HTTP_CACHE_SIZE;

        if (builder != null) {
            connectTimeout = builder.connectTimeout;
            readTimeout = builder.readTimeout;
            writeTimeout = builder.writeTimeout;
            httpCacheSize = builder.httpCacheSize;
        }

        Cache cache = new Cache(new File(APP.getAppContext().getCacheDir(), "HttpCache"), httpCacheSize);
        OkHttpClient client = new OkHttpClient.Builder().cache(cache)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(mLoggingInterceptor)
                .build();
        return client;
    }

    public static final class Builder {
        public long connectTimeout = DEFAULT_TIMEOUT;
        public long readTimeout = DEFAULT_TIMEOUT;
        public long writeTimeout = DEFAULT_TIMEOUT;
        public long httpCacheSize = DEFAULT_HTTP_CACHE_SIZE;

        public Builder connectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder readTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder writeTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder httpCacheSize(long httpCacheSize) {
            this.httpCacheSize = httpCacheSize;
            return this;
        }
    }
}
