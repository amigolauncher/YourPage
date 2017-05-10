package com.haokan.onescreen.model;

import java.util.List;

public class BaseCallModel<T> {
	public int err_code;
	public String err_msg;
	public List<T> data;
	public String cardtitle;

}
