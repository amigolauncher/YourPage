package com.gionee.amisystem.depencylib.http;

/**
 * Created by lee on 16-5-16.
 */
public class GnHttpConfig {
    public static final int DEFAULT_CONNECTION_TIMEOUT_INMILLS = 3000;
    public static final int DEFAULT_READ_TIMEOUT_INMILLLS = 5000;
    public static final String DEFAULT_ENCODE = "utf-8";

    private int mReadTimeoutInMills = DEFAULT_CONNECTION_TIMEOUT_INMILLS;
    private int mConnectionTimeoutInMills = DEFAULT_READ_TIMEOUT_INMILLLS;
    private String mEncode = DEFAULT_ENCODE;

    public GnHttpConfig(int mReadTimeoutInMills, int mConnectionTimeoutInMills) {
        this.mConnectionTimeoutInMills = mConnectionTimeoutInMills;
        this.mReadTimeoutInMills = mReadTimeoutInMills;
    }

    public GnHttpConfig(){

    }

    public int getConnectionTimeOut() {
        return mConnectionTimeoutInMills;
    }

    public void setConnectionTimeOut(int mConnectionTimeOut) {
        this.mConnectionTimeoutInMills = mConnectionTimeOut;
    }

    public int getReadtimeOut() {
        return mReadTimeoutInMills;
    }

    public void setReadtimeOut(int mReadtimeOut) {
        this.mReadTimeoutInMills = mReadtimeOut;
    }

    public String getEncode() {
        return mEncode;
    }

    public void setEncode(String mEncode) {
        this.mEncode = mEncode;
    }
}
