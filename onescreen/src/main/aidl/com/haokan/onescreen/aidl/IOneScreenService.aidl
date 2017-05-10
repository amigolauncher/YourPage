// ITestDemoService.aidl
package com.haokan.onescreen.aidl;

// Declare any non-default types here with import statements
import com.haokan.onescreen.aidl.IOneScreenCardCallback;

oneway interface IOneScreenService {

    oneway void refreshOneScreenCard();

    oneway void clickItem(in String url);

    oneway void clickMore();

    oneway void registerOneScreenCardCallback(IOneScreenCardCallback callback);

    oneway void unRegisterOneScreenCardCallback(IOneScreenCardCallback callback);
}