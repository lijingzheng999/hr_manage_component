package com.hr.manage.web.constant;

public class ResultInfo<T> {

	private int code;

	private String msg;

	private T data;
	
	private ResultInfo(CodeMsg codeMsg) {
		if (codeMsg == null)
			return;
		this.code = codeMsg.getCode();
		this.msg = codeMsg.getMsg();
	}

	private ResultInfo(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	
	private ResultInfo(T data) {
		this.code = CodeMsg.SUCCESS.getCode();
		this.msg = CodeMsg.SUCCESS.getMsg();
		this.data = data;
	}
	
	public static <T> ResultInfo<T> success (T data) {
		return new ResultInfo<T>(data);
	}
	
	public static <T> ResultInfo<T> success() {
		return new ResultInfo<T>(CodeMsg.SUCCESS);
	}
	
	public static <T> ResultInfo<T> error (CodeMsg codeMsg) {
		return new ResultInfo<T>(codeMsg);
	}
	
	public static <T> ResultInfo<T> error (CodeMsg codeMsg, String extraMsg) {
		String message = codeMsg.getMsg() + "," + extraMsg;
//		codeMsg.setMsg(codeMsg.getMsg() + "," + extraMsg);
		return new ResultInfo<T>(codeMsg.getCode(),message);
	}
	
	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}
	
}
