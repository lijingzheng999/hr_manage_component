package hr.manage.component.checkwork.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkDetail;
import hr.manage.component.checkwork.model.CheckWorkDetailCondition;
import hr.manage.component.checkwork.service.CheckWorkService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CheckWorkServiceImpl implements CheckWorkService {
	
	private final Log logger = LogFactory.getLog(CheckWorkServiceImpl.class);
	@Autowired
	CheckWorkDetailDAO checkWorkDetailDAO;
	
	@Override
	public List<CheckWorkDetail> listCheckWorkDetail(CheckWorkDetailCondition condition){
		return checkWorkDetailDAO.listCheckWorkDetail(condition);
	}
	
	@Override
	public Long countCheckWorkDetail(CheckWorkDetailCondition condition){
		return checkWorkDetailDAO.countCheckWorkDetail(condition);
	}
	
	@Override
	public CheckWorkDetail getCheckWorkDetailByName(CheckWorkDetailCondition condition){
		return checkWorkDetailDAO.getCheckWorkDetailByName(condition);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int saveCheckWorkDetailListRecord( List<CheckWorkDetail> checkWorkList){
		int result = 0;
		for (CheckWorkDetail checkWork : checkWorkList) {
			
			logger.info("saveCheckWorkDetailListRecord : 员工姓名：" + checkWork.getname() );
//
//			person.setEmployeeNumber(employeeNumber);
//			person.setIsDel(1);
//			person.setCreateTime(new Date());
//			// 保存员工个人信息表,返回主键ID
//			Integer personalInfoId = personalInfoDAO.save(person);
//			work.setPersonalInfoId(personalInfoId);
//			work.setIsDel(1);
//			work.setCreateTime(new Date());
//			Integer workId = personalWorkInfoDAO.save(work);
//			salary.setPersonalInfoId(personalInfoId);
//			salary.setIsDel(1);
//			salary.setCreateTime(new Date());
//			Integer salaryId = personalSalaryInfoDAO.save(salary);
//
//			ContractInfo contractInfo = new ContractInfo();
//			contractInfo.setPersonalInfoId(personalInfoId);
//			contractInfo.setEmployeeNumber(employeeNumber);
//			contractInfo.setName(person.getName());
//			contractInfo.setContractNumber(employeeNumber + "01"); // 合同编号=员工编号+"01"
//			contractInfo.setPosition(work.getPosition());
//			contractInfo.setStartDate(work.getContractStartdate());
//			contractInfo.setEndDate(work.getContractEnddate());
//			contractInfo.setContractCount(1); // 导入默认为第一次；
//			contractInfo.setMemo(person.getMemo());
//			contractInfo.setIsDel(1);
//			contractInfo.setCreateTime(new Date());
//			contractInfoDAO.save(contractInfo);
			logger.info("saveCheckWorkDetailListRecord : 员工姓名：" + checkWork.getname());
		}
		result = 1;
		return result;
	}
}
