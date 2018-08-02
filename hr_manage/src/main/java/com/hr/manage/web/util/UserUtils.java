package com.hr.manage.web.util;


import hr.manage.component.admin.model.Admin;
import hr.manage.component.admin.model.AdminRole;

import java.util.Date;

import javax.servlet.http.HttpSession;

import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.math.NumberUtils;



public class UserUtils {

	public static int getUserId(Invocation inv){
		HttpSession userSession = inv.getRequest().getSession();
		if(userSession.getAttribute("loginUserId") == null){
			return 0;
		}
		else{
			return NumberUtils.toInt(userSession.getAttribute("loginUserId").toString());
		}		
	}
	
	public static String getUserLastLoginIP(Invocation inv){
		HttpSession userSession = inv.getRequest().getSession();
		if(userSession.getAttribute("lastloginip") == null){
			return null;
		}
		else{
			return userSession.getAttribute("lastloginip").toString();
		}		
	}
	
	public static Date getUserLastLoginTime(Invocation inv){
		HttpSession userSession = inv.getRequest().getSession();
		if(userSession.getAttribute("lastlogintime") == null){
			return null;
		}
		else{
			return (Date)userSession.getAttribute("lastlogintime");
		}		
	}
	
	public static Admin getUser(Invocation inv){
		return (Admin)inv.getRequest().getSession().getAttribute("user");		
	}
	
	public static AdminRole getRole(Invocation inv){
		return (AdminRole)inv.getModel("role");	
	}
}
