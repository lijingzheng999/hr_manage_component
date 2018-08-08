package hr.manage.component.salary.service.impl;

import hr.manage.component.personal.dao.PersonalSalaryInfoDAO;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.salary.dao.SalaryChangeDAO;
import hr.manage.component.salary.model.SalaryChange;
import hr.manage.component.salary.model.SalaryChangeCondition;
import hr.manage.component.salary.service.SalaryService;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SalaryServiceImpl implements SalaryService {
	
	private final Log logger = LogFactory.getLog(SalaryServiceImpl.class);
	@Autowired
	SalaryChangeDAO salaryChangeDAO;
	@Autowired
	PersonalSalaryInfoDAO personalSalaryInfoDAO;
	
	@Override
	public List<SalaryChange> listSalaryChange(SalaryChangeCondition condition){
		return salaryChangeDAO.listSalaryChange(condition);
	}
	
	@Override
	public Long countSalaryChange(SalaryChangeCondition condition){
		return salaryChangeDAO.countSalaryChange(condition);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int addSalaryChange(SalaryChange salaryChange){
		int result =0;
		//保存工资调整信息
		result = salaryChangeDAO.save(salaryChange);
		if(result<1){
			result = -1;
		}
		//同步员工基本信息
		PersonalSalaryInfo salary = personalSalaryInfoDAO.getPersonalSalaryInfoById(salaryChange.getPersonalInfoId());
		if(salary!=null){
			//调级--修改基本工资
			Integer oldBasePay = salary.getBasePay();
			Integer oldMeritPay = salary.getMeritPay();
			if(salaryChange.getType()==1){
				salary.setBasePay(oldBasePay+salaryChange.getChangeRange());
				salary.setWorkerPay(salaryChange.getChangeRange()+salary.getWorkerPay());
			}
			else{ //调薪或其他--修改绩效工资
				salary.setMeritPay(oldMeritPay+salaryChange.getChangeRange());
				salary.setWorkerPay(salaryChange.getChangeRange()+salary.getWorkerPay());			
			}
			salary.setUpdateTime(new Date());
			boolean curResult = personalSalaryInfoDAO.update(salary);
			if(curResult){
				result = 1;
			}
			else{
				result = -3;
			}
		}
		else{
			result =-2;
		}
		return result;	
	}
	
}
