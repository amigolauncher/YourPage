package com.haokan.onescreen.model;


public class TitleAndUrlModel {

	public int err_code;
	public String err_msg;
	public TitleAndUrl data;
	public String ip;
	public int time;

	public int getErr_code() {
		return err_code;
	}

	public void setErr_code(int err_code) {
		this.err_code = err_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	public TitleAndUrl getData() {
		return data;
	}

	public void setData(TitleAndUrl data) {
		this.data = data;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public class TitleAndUrl{
		public String barname;
		public String url_title;
		public String url_click;

		public String getBarname() {
			return barname;
		}

		public void setBarname(String barname) {
			this.barname = barname;
		}

		public String getUrl_title() {
			return url_title;
		}

		public void setUrl_title(String url_title) {
			this.url_title = url_title;
		}

		public String getUrl_click() {
			return url_click;
		}

		public void setUrl_click(String url_click) {
			this.url_click = url_click;
		}
	}


}
