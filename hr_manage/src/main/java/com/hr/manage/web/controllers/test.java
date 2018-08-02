package com.hr.manage.web.controllers;

import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.service.PersonalService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;









import com.alibaba.fastjson.JSONObject;



public class test {
	
	 private static ClassPathXmlApplicationContext context;


//		static PersonalController controller = new PersonalController();
//		
//		static{
//			 context = new ClassPathXmlApplicationContext(
//					new String[] { "classpath:applicationContext.xml","classpath*:applicationContext-component.xml" });
//
//			context.start();
//		}
		
	public static void main(String[] args) throws IOException {
	
		PersonalController controller = new PersonalController();
		FileInputStream fis = new FileInputStream("D:/test/test1.xlsx");

        MockMultipartFile mfile = new MockMultipartFile("D:/test", "test1.xlsx", "multipart/form-data", fis);

        try {
        	String result =controller.importExcel(mfile);
             System.out.println(result);
           } catch (Exception e) {
             e.printStackTrace();
           }
	}
	
}
