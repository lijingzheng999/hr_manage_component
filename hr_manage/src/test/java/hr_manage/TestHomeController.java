package hr_manage;

import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hr.manage.web.controllers.HomeController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-test.xml","classpath*:applicationContext-component.xml" })
@WebAppConfiguration
public class TestHomeController extends AbstractTransactionalJUnit4SpringContextTests{
	//mock模拟session
    private MockHttpSession session;

    //mock模拟request
    private MockHttpServletRequest request;

//	    @Autowired
//	    private PersonalController personalController;

    @Before
    public void setUp() throws Exception {

        this.session = new MockHttpSession();
        this.request = new MockHttpServletRequest();

    }



	    
	    /**
	     * 导入数据单元测试
	     * 
	     * @throws Exception
	     */
	    @Test
	    public void testLogin() throws Exception
	    {
	    	System.out.println();
	    	//构造controller
	    	HomeController homeController = (HomeController) this.applicationContext.getBean("homeController");
	        String result = homeController.login("admin", "admin", "admin");
	        System.out.println("----result :\t" + result);
	        
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
