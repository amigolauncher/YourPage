package com.gionee.amisystem.yourpage.statistics.bi;

import android.content.ContentValues;

import com.gionee.amisystem.yourpage.statistics.StatisticConstant;

/**
 * Created by lee on 11/2/15.
 */
public class StatisticBiInfo {
    public static final String TAG = "StatisticBiInfo";

    public static final int SOURCE_ALL = -1;

    private long mId;
    private int mSourceFrom;
    private String mParams;
    private String mUrl;

    public StatisticBiInfo(String url, String mParams) {
        this.mParams = mParams;
        this.mUrl = url;
        mSourceFrom = SOURCE_ALL;
    }

    public StatisticBiInfo(int sourceFrom, String url, String mParams) {
        this.mSourceFrom = sourceFrom;
        this.mUrl = url;
        this.mParams = mParams;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setParms(String params) {
        this.mParams = params;
    }

    public String getParams() {
        return mParams;
    }

    public int getSourceFrom() {
        return mSourceFrom;
    }

    public void setSourceFrom(int sourceFrom) {
        this.mSourceFrom = sourceFrom;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(StatisticConstant.Column.SOURCE_FROM, mSourceFrom);
        values.put(StatisticConstant.Column.STATISTIC_INFO, mParams);
        values.put(StatisticConstant.Column.URL, mUrl);
        return values;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" mSourceFrom ");
        sb.append(mSourceFrom);
        sb.append(" mParams ");
        sb.append(mParams);
        sb.append(" url ").append(mUrl);

        return sb.toString();
    }

    public boolean isEmpty() {
        if (mParams == null || mParams.trim().length() == 0 || mUrl == null || mUrl.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
