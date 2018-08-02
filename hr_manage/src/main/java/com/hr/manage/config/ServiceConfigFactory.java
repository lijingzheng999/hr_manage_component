package com.hr.manage.config;

import java.io.InputStreamReader;
import java.util.Properties;

public class ServiceConfigFactory {

	private static final String CMS_FILE_NAME = "cms.";
    private static final String ENV = System.getProperty("ENV");
    private static final String PREFIX_FILE_NAME = (ENV == null ? "online" : ENV) + ".properties";
    
    private static Properties cmsConfig = new Properties();
	
    static {
		try {
			cmsConfig.load(new InputStreamReader(ServiceConfigFactory.class.getClassLoader().getResourceAsStream(CMS_FILE_NAME + PREFIX_FILE_NAME),"UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static String getValue(String key) {
    	return cmsConfig.getProperty(key);
    }
    
}
