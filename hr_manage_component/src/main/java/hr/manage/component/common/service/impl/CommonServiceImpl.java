package hr.manage.component.common.service.impl;

import hr.manage.component.common.constants.DepartmentConstants;
import hr.manage.component.common.constants.UnitConstants;
import hr.manage.component.common.dao.CommonTypeDAO;
import hr.manage.component.common.dao.SettingHolidayDAO;
import hr.manage.component.common.dao.UploadFileDAO;
import hr.manage.component.common.model.CommonType;
import hr.manage.component.common.model.SettingHoliday;
import hr.manage.component.common.model.UploadFile;
import hr.manage.component.common.service.CommonService;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.dao.PersonalWorkInfoDAO;
import hr.manage.component.personal.model.PersonalAll;
import hr.manage.component.personal.model.PersonalAllExport;
import hr.manage.component.personal.model.PersonalCondition;
import hr.manage.component.personal.model.PersonalInfo;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;
import hr.manage.component.personal.service.PersonalService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CommonServiceImpl implements CommonService {
	
	private final Log logger = LogFactory.getLog(CommonServiceImpl.class);
	@Autowired
	CommonTypeDAO commonTypeDAO;
	@Autowired
	UploadFileDAO uploadFileDAO;
	@Autowired
	SettingHolidayDAO settingHolidayDAO;
	
	@Override
	public int saveUploadFile(UploadFile file){
		return uploadFileDAO.save(file);
	}
	
	@Override
	public int deleteUploadFile(Integer fileId){
		return uploadFileDAO.deleteUploadFile(fileId);
	}
	
	@Override
	public List<UploadFile> listUploadFile(Integer personalInfoId,Integer type){
		return uploadFileDAO.listUploadFile(personalInfoId, type);
	}
	
	@Override
	public List<CommonType> listCommonType(Integer type){
		return commonTypeDAO.listCommonType(type);
	}
	
	@Override
	public List<SettingHoliday> listSettingHoliday(Integer type,Date startDate, Date endDate){
		return settingHolidayDAO.listSettingHoliday(type,startDate,endDate);
	}
	@Override
	public int deleteSettingHoliday(Integer holiDayId){
		return settingHolidayDAO.deleteSettingHoliday(holiDayId);
	}
	@Override
	public int saveSettingHoliday(SettingHoliday holiday){
		return settingHolidayDAO.save(holiday);
	}
	@Override
	public SettingHoliday getSettingHolidayByDay(Date curDay){
		return settingHolidayDAO.getSettingHolidayByDay(curDay);
	}
}
