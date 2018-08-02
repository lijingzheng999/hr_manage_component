package com.hr.manage.web.util;

import net.paoding.rose.load.context.RoseAppContext;

public class RoseAppContextProvider {

	private RoseAppContext rose = new RoseAppContext();
	private RoseAppContextProvider(){}
	
	private static RoseAppContextProvider instance = new  RoseAppContextProvider();
	
	public static RoseAppContextProvider getInstance(){
		if(instance == null){
			instance = new  RoseAppContextProvider();
		}
		return instance;
	}
	
	public RoseAppContext getRoseAppContext(){
		return rose;
	}
	
}
