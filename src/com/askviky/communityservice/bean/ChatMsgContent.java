package com.askviky.communityservice.bean;

import java.util.List;

public class ChatMsgContent {

	private int res;  //结果状态常量
	private List<ChatMsgContent> data;  //login内容对象

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public List<ChatMsgContent> getData() {
		return data;
	}

	public void setData(List<ChatMsgContent> data) {
		this.data = data;
	}

	public ChatMsgContent() {
		super();
	}

	public ChatMsgContent(int res, List<ChatMsgContent> data) {
		super();
		this.res = res;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ServeListBean [res=" + res + ", data=" + data + "]";
	}

}
