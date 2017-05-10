package com.haokan.onescreen.model;

/**
 * Created by haokao on 2016/11/29.
 */

public class HeaderBean {

    /**
     * messageID : 20161129193032000000000001
     * resCode : 0
     * resMsg : success|总局返回：success
     * timeStamp : 20161129200048
     * transactionType : 700101
     */
    private String messageID;
    private int resCode;
    private String resMsg;
    private String timeStamp;
    private String transactionType;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
