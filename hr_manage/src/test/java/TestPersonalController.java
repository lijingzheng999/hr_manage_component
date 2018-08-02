

import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.hr.manage.web.controllers.PersonalController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml","classpath*:applicationContext-component.xml" })
@WebAppConfiguration
public class TestPersonalController {
	    protected MockMvc mockMvc;

	   @Autowired
	   protected WebApplicationContext wac;

//	    @Autowired
//	    private PersonalController personalController;

	    @Before  
	    public void setup() {   
//	        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	    	this.mockMvc = MockMvcBuilders.standaloneSetup(new PersonalController()).build();
	    } 

	    
	    /**
	     * 导入数据单元测试
	     * 
	     * @throws Exception
	     */
	    @Test
	    @Transactional
	    @Rollback(false)
	    public void testImportExcel() throws Exception {
	    	 FileInputStream fis = new FileInputStream("D:/test/test1.xlsx");

		        MockMultipartFile mfile = new MockMultipartFile("D:/test", "test1.xlsx", "multipart/form-data", fis);

		        try {
		            String result =  mockMvc.perform(
		                 MockMvcRequestBuilders
		                     .fileUpload("/personal/importExcel")
		                     .file(
		                    		 mfile
		                     )
		             ).andExpect(MockMvcResultMatchers.status().isOk())
		             .andReturn().getResponse().getContentAsString();
		             System.out.println(result);
		           } catch (Exception e) {
		             e.printStackTrace();
		           }


//	        MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();
//
//	        request.setMethod("POST");
//
//	        request.setContentType("multipart/form-data");
//
//	        request.addHeader("Content-type", "multipart/form-data");

	       
//	        String resultString= this.personalController.importExcel(mfile);

//	        Assert.assertTrue(true);

	    }
	
}
