**接口定义**
```
    public interface HaoKanService {
        String BASE_URL = "http://sapi.levect.com/"; //必须添加该变量

        @GET("/")
        Observable<HaoKanBean> getHaoKanBean(@QueryMap Map<String, String> queryParameters);

        @GET("/")
        Call<HaoKanBean> getHaoKanBeanWithoutRxJava(@QueryMap Map<String, String> queryParameters);
    }
```

**使用方式**

    private void methodDefault() { // 使用默认的方式
    	 //第一次使用HaoKanService,可以通过Builder指定网络请求的一些数据
        RetrofitManager.Builder builder = new RetrofitManager.Builder().connectTimeout(1000).httpCacheSize(1000);
        Call<HaoKanBean> call = RetrofitManager.getsInstance().getService(HaoKanService.class, builder).getHaoKanBeanWithoutRxJava(queryParameters);
        call.enqueue(new Callback<HaoKanBean>() {
            @Override
            public void onResponse(Call<HaoKanBean> call, Response<HaoKanBean> response) {
                LogUtils.d();
                LogUtils.d(new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<HaoKanBean> call, Throwable t) {
                LogUtils.d();
            }
        });
    }



    private void methodOne() { // 使用RxJava的方式
        Observable<HaoKanBean> observable = RetrofitManager.getsInstance().getService(HaoKanService.class).getHaoKanBean(queryParameters);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<HaoKanBean>(){
                    @Override
                    public void onNext(HaoKanBean haoKanBean) {
                        super.onNext(haoKanBean);
                        LogUtils.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtils.d();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        LogUtils.d();
                    }
                });
    }