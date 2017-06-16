package com.haokan.onescreen;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gionee.amisystem.depencylib.IYourpageService;
import com.gionee.amisystem.yourpage.common.APP;
import com.gionee.amisystem.yourpage.common.http.RetrofitManager;
import com.gionee.amisystem.yourpage.common.utils.GsonUtils;
import com.gionee.amisystem.yourpage.common.utils.LogUtils;
import com.gionee.amisystem.yourpage.common.utils.YourPageUtils;
import com.gionee.amisystem.yourpage.statistics.CardStatisticsInterface;
import com.gionee.amisystem.yourpage.statistics.StatisticsUtil;
import com.gionee.yourspage.cardinterface.IGioneeCardViewInterface;
import com.haokan.onescreen.callbacks.OneScreenConnection;
import com.haokan.onescreen.http.HttpRetrofitJavaManager;
import com.haokan.onescreen.http.RetrofitHttpService;
import com.haokan.onescreen.model.ListBean;
import com.haokan.onescreen.model.OneScreenImgBean;
import com.haokan.onescreen.model.ResponseBeanJavaBase;
import com.haokan.onescreen.model.SmallsBean;
//import com.haokan.onescreen.utils.CommonUtil;
import com.haokan.onescreen.model.TitleAndUrlModel;
import com.haokan.onescreen.utils.CommonUtil;
import com.haokan.onescreen.utils.DefaultDataUtils;
import com.haokan.onescreen.utils.JsonUtil;
import com.haokan.onescreen.utils.UrlsUtil;
//import com.haokan.onescreen.utils.UrlsUtil;
//import com.haokan.statistics.HaokanStatistics;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class OneScreenView extends LinearLayout implements IGioneeCardViewInterface, CardStatisticsInterface {
	private static final String TAG = "OneScreenView";
	private int width;
	private int dimen_42;
	private ImageButton mRefresh;
	private LinearLayout mRefreshLayout;
	private View more;
	private View mLoading;
	private View mLoadingFail;
	private TextView mTitle;
	private boolean mLoadSuccess;
	private RotateAnimation mAnimation;
	private Context mContext;
	private Context mAppContext;
	private boolean isRefersh;
	private int mCardType;
	private IYourpageService mIservice;
	private long mLastRefershTime;
	private OneScreenConnection mOneScreenConnection;

	private View mFiveView;
	private TextView mImg5Title;
	private ImageView mImg5Iv1;
	private ImageView mImg5Iv2;
	private ImageView mImg5Iv3;
	private ImageView mImg5Iv4;
	private ImageView mImg5Iv5;
	private List<ImageView> mImg5List;
	private int stencilId;

	public OneScreenView(Context launcherContext, Context context) {
		this(context, null, 0);
		this.mAppContext = launcherContext.getApplicationContext();
// test commit
	}

	public OneScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		getMoreClickUrl();
		initData(context);
		getData();

		// TODO url和获取数据
		// // Bind Service
//		mOneScreenConnection = new OneScreenConnection(this);
//		mOneScreenConnection.bindService();

		// ===============好看埋点初始化===================
