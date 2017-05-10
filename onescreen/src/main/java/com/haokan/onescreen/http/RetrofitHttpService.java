package com.haokan.onescreen.http;

import com.haokan.onescreen.model.OneScreenImgBean;
import com.haokan.onescreen.model.ResponseBeanJavaBase;
import com.haokan.onescreen.model.TitleAndUrlModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by chenming on 16/8/3.
 */
public interface RetrofitHttpService {
    String BASE_URL = "http://118.178.174.227:80/hk-protocol/";

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("gionee")
    Call<ResponseBeanJavaBase<OneScreenImgBean>> getMainPageImgHotData(@Body RequestBody route);
    
    
    @GET
    Call<TitleAndUrlModel> TitleAddressUrl(@Url String url);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("gionee")
    Observable<ResponseBeanJavaBase<OneScreenImgBean>> getData(@Body RequestBody requestBody);

    @GET
    Observable<TitleAndUrlModel> getTitle(@Url String url);
}
