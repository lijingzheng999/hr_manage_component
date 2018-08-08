package com.hr.manage.web.controllers;


import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.mock.web.MockMultipartFile;



public class test {
//	
//	 private static ClassPathXmlApplicationContext context;


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
