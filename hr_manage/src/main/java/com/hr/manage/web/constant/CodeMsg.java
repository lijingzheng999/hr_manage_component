package com.hr.manage.web.constant;

public enum CodeMsg {
	
	/**
	 * 操作成功
	 */
	SUCCESS (0000, "操作成功"),
	
	/**
	 * 操作失败
	 */
	ERROR (-1, "操作失败"),
	
	/**
	 * 没有访问权限
	 */
	NO_AUTHORITY(1001,"没有访问权限"),
	

	/**
	 * 没有登陆
	 */
	NO_LOGIN(2001,"没有登陆"),
	
	/**
	 * 内部平台错误
	 */
	SERVER_ERROR (9999, "内部平台错误");
	
	private int code;

	private String msg;
	
	private CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}	
	
}
