package hr.manage.component.common.model;

import java.io.Serializable;

public class UploadResult implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 成功与否 OK：保存成功  fail：保存失败
	 */
	private String info;

	/**
	 * 文件原有名称
	 */
	private String name;
	
	/**
	 * 图片路径
	 */
	private String filePath;
	
	/**
	 * 文件完整URL
	 */
	private String downloadUrl;
	
	private String length;
	
	public UploadResult(){}
	public UploadResult(String info, String filePath, String length) {
		super();
		this.info = info;
		this.filePath = filePath;
		this.length = length;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
