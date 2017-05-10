// IYourpageService.aidl
package com.gionee.amisystem.depencylib;

// Declare any non-default types here with import statements
import com.gionee.amisystem.depencylib.IYourpageCallback;
interface IYourpageService {

    void startLoadCardData();

    void release();

    void startCardManager();

    void registerCallback(IYourpageCallback var1);

    void unRegisterCallback(IYourpageCallback var1);

    void downloadNewVersion();

    void onEventForStatistics(String var1);

    void onEventForStatisticsForParams(String params);

    void onEventForStatisticsForValue(String var1, String var2);

    void onKillSelf();

    void onResume();

    void onPause();

    void remove(String cardInfo);
}
