package com.hr.manage.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataMapUtil {
	
	public static Map<String, Object> getDataMap(String keyName, Object obj, Long count, Long pageCount){
		Map<String, Object> dataMap = new HashMap<>();
		if(count != null){
			dataMap.put("count", count);
		}
		if(pageCount != null){
			dataMap.put("pageCount", pageCount);
		}
		if(obj == null) {
			obj = new ArrayList<>();
		}
		dataMap.put(keyName, obj);
		return dataMap;
	}
	
}
