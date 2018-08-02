package com.hr.manage.web.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringToListUtil {
	
	public static List<Integer> getList(String arrStr, String splitRegex) {
		List<Integer> list = new  ArrayList<>();
		if(StringUtils.isBlank(arrStr) || StringUtils.isBlank(splitRegex)){
			return list;
		}
		String[] arrs = arrStr.split(splitRegex);
		for (int i = 0; i < arrs.length; i++) {
			if(!StringUtils.isBlank(arrs[i])){
				try {
					list.add(Integer.parseInt(arrs[i]));
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}else {
				continue;
			}
		}
		return list;
	}
	
	public static List<String> getStringList(String arrStr, String splitRegex) {
		List<String> list = new  ArrayList<>();
		if(StringUtils.isBlank(arrStr) || StringUtils.isBlank(splitRegex)){
			return list;
		}
		String[] arrs = arrStr.split(splitRegex);
		for (int i = 0; i < arrs.length; i++) {
			if(!StringUtils.isBlank(arrs[i])){
				try {
					list.add(arrs[i]);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}else {
				continue;
			}
		}
		return list;
	}
}
