package com.haokan.onescreen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SmallsBean implements Parcelable {

	/**
	 * imageId : 243403 imgClickUrl :
	 * http://levect.com/zh/detail/tag/26529/img/243402
	 * /simg/243403/?eid=102002&appid=10047 imageUrl :
	 * http://res.levect.com/hkimages
	 * /48/59/148140867916090095948.jpg@!480x854?k=10047&pid=0&eid=102002
	 */

	private int imageId;
	private String imgClickUrl;
	private String imageUrl;
	private String nativeUrl;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImgClickUrl() {
		return imgClickUrl;
	}

	public void setImgClickUrl(String imgClickUrl) {
		this.imgClickUrl = imgClickUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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
		dest.writeInt(this.imageId);
		dest.writeString(this.imgClickUrl);
		dest.writeString(this.imageUrl);
		dest.writeString(this.nativeUrl);
	}

	public SmallsBean() {
	}

	protected SmallsBean(Parcel in) {
		this.imageId = in.readInt();
		this.imgClickUrl = in.readString();
		this.imageUrl = in.readString();
		this.nativeUrl = in.readString();
	}

	public static final Parcelable.Creator<SmallsBean> CREATOR = new Parcelable.Creator<SmallsBean>() {
		public SmallsBean createFromParcel(Parcel source) {
			return new SmallsBean(source);
		}

		public SmallsBean[] newArray(int size) {
			return new SmallsBean[size];
		}
	};
}
