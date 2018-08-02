package com.hr.manage.web.constant;

import com.alibaba.fastjson.JSONObject;

public class JSONResult {
	
	public static String success(Object data) {
		return JSONObject.toJSONString(ResultInfo.success(data));
	}
	
	public static String success() {
		return JSONObject.toJSONString(ResultInfo.success());
	}
	
	public static String error(CodeMsg codeMsg) {
		return JSONObject.toJSONString(ResultInfo.error(codeMsg));
	}
	
	public static String error(CodeMsg codeMsg,String extraMsg){
		return JSONObject.toJSONString(ResultInfo.error(codeMsg,extraMsg));
	}
}
