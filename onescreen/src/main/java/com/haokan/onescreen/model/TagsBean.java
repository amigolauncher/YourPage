package com.haokan.onescreen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TagsBean implements Parcelable {
	
	  /**
     * name : 田馥甄
     * url : http://levect.com/zh/detail/tag/21244/img/245719/?eid=102002&appid=10047
     */

    private String name;
    private String url;
    private String tagId;
    private String nativeUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    
    
    public String getNativeUrl() {
        return nativeUrl;
    }

    public void setNativeUrl(String nativeUrl) {
        this.nativeUrl = nativeUrl;
    }
    


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.tagId);
        dest.writeString(this.nativeUrl);
    }

    public TagsBean() {
    }

    protected TagsBean(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.tagId = in.readString();
        this.nativeUrl = in.readString();
    }

    public static final Parcelable.Creator<TagsBean> CREATOR = new Parcelable.Creator<TagsBean>() {
        public TagsBean createFromParcel(Parcel source) {
            return new TagsBean(source);
        }

        public TagsBean[] newArray(int size) {
            return new TagsBean[size];
        }
    };
}
