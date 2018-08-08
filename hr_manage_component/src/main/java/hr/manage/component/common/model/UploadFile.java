package hr.manage.component.common.model;

import java.io.Serializable;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

//上传文件
@Table("upload_file")
public class UploadFile implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3881162222229699275L;


	/**
	 * 自增id
	 */
    @Column(pk=true,value = "id")
	private long id;
	
	/**
	 * 关联员工id
	 */
    @Column(value = "personal_info_id")
	private Integer personalInfoId;
	
	/**
	 * 关联文件类型 1员工图片 2带扩展
	 */
	@Column(value = "type")
	private Integer type;
	
	/**
	 * 文件名称
	 */
	@Column(value = "file_name")
	private String fileName;
	
	/**
	 * 文件路径
	 */
	@Column(value = "file_url")
	private String fileUrl;
	
	/**
	 * 文件类型
	 */
	@Column(value = "file_type")
	private String fileType;
	
	/**
	 * 备注  默认存全路径
	 */
	@Column(value = "comment")
	private String comment;
	
	/**
	 * 逻辑删除 0 已删除  1在用
	 */
    @Column(value = "is_del")
	private int isDel;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPersonalInfoId() {
		return personalInfoId;
	}

	public void setPersonalInfoId(Integer personalInfoId) {
		this.personalInfoId = personalInfoId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	

}
