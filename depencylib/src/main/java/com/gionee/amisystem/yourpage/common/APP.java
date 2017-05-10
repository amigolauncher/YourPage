package com.gionee.amisystem.yourpage.common;

import android.content.Context;

import com.gionee.amisystem.depencylib.IYourpageService;

/**
 * Created by orgcheng on 17-4-12.
 * <p>
 * save the right ApplicationContext for different situation
 */

public class APP {
    private static Context sAppContext;

    public static Context getAppContext() {
        return sAppContext;
    }

    public static void setAppContext(Context applicationContext) {
        sAppContext = applicationContext;
    }

    private static IYourpageService sYourPageService;

    public static IYourpageService getYourPageService() {
        return sYourPageService;
    }

    public static void setYourPageService(IYourpageService yourPageService) {
        sYourPageService = yourPageService;
    }

}
