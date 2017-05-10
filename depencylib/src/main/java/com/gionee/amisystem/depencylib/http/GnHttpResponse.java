package com.gionee.amisystem.depencylib.http;


/**
 * Created by lee on 16-5-16.
 */
public class GnHttpResponse {

    public static final String TAG = "GnHttpResponse";
    private int mStatusCode = -100;
    private String mResponseString = "";

    public GnHttpResponse() {

    }

    public GnHttpResponse(int statusCode, String responseString) {
        this.mStatusCode = statusCode;
        this.mResponseString = responseString;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int mStatusCode) {
        this.mStatusCode = mStatusCode;
    }

    public String getResponseString() {
        return mResponseString;
    }

    public void setResponseString(String mResponseString) {
        this.mResponseString = mResponseString;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" status code ").append(mStatusCode);
        sb.append(" response string ").append(mResponseString);
        return sb.toString();
    }
}
