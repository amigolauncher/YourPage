package com.gionee.amisystem.depencylib.http;

import java.util.Map;

/**
 * Created by lee on 16-5-16.
 */
public interface GnHttpRequest {
    // send get request with full url .
    public GnHttpResponse get(String url);

    // send get requst with base url . params will be concat as a full url .such as url?key1=value1&key2=value2
    public GnHttpResponse get(String url, Map<String, String> params);

    // send post request .data with from .
    public GnHttpResponse post(String url, Map<String, String> parms);

    // send post requst .data send as from .
    public GnHttpResponse post(String url, String params);

    // send post requst .data send as json .
    public GnHttpResponse postWithJson(String url, String params);

    // httpconfig .such as read timeout , connected timeout
    public void setHttpConfig(GnHttpConfig httpConfig);
}
