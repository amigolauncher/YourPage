package com.gionee.amisystem.depencylib.http;

/**
 * Created by lee on 16-5-16.
 */
public class GnHttpSimpleFactory {

    public static GnHttpRequest getDefalutHttpRequest() {
        GnHttpRequest request = new NativeHttpUtils();
        request.setHttpConfig(new GnHttpConfig());
        return request;
    }
}
