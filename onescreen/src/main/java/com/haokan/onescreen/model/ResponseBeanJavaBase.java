package com.haokan.onescreen.model;

/**
 * Created by on 2016/12/13.
 */
public class ResponseBeanJavaBase<T> {

    private HeaderBean header;
    private T body;


    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

}
