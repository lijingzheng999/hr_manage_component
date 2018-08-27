package com.hr.manage.web.interceptor;

import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.service.AdminService;

import javax.servlet.http.Cookie;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hr.manage.web.annotation.NotCareLogin;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.JSONResult;

@Component
public class SiteInterceptor extends ControllerInterceptorAdapter {

	@Autowired
	private AdminService adminService;


	@Override
	protected Object before(Invocation inv) throws Exception {
		inv.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		inv.getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		inv.getResponse().setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		inv.getResponse().setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		Class<? extends Object> controller = inv.getController().getClass();
		boolean isPresent = controller.isAnnotationPresent(NotCareLogin.class)
				|| inv.getMethod().isAnnotationPresent(NotCareLogin.class);

		if (isPresent) {
			return true;
		}
		Admin user = (Admin) inv.getRequest().getSession().getAttribute("user");
		if (user == null) {
			return notLoginRedirect(inv);
		}
		// int userId = UserUtils.getUserId(inv);
		// if(userId <= 0){
		// return notLoginRedirect(inv);
		// }
		// Admin user = adminService.getUserById(UserUtils.getUserId(inv));
		// List<Integer> roleids = AdminUtil.getAdminRoleIds(user.getRoleids());
		// List<AdminRole> adminRoles = adminService.getAdminRoleByIds(roleids);
		// List<Integer> funcIntegers = new ArrayList<Integer>();
		// if(adminRoles != null){
		// for(AdminRole adminRole : adminRoles){
		// funcIntegers.addAll(AdminUtil.getAdminRoleIds(adminRole.getFunctionIds()));
		// }
		// HashSet<Integer> set = new HashSet<Integer>(funcIntegers);
		// funcIntegers.clear();
		// funcIntegers.addAll(set);
		// }
		// AdminRole role = adminService.getRole(user.getRoleid());
		// if (role != null){
		// Gson gson = new Gson();
		// Map<String, String> map = gson.fromJson(role.getRoleModules(), new
		// TypeToken<Map<String, String>>(){}.getType());
		// inv.addModel("roleMap", map);
		// } else{
		// inv.addModel("roleMap", new HashMap<String, String>());
		// }
		// inv.addModel("role", role);
		// inv.addModel("user", user);
		// 设置UTF-8编码
		inv.getResponse().setCharacterEncoding("utf-8");
        
		Cookie[] cookies = inv.getRequest().getCookies();  
		String value =null;  
		        if (cookies != null) {  
		                Cookie cookie = cookies[0];  
		                if (cookie != null) {  
		                    /*cookie.setMaxAge(3600); 
		                    cookie.setSecure(true); 
		                    resp.addCookie(cookie);*/  
		                      
		                    //Servlet 2.5不支持在Cookie上直接设置HttpOnly属性  
		                    value = cookie.getValue();  
		                }
		        }
		inv.getResponse().addHeader("Set-Cookie", "JSESSIONID=" + value + ";PATH=/admin;HttpOnly;secure");		
		String localurl = inv.getRequest().getServerName()+":" + inv.getRequest().getServerPort();		
		String ConSecPol = "script-src "+ localurl + " cdn.datatables.net cdn.bootcss.com 'unsafe-inline' 'unsafe-eval'"+"; object-src "+ localurl +" cdn.datatables.net cdn.bootcss.com;style-src "+ localurl + " cdn.datatables.net cdn.bootcss.com 'unsafe-inline' 'unsafe-eval';report-uri " +localurl+"/error.jsp";
	
		inv.getResponse().addHeader("Content-Security-Policy", ConSecPol);

		return true;
	}

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {
		inv.getResponse().setCharacterEncoding("UTF-8");
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		System.out.println("adminUrl:"+inv.getRequest().getContextPath());
		inv.addModel("adminUrl", inv.getRequest().getContextPath());
		inv.getResponse().setCharacterEncoding("UTF-8");
	
		return super.after(inv, instruction);
	}

	Object notLoginRedirect(Invocation inv) {
		
			return "@"+JSONResult.error(CodeMsg.NO_LOGIN);
		
	}

}
