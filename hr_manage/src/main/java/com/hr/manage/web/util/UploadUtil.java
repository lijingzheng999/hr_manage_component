package com.hr.manage.web.util;

import hr.manage.component.common.model.UploadResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import net.sourceforge.pinyin4j.PinyinHelper;







import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.hr.manage.config.ServiceConfigFactory;

/**
 * 上传文件的工具类
 * 
 * @author yufeng.che@gmail.com
 * 
 */
public class UploadUtil {
	private static Logger logger = Logger.getLogger(UploadUtil.class);
	private static final String FILE_UPLOAD_PATH=ServiceConfigFactory.getValue("file.upload.path");
	private static final String DOWNLOAD_URL =ServiceConfigFactory.getValue("file.download.url");
	
	public static String upload( @Param("uploadFile")MultipartFile uploadFile) throws Exception{
		UploadResult result = new UploadResult();
		try{
		InputStream  is = uploadFile.getInputStream();
		String filePathInServer ="";
		String prefix=uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1);
		String fileName =System.currentTimeMillis()+new Random().nextInt(100) +"."+prefix;
		String[] arys =new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
		String dateDir =arys[0]+File.separator+arys[1]+File.separator+arys[2]+File.separator;
		String fileUrl=FILE_UPLOAD_PATH+dateDir;
		logger.info("fileUrl=========="+fileUrl);
		String fullUrl=fileUrl+fileName;
		// 创建一个输出流
		File toFileDir = new File(fileUrl);
		// 设置目标文件
		if (!toFileDir.exists()) {
			toFileDir.mkdirs();
		}
		File file =new File(fullUrl);
		logger.info("filePath=========="+fullUrl);
		OutputStream os = new FileOutputStream(file);
		// 设置缓存
		byte[] buffer = new byte[1024];
		int length = 0;
		// 读取myFile文件输出到toFile文件中
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
		os.flush();
		if (is != null) {
			is.close();
		}
		if (os != null) {
			os.close();
		}
		filePathInServer=dateDir+fileName;
		result.setInfo("OK");
		result.setFilePath(filePathInServer);
		result.setDownloadUrl(DOWNLOAD_URL+filePathInServer);
		result.setName(uploadFile.getOriginalFilename());
		
		}catch(Exception e){
			logger.error(e);
			result.setInfo("fail");
		}
		logger.info(new Gson().toJson(result));
		return new Gson().toJson(result);
	}
}
