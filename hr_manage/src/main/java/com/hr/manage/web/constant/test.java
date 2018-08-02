package com.hr.manage.web.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.alibaba.fastjson.JSONObject;



public class test {
	
	public static void main(String[] args) {
		
		System.out.println(JSONObject.toJSONString(ResultInfo.error(CodeMsg.SERVER_ERROR)));
		
		Map<String, Object> dataMap = new HashedMap();
		List<String> list = new ArrayList<>();
		list.add("第一个元素");
		list.add("第二个元素");
		list.add("第三个元素");
		list.add("第四个元素");
		list.add("第五个元素");
		dataMap.put("basicAccountViewList", list);
		dataMap.put("count", 100);
		dataMap.put("pageCount", 3);
		
		System.out.println(JSONObject.toJSONString(ResultInfo.success(dataMap)));
		
		
		System.out.println(JSONObject.toJSONString(ResultInfo.error(CodeMsg.ERROR,"参数有误")));
		
		int count = 1;
		int pageSize = 5;
		
		int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		System.out.println("pageCount = " + pageCount);
	}
	
}
