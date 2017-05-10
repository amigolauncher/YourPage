## 概述
1. 提供统一的线程池
	`YourPageExecutor.THREAD_POOL_EXECUTOR.execute(...);`

2. 网络请求方式Retrofit+Rxjava
	Retrofit定义接口时,必须定义变量`String BASE_URL;`

3. `SharedPrefereces`的使用,建议使用统一提供的工具类`SPUtils`

4. 日志打印, 建议使用提供打`LogUtils`,提供统一的`yourpage_`前缀

5. `APP`类提供正确的`Context`
	在桌面进程提供桌面的`Context`,在桌面看看进程提供桌面看看的`Context`

6. 继续完善中...


## 卡片的集成
- - -
修改`YourPageView`如下方法,替换`OneScreenView`为自己实现的`View`即可

    public void addCardView() {
        if (cardView == null) {
            // TODO 此处的OneScreenView,送好看实现的卡片View
            cardView = new OneScreenView(mLauncherContext, mContext);
            cardView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            addView(cardView, 0);

            // TODO 传递用于统计的参数, 此处传入假的数据
            cardView.setCardType(0);
            cardView.setYourpageServie(null);

            cardView.onAdd();
            cardView.onResume();
        }
    }