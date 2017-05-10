// ITestDemoCardCallback.aidl
package com.haokan.onescreen.aidl;

// Declare any non-default types here with import statements


oneway interface IOneScreenCardCallback {

    oneway void notifyRefesh();
 
    oneway void onDataSuccess(in String data);

    oneway void onGetDataFialed();
}