//		try {
//			HaokanStatistics.getInstance(mContext).init(CommonUtil.getDid(context), "0", UrlsUtil.Urls.COMPANY_NO, UrlsUtil.Urls.GIONEE_EID,
//					CommonUtil.getAndroid_ID(context));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// ===============好看埋点初始化===================
	}

	public OneScreenView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public void initData(Context context) {
		Log.d(TAG, hashCode() + " initData " + Thread.currentThread().getName());
		// 加载布局
		View.inflate(context, getResourceId("haokan_onescreen_cardview_layout", "layout"), this);
		width = context.getResources().getDisplayMetrics().widthPixels;
		dimen_42 = context.getResources().getDimensionPixelSize(getResourceId("haokan_dimen_42", "dimen"));
		mImg5List = new ArrayList<ImageView>();

		initLayout(context);

		mAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mAnimation.setInterpolator(new LinearInterpolator());// 不停顿
		mAnimation.setRepeatCount(-1);// 重复次数
		mAnimation.setFillAfter(true);// 停在最后
		mAnimation.setDuration(600);

	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
	}

	private int overallheight;
	private int smallLayouthHeight;

	private void initLayout(Context context) {
		mTitle = (TextView) findViewById(getResourceId("title_tv", "id"));
		mTitle.setText("好看精选图片");
		more = findViewById(getResourceId("buttom_bar", "id"));
		mRefreshLayout = (LinearLayout) findViewById(getResourceId("refresh_layout", "id"));
		mRefresh = (ImageButton) findViewById(getResourceId("title_refresh", "id"));
		mRefresh.setClickable(false);
		mRefresh.setSelected(false);

		mLoading = findViewById(getResourceId("loading_view", "id"));
		mLoadingFail = findViewById(getResourceId("loading_fail_view", "id"));
		mFiveView = findViewById(getResourceId("five_view", "id"));

		mImg5Title = (TextView) mFiveView.findViewById(getResourceId("img5_img_title", "id"));
		mImg5Iv1 = (ImageView) mFiveView.findViewById(getResourceId("img5_img_1", "id"));
		mImg5Iv2 = (ImageView) mFiveView.findViewById(getResourceId("img5_img_2", "id"));
		mImg5Iv3 = (ImageView) mFiveView.findViewById(getResourceId("img5_img_3", "id"));
		mImg5Iv4 = (ImageView) mFiveView.findViewById(getResourceId("img5_img_4", "id"));
		mImg5Iv5 = (ImageView) mFiveView.findViewById(getResourceId("img5_img_5", "id"));

		mImg5List.add(mImg5Iv1);
		mImg5List.add(mImg5Iv2);
		mImg5List.add(mImg5Iv3);
		mImg5List.add(mImg5Iv4);
		mImg5List.add(mImg5Iv5);

		mImg5Title.setSingleLine(true);

		int imgw5 = (width - dimen_42);
		int imgwh5 = (int) ((int) imgw5 * 0.52);
		overallheight = imgwh5 / 9 * 18;
		smallLayouthHeight = (int) (((imgw5 / 24 * 11)) / 3 * 1.57);

		FrameLayout.LayoutParams lp5 = new FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT, overallheight);
		RelativeLayout.LayoutParams smalllp = new RelativeLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
				smallLayouthHeight);
		smalllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		mFiveView.setLayoutParams(lp5);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, overallheight);
		mLoading.setLayoutParams(lp);
		mLoadingFail.setLayoutParams(lp);
		mLoadingFail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!checkNetWorkConnect(mContext)) {
					return;
				}
				refersh();
				isRefersh = false;
			}
		});
		mRefreshLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!checkNetWorkConnect(mContext)) {

					Toast.makeText(mContext, mContext.getResources().getString(getResourceId("haokan_network_anomalies", "string")),
							Toast.LENGTH_SHORT).show();

					return;
				}
				isRefersh = true;
				StatisticsUtil.Statistics_Click_Card_Refresh_Button_Times(mContext, mIservice, mCardType);

				refersh();
				// 动画开始
				mRefresh.startAnimation(mAnimation);

			}
		});
		more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!checkNetWorkConnect(mContext)) {
					return;
				}

				// TODO 点击更多
				if(!YourPageUtils.isFastDoubleClick()) {
					skipWeb(mMoreClickUrl);
				}
				StatisticsUtil.Statistics_Click_Card_More_Button_Times(mContext, mIservice, mCardType);
