package com.lctech.swm.domain;

public class MessageToast {
	private String msg;
	private String msgType;
	private String msgTitle;

	public MessageToast() {
		super();
	}

	public MessageToast(String msg, String msgType, String msgTitle) {
		super();
		this.msg = msg;
		this.msgType = msgType;
		this.msgTitle = msgTitle;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

}
