package com.haokan.onescreen.callbacks;

import android.os.Handler;
import android.os.RemoteException;

import com.haokan.onescreen.aidl.IOneScreenCardCallback;

import java.lang.ref.WeakReference;



public class OneScreenCardCallback extends IOneScreenCardCallback.Stub {

    private WeakReference<Handler> mHandler;

    public OneScreenCardCallback(Handler handler) {
        mHandler = new WeakReference<Handler>(handler);
    }

    @Override
    public void notifyRefesh() throws RemoteException {
        if (mHandler.get() != null) {
            mHandler.get().obtainMessage(OneScreenConnection.MSG_NOTIFY_REFRESH).sendToTarget();
        }
    }

    @Override
    public void onGetDataFialed() throws RemoteException {
        if (mHandler.get() != null) {
            mHandler.get().obtainMessage(OneScreenConnection.MSG_LOAD_FAILED).sendToTarget();
        }
    }

	@Override
	public void onDataSuccess(String data) throws RemoteException {
		 if (mHandler.get() != null) {
	            mHandler.get().obtainMessage(OneScreenConnection.MSG_SUCCESS,data).sendToTarget();
	        }
		
	}
}
