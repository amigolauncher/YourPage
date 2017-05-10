package com.haokan.onescreen.activity;

import com.haokan.onescreen.utils.CommonUtil;
import com.haokan.onescreen.utils.UrlsUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.os.Process;

public class WebViewActivity extends Activity {

	protected static final String TAG = "WebViewActivity";
	private WebView mWebView;
	private RelativeLayout webLayout;
	private ProgressBar mPb;
	private String mWeb_Url;
	private LayoutParams rl_match_params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(getResourceId("haokan_onescreen_webview_layout", "layout"));
		webLayout = (RelativeLayout) findViewById(getResourceId("haokan_webview_layout", "id"));
		mPb = (ProgressBar) findViewById(getResourceId("haokan_webview_pb_link", "id"));
		mPb.setVisibility(View.VISIBLE);
		mPb.setMax(100);
		mPb.setProgress(0);
		mWebView = new WebView(this);
		webLayout.removeAllViews();
		initWebView();
		webLayout.addView(mWebView, rl_match_params);
		loadData();

	}

	public int getResourceId(String paramString, String paramResType) {
		int i = this.getResources().getIdentifier(paramString, paramResType.toString(), this.getPackageName());
		if (i <= 0) {
			throw new RuntimeException("获取资源ID失败:(packageName=" + this.getPackageName() + " type=" + paramResType + " name=" + paramString);
		}
		return i;
	}

	@SuppressLint("NewApi")
	private void initWebView() {
		WebSettings settings = mWebView.getSettings();
		StringBuilder builder = new StringBuilder(WebSettings.getDefaultUserAgent(this));
		builder.append(" Levect/").append(CommonUtil.getLocalVersionName(this)).append(" (").append(UrlsUtil.Urls.COMPANY_NO).append("; ")
				.append(CommonUtil.getDid(this)).append("; ").append(0).append(")");

		settings.setUserAgentString(builder.toString());

		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		mWebView.setWebViewClient(new WebViewClient() {
			// 点击链接在此webView打开
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// mWeb_Url = url;
				// return false;

				if (url.startsWith("http") || url.startsWith("https")) {
					view.loadUrl(url);
					return true;
				} else {
					loadLocalApp(url);
					return true;
				}
			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				handler.proceed();
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				mPb.setProgress(newProgress);
				if (newProgress == 100) {
					mPb.setVisibility(View.GONE);
				}
			}
		});

		settings.setAppCacheEnabled(true);
		// settings.setAppCachePath(CacheManager.getWebViewAppCacheDir(getApplicationContext()).getAbsolutePath());
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		settings.setAppCacheMaxSize(1024 * 1024 * 10);
		settings.setAllowFileAccess(true);
		settings.setBuiltInZoomControls(false);

		settings.setTextZoom(100); // 加入这一段是为了手机在设置系统字体大小时，不影响H5字体大小。

		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);

		// mWebView.getSettings().setDatabasePath(CacheManager.getWebViewAppCacheDir(getApplicationContext()).getAbsolutePath());
		settings.setUseWideViewPort(true);
		settings.setGeolocationEnabled(true);

		mWebView.setDownloadListener(new DownloadListener() {// 实现文件下载功能
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
	}

	private void loadData() {

		Bundle bundle = getIntent().getBundleExtra("data");
		mWeb_Url = bundle.getString("url");

		if (TextUtils.isEmpty(mWeb_Url))
			return;

		if (mWeb_Url.startsWith("www")) {
			mWeb_Url = "http://" + mWeb_Url;
		}

		if (mWeb_Url.startsWith("http") || mWeb_Url.startsWith("https")) {
			mWebView.loadUrl(mWeb_Url);
		} else {
			loadLocalApp(mWeb_Url);
		}

	}

	/**
	 * 如果给的链接不是http或者https，默认认为是打开本地应用的activity
	 */
	private void loadLocalApp(String mWeb_Url) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			Uri content_url = Uri.parse(mWeb_Url);
			intent.setData(content_url);
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView != null && mWebView.canGoBack()) {
				mWebView.goBack();
				return true;
			}
			finish();
			try {
				Process.killProcess(Process.myPid());
			} catch (Throwable var2) {

			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mWebView != null) {
			mWebView.destroy();
		}
	}

}
