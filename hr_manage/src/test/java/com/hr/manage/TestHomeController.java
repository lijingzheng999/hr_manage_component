package com.hr.manage;


import java.io.FileInputStream;













import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hr.manage.web.controllers.HomeController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class TestHomeController {

	

	private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {

		 this.mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();

    }



	    
	    /**
	     * 导入数据单元测试
	     * 
	     * @throws Exception
	     */
	    @Test
	    public void testLogin() throws Exception
	    {
	    	try {
	    	      MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/login"))
	    	                .andExpect(MockMvcResultMatchers.status().is(200))
	    	                .andDo(MockMvcResultHandlers.print())
	    	                .andReturn();
	    	      int status = mvcResult.getResponse().getStatus();
	    	      System.out.println("请求状态码：" + status);
	    	      String result = mvcResult.getResponse().getContentAsString();
	    	      System.out.println("接口返回结果：" + result);
	    	      JSONObject resultObj = JSON.parseObject(result);
	    	      // 判断接口返回json中success字段是否为true
	    	      Assert.assertTrue(resultObj.getBooleanValue("success"));
	    	    } catch (Exception e) {
	    	      e.printStackTrace();
	    	    }
	    	
//	    	System.out.println();
////	    	MvcResult  result = mockMvc.perform(get("/login?username=admin&password=admin&captcha=dfds"))
////	    			.andDo(MockMvcResultHandlers.print())  
////	    		       .andReturn();
//	    	 mockMvc.perform(post("/login", "json").accept(MediaType.APPLICATION_JSON)
//	                .param("username", "1")
//	                .param("password", "abc")
//	                .param("captcha", "abc")
//	                )
//	                .andDo(print())
//	                .andExpect(status().isOk());
//		    	System.out.println(result);
	        
//	        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/login?username=admin&password=admin&captcha=dfds"));
//	        MvcResult mvcResult = resultActions.andReturn();
//	        System.out.println("----mvcResult :\t" + mvcResult.getResponse().getStatus());
//	        System.out.println("----reponse :\t" + mvcResult.getResponse());
//	    	String result = mockMvc.perform(get("/login?username=admin&password=admin&captcha=dfds"))
//	            .andExpect(status().isOk())
//	            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//	            .andReturn().getResponse().getContentAsString();
//	    	System.out.println(result);

	    }

	
}
