package com.haokan.onescreen.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.haokan.onescreen.activity.WebViewActivity;
import com.haokan.onescreen.aidl.IOneScreenCardCallback;
import com.haokan.onescreen.aidl.IOneScreenService;
import com.haokan.onescreen.http.HttpRetrofitJavaManager;
import com.haokan.onescreen.model.ListBean;
import com.haokan.onescreen.model.OneScreenImgBean;
import com.haokan.onescreen.model.ResponseBeanJavaBase;
import com.haokan.onescreen.model.TitleAndUrlModel;
import com.haokan.onescreen.model.TitleAndUrlModel.TitleAndUrl;
import com.haokan.onescreen.utils.CommonUtil;
import com.haokan.onescreen.utils.JsonUtil;
import com.haokan.onescreen.utils.UrlsUtil;
import com.haokan.onescreen.utils.UrlsUtil.Urls;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OneScreenService extends Service {

	private static final String TAG = "OneScreenService";
	private static final String HAOKANAPP_URL = "haokanyitu://main";
	private static final String HAOKANH5_URL = "http://levect.com/#/zh/index?eid=102022";
	private String mMoreUrl;

	private OneScreenServiceStubs mIBinder = new OneScreenServiceStubs(this);
	private final RemoteCallbackList<IOneScreenCardCallback> mCallback = new RemoteCallbackList<IOneScreenCardCallback>();

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return mIBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
//		getTitleData();
		Log.d(TAG, "onCreate");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	private void refreshTestDemoCard() {
		Log.d(TAG, "refreshTestDemoCard");
		getData();
		getTitleData();
	}

	private void clickItem(String url) {
		String[] urls = url.replace("|+_+|", ";").split(";");
		if (urls.length == 1) {
			skipWeb(urls[0]);
			return;
		}
		try {
			String uri;
			if (TextUtils.isEmpty(urls[1])) {
				uri = HAOKANAPP_URL + "/?t=" + System.currentTimeMillis();
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
		Uri content_url = Uri.parse(uri + "&eid=102002" + "&did=" + CommonUtil.getDid(getApplicationContext()));
		intent.setData(content_url);
		startActivity(intent);
	}

	private void skipWeb(String url) {
		if (TextUtils.isEmpty(url)) {
			url = HAOKANH5_URL;
		}
		// Log.e(TAG, "@@@@@@@@@@@@@@@@@--h5->"+url);
		Intent intent = new Intent(this, WebViewActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		intent.putExtra("data", bundle);
		startActivity(intent);
	}

	private void clickMore() {
		try {
			skipHaokanApp(HAOKANAPP_URL + "/?t=" + System.currentTimeMillis());
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
			if (TextUtils.isEmpty(mMoreUrl)) {
				skipWeb(HAOKANH5_URL);
			} else {
				skipWeb(mMoreUrl);
			}
		}
	}

	private void registerTestDemoCardCallback(IOneScreenCardCallback callback) throws RemoteException {
		if (callback != null) {
			synchronized (mCallback) {
				mCallback.register(callback);
			}
		}
	}

	private void unRegisterTestDemoCardCallback(IOneScreenCardCallback callback) throws RemoteException {
		if (callback != null) {
			synchronized (mCallback) {
				mCallback.unregister(callback);
			}
		}
	}

	private static class OneScreenServiceStubs extends IOneScreenService.Stub {
		private WeakReference<OneScreenService> mService;

		public OneScreenServiceStubs(OneScreenService service) {
			mService = new WeakReference<OneScreenService>(service);

		}

		@Override
		public void refreshOneScreenCard() throws RemoteException {
			if (mService.get() != null) {
				mService.get().refreshTestDemoCard();
			}
		}

		@Override
		public void clickItem(String url) throws RemoteException {
			if (mService.get() != null) {
				mService.get().clickItem(url);
			}
		}

		@Override
		public void clickMore() throws RemoteException {
			if (mService.get() != null) {
				mService.get().clickMore();
			}
		}

		@Override
		public void registerOneScreenCardCallback(IOneScreenCardCallback callback) throws RemoteException {
			if (mService.get() != null) {
				mService.get().registerTestDemoCardCallback(callback);
			}
		}

		@Override
		public void unRegisterOneScreenCardCallback(IOneScreenCardCallback callback) throws RemoteException {
			if (mService.get() != null) {
				mService.get().unRegisterTestDemoCardCallback(callback);
			}
		}
	}

	public static final String REQUEST_HOT_GROUP = "1"; // 每次请求两组图

	private void getData() {
		LinkedHashMap<String, Object> requestMap = new LinkedHashMap<String, Object>();
		requestMap.put("eId", UrlsUtil.Urls.GIONEE_EID);
		requestMap.put("companyId", "10047");
		requestMap.put("imageTpye", "2");
		requestMap.put("countryCode", "cn");
		requestMap.put("pageSize", REQUEST_HOT_GROUP);
		requestMap.put("pId", Urls.COMPANY_NO);
		requestMap.put("lanId", UrlsUtil.Urls.APP_RQUEST_LANGUAGE);
		requestMap.put("dId", CommonUtil.getDid(getApplicationContext()));

		RequestBody body = HttpRetrofitJavaManager.getRequestBody(HttpRetrofitJavaManager.map2Json(UrlsUtil.Urls.APP_GIONEE_NUMBER,
				JsonUtil.mapToJson(requestMap)));
		Call<ResponseBeanJavaBase<OneScreenImgBean>> mainImgsDataCall2 = HttpRetrofitJavaManager.getInstance().getRetrofitService()
				.getMainPageImgHotData(body);
		mainImgsDataCall2.enqueue(new Callback<ResponseBeanJavaBase<OneScreenImgBean>>() {

			@Override
			public void onResponse(Call<ResponseBeanJavaBase<OneScreenImgBean>> arg0, Response<ResponseBeanJavaBase<OneScreenImgBean>> response) {
				if (mCallback != null) {
					try {
						for (int i = mCallback.beginBroadcast() - 1; i >= 0; i--) {
							final IOneScreenCardCallback cardCallback = mCallback.getBroadcastItem(i);
							if (response != null && response.body().getHeader().getResCode() == 0) {
								OneScreenImgBean oneBean = response.body().getBody();
								String editorRe = oneBean.getEditorRe();
								if (oneBean.getList().size() > 0 && oneBean.getList().get(0) != null) {
									ListBean listbean = oneBean.getList().get(0);
									listbean.setEditorRe(editorRe);
									String json = JsonUtil.toJson(listbean);
									cardCallback.onDataSuccess(json);
								} else {
									cardCallback.onGetDataFialed();
								}

							} else {
								cardCallback.onGetDataFialed();
							}
						}
						mCallback.finishBroadcast();
						
					} catch (Exception e) {
						Log.w(TAG, "DataTask : onPostExecute : ", e);
					}
				}

			}

			@Override
			public void onFailure(Call<ResponseBeanJavaBase<OneScreenImgBean>> arg0, Throwable arg1) {
				if (mCallback != null) {
					try {
						for (int i = mCallback.beginBroadcast() - 1; i >= 0; i--) {
							IOneScreenCardCallback cardCallback = mCallback.getBroadcastItem(i);
							cardCallback.onGetDataFialed();
						}
						mCallback.finishBroadcast();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

		});
	}

	/**
	 * 获取标题
	 */
	private void getTitleData() {
		String titleurl = UrlsUtil.getTitleAddressUrl(this);
		
		Call<TitleAndUrlModel> titleAndUrlCall = HttpRetrofitJavaManager.getInstance().getRetrofitService().TitleAddressUrl(titleurl);
		titleAndUrlCall.enqueue(new Callback<TitleAndUrlModel>() {
			@Override
			public void onResponse(Call<TitleAndUrlModel> arg0, Response<TitleAndUrlModel> arg1) {
				TitleAndUrlModel model = arg1.body();
				if (model != null && model.err_code == 0) {
					TitleAndUrl data = model.data;
					mMoreUrl = data.url_click;
				}
			}

			@Override
			public void onFailure(Call<TitleAndUrlModel> arg0, Throwable arg1) {

			}
		});
	}

}
