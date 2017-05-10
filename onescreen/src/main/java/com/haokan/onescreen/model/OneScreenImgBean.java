package com.haokan.onescreen.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by chenming on 2016/6/12.
 */
public class OneScreenImgBean implements Parcelable {
	private int size;
	private List<ListBean> list;
	private String editorRe;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<ListBean> getList() {
		return list;
	}

	public void setList(List<ListBean> list) {
		this.list = list;
	}
	
	
	public String getEditorRe() {
		return editorRe;
	}

	public void setEditorRe(String editorRe) {
		this.editorRe = editorRe;
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.size);
		dest.writeList(this.list);
		dest.writeString(this.editorRe);
	}

	public OneScreenImgBean() {
	}

	protected OneScreenImgBean(Parcel in) {
		this.size = in.readInt();
		this.list = new ArrayList<ListBean>();
		in.readList(this.list, List.class.getClassLoader());
		this.editorRe = in.readString();
	}

	public static final Parcelable.Creator<OneScreenImgBean> CREATOR = new Parcelable.Creator<OneScreenImgBean>() {
		public OneScreenImgBean createFromParcel(Parcel source) {
			return new OneScreenImgBean(source);
		}

		public OneScreenImgBean[] newArray(int size) {
			return new OneScreenImgBean[size];
		}
	};
}
