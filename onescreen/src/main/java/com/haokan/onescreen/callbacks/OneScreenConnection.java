package com.haokan.onescreen.callbacks;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.haokan.onescreen.OneScreenView;
import com.haokan.onescreen.aidl.IOneScreenService;
import com.haokan.onescreen.model.ListBean;
import com.haokan.onescreen.utils.JsonUtil;
//import com.haokan.statistics.HaokanStatistics;

import java.lang.ref.WeakReference;

public class OneScreenConnection implements ServiceConnection {
	private static final String TAG = "OneScreenConnection";
	private WeakReference<OneScreenView> mRef;
	private IOneScreenService mService;
	private OneScreenCardCallback mCallback;
	private boolean mDestroyed;
	public static final int MSG_LOAD_SUCCESS = 1;
	public static final int MSG_SUCCESS = 5;
	public static final int MSG_LOAD_FAILED = 2;
	public static final int MSG_NOTIFY_REFRESH = 3;

	private static final String ACTION_TESTDEMO_SERVICE = "action.start.onescreenservice";

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SUCCESS:
				loadDataSuccess((String) (msg.obj));
				break;
			case MSG_LOAD_FAILED:
				loadFailed();
				break;
			case MSG_NOTIFY_REFRESH:
				refersh();
				break;
			default:
				break;
			}
		}
	};

	public OneScreenConnection(OneScreenView view) {
		Log.d(TAG, hashCode() + " OneScreenConnection");
		mRef = new WeakReference<OneScreenView>(view);
		mCallback = new OneScreenCardCallback(mHandler);
	}

	// remote callback-loadSuccess
	private void loadDataSuccess(String data) {
		Log.d(TAG, hashCode() + " loadSuccess");
		ListBean modle = JsonUtil.fromJson(data, new TypeToken<ListBean>() {
		}.getType());

		OneScreenView oneScreenView = mRef.get();
		if (oneScreenView != null) {
			oneScreenView.loadDataSuccess(modle);
		}
	}

	// remote callback-loadFailed
	private void loadFailed() {
		Log.d(TAG, hashCode() + " loadFailed");

		OneScreenView oneScreenView = mRef.get();
		if (oneScreenView != null) {
			oneScreenView.loadFailed();
		}
	}

	// remote callback-refersh
	private void refersh() {
		Log.d(TAG, hashCode() + " refersh");
		OneScreenView oneScreenView = mRef.get();
		if (oneScreenView != null) {
			oneScreenView.refersh();
		}
	}

	// local-refreshOneScreenCard
	public void refreshOneScreenCard() {
		Log.d(TAG, hashCode() + " refreshOneScreenCard");

		try {
			if (mService != null) {
				mService.refreshOneScreenCard();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// local-clickItem
	public void clickItem(String url) {
		Log.d(TAG, hashCode() + " clickItem");

		try {
			if (mService != null) {
				mService.clickItem(url);
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// local-clickMore
	public void clickMore() {
		Log.d(TAG, hashCode() + " clickMore");

		try {
			if (mService != null) {
				mService.clickMore();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		Log.d(TAG, hashCode() + " onServiceConnected: " + Thread.currentThread().getName());

		mService = IOneScreenService.Stub.asInterface(service);

		if (mDestroyed) {
			unRegisterOneScreenCardCallback();
		} else {
			registerOneScreenCardCallback();
			refreshOneScreenCard();
		}

	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		mService = null;
		mDestroyed = true;
	}

	private void registerOneScreenCardCallback() {
		Log.d(TAG, hashCode() + " registerOneScreenCardCallback");

		try {
			OneScreenView oneScreenView = mRef.get();
			if (!mDestroyed && mCallback != null && oneScreenView != null && mService != null) {
				mService.registerOneScreenCardCallback(mCallback);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void bindService() {
		Log.d(TAG, hashCode() + " bindService");

		OneScreenView oneScreenView = mRef.get();
		if (!mDestroyed && oneScreenView != null) {
			Context context = oneScreenView.getContext();
			if (context == null) {
				Log.d(TAG, hashCode() + " bindService: false");
				return;
			}

			Intent intent = new Intent();
			intent.setAction(ACTION_TESTDEMO_SERVICE);
			intent.setComponent(new ComponentName(context.getPackageName(), "com.haokan.onescreen.service.OneScreenService"));
			context.bindService(intent, this, Context.BIND_AUTO_CREATE);
			Log.d(TAG, hashCode() + " bindService: success");
		}
	}

	public void unbindService() {
		try {
			Log.d(TAG, hashCode() + " unbindService");
			OneScreenView oneScreenView = mRef.get();
			if (oneScreenView != null && mService != null) {
				unRegisterOneScreenCardCallback();

				Context context = oneScreenView.getContext();
				if (context != null) {
					context.unbindService(this);
//					try {
//						HaokanStatistics.getInstance(context).stopService();
//						Log.d(TAG, hashCode() + "stopService");
//					} catch (Exception e) {
//						Log.d(TAG, hashCode() + "stopService--shibai");
//						e.printStackTrace();
//					}
				}
			}

			if (mHandler != null) {
				mHandler.removeCallbacksAndMessages(null);
				mHandler = null;
			}

			mCallback = null;
			mDestroyed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unRegisterOneScreenCardCallback() {
		Log.d(TAG, hashCode() + " unRegisterOneScreenCardCallback");
		try {
			if (mCallback != null && mService != null) {
				mService.unRegisterOneScreenCardCallback(mCallback);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