//				if (mLoadSuccess) {
//					mOneScreenConnection.clickMore();
//					StatisticsUtil.Statistics_Click_Card_More_Button_Times(mContext, mIservice, mCardType);
//				}

			}
		});

		mLoading.setVisibility(View.VISIBLE);
		mLoadingFail.setVisibility(View.GONE);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public int getResourceId(String paramString, String paramResType) {
		int i = mContext.getResources().getIdentifier(paramString, paramResType.toString(), mContext.getPackageName());
		if (i <= 0) {
			throw new RuntimeException("获取资源ID失败:(packageName=" + mContext.getPackageName() + " type=" + paramResType + " name=" + paramString);
		}
		return i;
	}

	public void refersh() {
		// TODO 刷新
		if(!YourPageUtils.isFastDoubleClick()) {
			getData();
		}
//		try {
//			mOneScreenConnection.refreshOneScreenCard();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public void loadDataSuccess(ListBean data) {
		try {
			mLoading.setVisibility(View.GONE);
			mLoadingFail.setVisibility(View.GONE);
			if (isRefersh) {
				if (mAnimation != null)
					mAnimation.cancel();
			}

			if (data != null) {
				setImg5View(data);
			}
		} catch (Exception e) {
			loadFailed();
			e.printStackTrace();
		}
		mLastRefershTime = System.currentTimeMillis();
		mLoadSuccess = true;

	}

	private void setImg5View(ListBean data) {
		stencilId = 2;
		mFiveView.setVisibility(View.VISIBLE);
		List<SmallsBean> sBeans = data.getSmalls();
		if (sBeans.size() < 4) {
			data = DefaultDataUtils.getDefaultData();
			sBeans = data.getSmalls();
		}

		SmallsBean smallsBean = new SmallsBean();
		smallsBean.setImageId(data.getImageId());
		smallsBean.setImageUrl(data.getImageUrl());
		smallsBean.setImgClickUrl(data.getImgClickUrl());
		smallsBean.setNativeUrl(data.getNativeUrl());

		sBeans.add(0, smallsBean);
		final List<SmallsBean> sBean = sBeans;

		if (sBeans == null || sBeans.size() < 5) {
			return;
		}

		mImg5Title.setText(data.getTitle());

		for (int i = 0; i < mImg5List.size(); i++) {
			final ImageView iv = mImg5List.get(i);
			try {
				Glide.with(mAppContext).load(sBeans.get(i).getImageUrl()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
						.placeholder(mContext.getResources().getDrawable(getResourceId("haokan_bg_default", "drawable"))).into(iv);
			} catch (Exception e) {
				iv.setImageResource(getResourceId("haokan_bg_default", "color"));
			}
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!checkNetWorkConnect(mContext)) {
						return;
					}
					int x = mImg5List.indexOf(iv);

					String nativeurl = sBean.get(x).getNativeUrl();
					// TODO 点击item
					clickItem(sBean.get(x).getImgClickUrl() + "|+_+|" + nativeurl);
//					mOneScreenConnection.clickItem(sBean.get(x).getImgClickUrl() + "|+_+|" + nativeurl);

					StatisticsUtil.Statistics_Click_Card_Times(mContext, mIservice, mCardType);

				}
			});
		}
		invalidate();
	}

	public void loadFailed() {
		try {
			if (mFiveView.getVisibility() == View.GONE) {
				mLoading.setVisibility(View.GONE);
				mLoadingFail.setVisibility(View.VISIBLE);
			}
			if (isRefersh) {
				if (mAnimation != null)
					mAnimation.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public View getCardView(Context context) {
		return this;
	}

	@Override
	public void init(Context context) {
		Log.d(TAG, hashCode() + " init " + Thread.currentThread().getName());
//		 this.mAppContext = context.getApplicationContext();
	}

	/**
	 * 卡片view添加成功调用的接口
	 */
	@Override
	public void onAdd() {
		Log.d(TAG, hashCode() + " onAdd " + Thread.currentThread().getName());
		// Log.d(TAG, "onAdd : connectSuccess = " + connectSuccess);
	}

	/**
	 * 卡片view删除时调用的接口，与onAdd相对应
	 */
	@Override
	public void onRemove() {
		Log.d(TAG, hashCode() + " onRemove " + Thread.currentThread().getName());
//		mOneScreenConnection.unbindService();
	}

	/**
	 * 卡片view显示时调用的接口
	 */
	@Override
	public void onResume() {
		Log.d(TAG, "onResume");
	}

	/**
	 * 卡片view不显示时调用的接口，如被隐藏、滑动到不可见等情况，与onResume相对应
	 */
	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
	}

	/**
	 * 取得当前卡片的名称
	 *
	 * @return 当前卡片的名称
	 */
	@Override
	public String getCardViewName() {
		return mContext.getResources().getString(getResourceId("haokan_onescreen_cardname", "string"));
	}

	/**
	 * 设置卡片是否有连接网络的权限,有网络权限的时候才允许有联网获取卡片数据，刷新数据等行为。
	 *
	 * @param b
	 */
	@Override
	public void setNetWorkAuthority(boolean b) {

	}

	/**
	 * 设置卡片缓存文件，图片，下载的apk的根目录
	 *
	 * @param
	 */
	@Override
	public void setCardFilePath(String s) {

	}

	/**
	 * 设定卡片的最大宽度和最大高度，卡片设计不能超过此高度和宽度
	 *
	 * @param i
	 * @param i1
	 */
	@Override
	public void setMaxSize(int i, int i1) {

	}

	@Override
	public void setAllowInvalidate(boolean b) {

	}

	/**
	 * 检查网络连接状态
	 */
	public boolean checkNetWorkConnect(Context context) {
		boolean result;
		if (context != null) {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netinfo = cm.getActiveNetworkInfo();
			result = netinfo != null && netinfo.isConnected();
			return result;
		} else {
			return false;
		}
	}

	@Override
	public void setCardType(int cardType) {
		this.mCardType = cardType;
	}

	@Override
	public void setYourpageServie(IYourpageService iservice) {
		this.mIservice = iservice;
	}

	public long getYourPageLastRefreshTime() {
		return mLastRefershTime;
	}

	public void onYourPageAutoRefresh() {
		if (checkNetWorkConnect(mContext) && isWifi(mContext)) {
			isRefersh = true;
			refersh();
		}
	}

	/**
	 * 检测当前打开的网络类型是否WIFI
	 *
	 * @param context
	 *            上下文
	 * @return 是否是Wifi上网
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
	}

	// =====================Copy from OneScreenServie===========================
	private String mMoreClickUrl = "http://levect.com/#/zh/index?eid=102022";

	public void getMoreClickUrl() {
		String titleurl = UrlsUtil.getTitleAddressUrl(APP.getAppContext());
		Observable<TitleAndUrlModel> observable = RetrofitManager.getsInstance().getService(RetrofitHttpService.class).getTitle(titleurl);
		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<TitleAndUrlModel>() {
					@Override
					public void accept(@NonNull TitleAndUrlModel titleAndUrlModel) throws Exception {
						LogUtils.d("default mMoreClickUrl = " + mMoreClickUrl);
						if (titleAndUrlModel != null && titleAndUrlModel.getErr_code() == 0) {
							TitleAndUrlModel.TitleAndUrl data = titleAndUrlModel.getData();
							mMoreClickUrl = data.getUrl_click();
							LogUtils.d("mMoreClickUrl = " + mMoreClickUrl);
						}
					}
				});
	}

	public void clickItem(String url) {
		String[] urls = url.replace("|+_+|", ";").split(";");
		if (urls.length == 1) {
			skipWeb(urls[0]);
			return;
		}
		try {
			String uri;
			if (TextUtils.isEmpty(urls[1])) {
				uri = "haokanyitu://main" + "/?t=" + System.currentTimeMillis();
			} else {
				if (urls[1].contains("/?")) {
					uri = urls[1] + "&t=" + System.currentTimeMillis();
				} else {
					uri = urls[1] + "/?t=" + System.currentTimeMillis();
				}
			}

			skipHaokanApp(uri);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
			skipWeb(urls[0]);
		}
	}

	private void skipHaokanApp(String uri) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri content_url = Uri.parse(uri + "&eid=102002" + "&did=" + CommonUtil.getDid(APP.getAppContext()));
		intent.setData(content_url);
		APP.getAppContext().startActivity(intent);
	}

	private void skipWeb(String url) {
		if (TextUtils.isEmpty(url)) {
			url = "http://levect.com/#/zh/index?eid=102022";
		}
		// Log.e(TAG, "@@@@@@@@@@@@@@@@@--h5->"+url);
		Intent intent = new Intent("action.start.WebViewActivity");
		intent.setPackage("com.gionee.amisystem.yourpage");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		intent.putExtra("data", bundle);
		APP.getAppContext().startActivity(intent);
	}

	public void getData() {
		LinkedHashMap<String, Object> requestMap = new LinkedHashMap<String, Object>();
		requestMap.put("eId", UrlsUtil.Urls.GIONEE_EID);
		requestMap.put("companyId", "10047");
		requestMap.put("imageTpye", "2");
		requestMap.put("countryCode", "cn");
		requestMap.put("pageSize", "1");
		requestMap.put("pId", UrlsUtil.Urls.COMPANY_NO);
		requestMap.put("lanId", UrlsUtil.Urls.APP_RQUEST_LANGUAGE);
		requestMap.put("dId", CommonUtil.getDid(APP.getAppContext()));
		RequestBody body = HttpRetrofitJavaManager.getRequestBody(HttpRetrofitJavaManager.map2Json(UrlsUtil.Urls.APP_GIONEE_NUMBER,
				JsonUtil.mapToJson(requestMap)));

		Observable<ResponseBeanJavaBase<OneScreenImgBean>> observable = RetrofitManager.getsInstance().getService(RetrofitHttpService.class).getData(body);
		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<ResponseBeanJavaBase<OneScreenImgBean>>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(ResponseBeanJavaBase<OneScreenImgBean> response) {
						LogUtils.d("result = " + GsonUtils.toJson(response));
						OneScreenImgBean oneBean = response.getBody();
						String editorRe = oneBean.getEditorRe();
						if (oneBean.getList().size() > 0 && oneBean.getList().get(0) != null) {
							ListBean listbean = oneBean.getList().get(0);
							listbean.setEditorRe(editorRe);
							loadDataSuccess(listbean);
						} else {
							loadFailed();
						}
					}

					@Override
					public void onError(Throwable e) {
						loadFailed();
					}

					@Override
					public void onComplete() {

					}
				});
	}
}
