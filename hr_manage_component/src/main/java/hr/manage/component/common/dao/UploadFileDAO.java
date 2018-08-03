package hr.manage.component.common.dao;

import hr.manage.component.common.model.CommonType;
import hr.manage.component.common.model.UploadFile;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.plugin.sql.GenericDAO;

@DAO(catalog="hr_manage")
public interface UploadFileDAO  extends GenericDAO<UploadFile,Integer>{
	
	public static final String TABLE = " upload_file ";
	
    public static final String COLUMNS = " id, personal_info_id, type, file_name, file_url, file_type, comment, is_del ";
	
//	public static final String parameters = " :1.personal_info_id, :1.type, :1.fileName, :1.fileUrl, :1.fileType, :1.comment, :1.isDel ";

	    
	    @SQL("SELECT  " + COLUMNS + " FROM "+TABLE+" WHERE 1 = 1 " +
	            "#if(:1.personalInfoId != null && :1.personalInfoId >0) { and personal_info_id = :1 } " +
	            "#if(:1.type != null && :1.type >0) { and type = :1 } " +
	            " and is_del=1 " +
	    		" order by id ")
	    List<UploadFile> listUploadFile(Integer personalInfoId,Integer type);
	    
	    @SQL(" UPDATE "+TABLE +
	           " set is_del=0 " +
	    		" where id = :1 ")
	    int deleteUploadFile(Integer fileId);

}
