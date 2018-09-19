package com.hr.manage.web.interceptor;

import hr.manage.component.admin.service.AdminAuthorityServiceHome;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hr.manage.web.annotation.AuthorityCheck;
import com.hr.manage.web.constant.CodeMsg;
import com.hr.manage.web.constant.Constants;
import com.hr.manage.web.constant.JSONResult;
import com.hr.manage.web.util.StringUtil;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.advancedinterceptor.Ordered;

@Component
public class AuthorityInterceptor extends ControllerInterceptorAdapter implements Ordered{
	
	private static Log logger = LogFactory.getLog(AuthorityInterceptor.class);
	
	@Autowired
	private AdminAuthorityServiceHome authorityServiceHome;
	
	@Override
	public int getPriority() {
		return 80;
	}
	
	@Override
	public Object before(Invocation inv) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("-----------LoginInterceptor");
		} 
		if(inv.getMethod().isAnnotationPresent(AuthorityCheck.class)){
			int functionId = inv.getMethod().getAnnotation(AuthorityCheck.class).function();
//			String passport = CookieUtils.getInstance().getCookieValue(inv.getRequest(), Constants.anminCookie);
			HttpSession userSession = inv.getRequest().getSession();
			String loginUserIdString = (String) userSession.getAttribute(Constants.LOGIN_USER_ID_TAG_FOR_AUTH);
			logger.info(loginUserIdString);
			int adminId = StringUtil.conver2Int(loginUserIdString,0);
			logger.info(adminId);
			if(adminId==0){
				return notLoginRedirect(inv);
			}
//			adminId = 216;
			if(authorityServiceHome.haveAuthority(adminId, functionId)){
				return true;
			}else{
				return "@"+JSONResult.error(CodeMsg.NO_AUTHORITY);
			}
		}else{
			return true;
		}
	}
	
	Object notLoginRedirect(Invocation inv) {
		return "@"+JSONResult.error(CodeMsg.NO_LOGIN);
	}
	
}
