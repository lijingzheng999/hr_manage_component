package hr.manage.component.personal.service.impl;

import hr.manage.component.common.constants.DepartmentConstants;
import hr.manage.component.common.constants.UnitConstants;
import hr.manage.component.personal.dao.PersonalInfoDAO;
import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.dao.PersonalWorkInfoDAO;
import hr.manage.component.personal.model.PersonalAll;
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
public class PersonalServiceImpl implements PersonalService {
	
	private final Log logger = LogFactory.getLog(PersonalServiceImpl.class);
	@Autowired
	PersonalInfoDAO personalInfoDAO;
	@Autowired
	PersonalSalaryInfoDAO personalSalaryInfoDAO;
	@Autowired
	PersonalWorkInfoDAO personalWorkInfoDAO;
	
	@Override
	public int checkPersonalByNameAndCard(String name, String identityCard){
		return personalInfoDAO.checkPersonalByNameAndCard(name, identityCard);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public synchronized int savePersonalAllListRecord(List<PersonalAll> personAllList) {
		int result=0;
		for(PersonalAll curPerson : personAllList){
			 PersonalInfo person= curPerson.getPersonalInfo();
             PersonalWorkInfo work= curPerson.getPersonalWorkInfo();
             PersonalSalaryInfo salary =curPerson.getPersonalSalaryInfo();
             //补全默认值
             //生成员工编号  项目+部门+自增编号
             String unitCode= UnitConstants.seletCode(work.getExpatriateUnit());//外派简称
             String departCode= DepartmentConstants.seletCode(work.getDepartment());//部门简称
             int personNum = personalInfoDAO.countPersonalInfo();
             String strPersonNum = new DecimalFormat("0000").format(personNum+1);
             String employeeNumber = unitCode+departCode+strPersonNum;
             logger.info("savePersonalAllListRecord : 员工编号："+employeeNumber);
     		
             person.setEmployeeNumber(employeeNumber);
             person.setIsDel(1);
             person.setCreateTime(new Date());
             //保存员工个人信息表,返回主键ID
             Integer personalInfoId = personalInfoDAO.save(person);
             work.setPersonalInfoId(personalInfoId);
             work.setIsDel(1);
             work.setCreateTime(new Date());
             Integer workId= personalWorkInfoDAO.save(work);
             salary.setPersonalInfoId(personalInfoId);
             salary.setIsDel(1);
             salary.setCreateTime(new Date());
             Integer salaryId= personalSalaryInfoDAO.save(salary);
             logger.info("savePersonalAll : 员工编号："+employeeNumber +person.getName());
		}
		result=1;
		return result;
	}
	
	@Override
	public PersonalAll getPersonalAllInfoById(Integer personalInfoId){
		PersonalAll personalAll = new PersonalAll();
		PersonalInfo personalInfo= personalInfoDAO.getPersonalInfoById(personalInfoId);
        PersonalWorkInfo personalWorkInfo= personalWorkInfoDAO.getPersonalWorkInfoById(personalInfoId);
        PersonalSalaryInfo personalSalaryInfo =personalSalaryInfoDAO.getPersonalSalaryInfoById(personalInfoId);
        personalAll.setPersonalInfo(personalInfo);
        personalAll.setPersonalWorkInfo(personalWorkInfo);
        personalAll.setPersonalSalaryInfo(personalSalaryInfo);
        return personalAll;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int deletePersonalAllInfoById(Integer personalInfoId){
		int result=0;
		personalInfoDAO.deletePersonalInfoById(personalInfoId);
		personalWorkInfoDAO.deletePersonalWorkInfoById(personalInfoId);
		personalSalaryInfoDAO.deletePersonalSalaryInfoById(personalInfoId);
		result=1;
		return result;
	}

}
