package com.askviky.communityservice.bean;

public class ChatMsgEntity {

	private int id;
	private int type = 1;
	private int img = 0;
	private String title;
	private String time;
	private String msg;

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String date) {
		this.time = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String text) {
		this.msg = text;
	}

	public int getType() {
		return type;
	}

	public void setType(int isComMsg) {
		this.type = isComMsg;
	}

	public ChatMsgEntity() {

	}

	public ChatMsgEntity(String title, String time, String msg, int isComMsg, int img) {
		super();
		this.title = title;
		this.time = time;
		this.msg = msg;
		this.type = isComMsg;
		this.img = img;
	}
}
