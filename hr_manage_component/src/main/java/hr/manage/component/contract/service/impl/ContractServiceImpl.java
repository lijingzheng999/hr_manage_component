package hr.manage.component.contract.service.impl;

import hr.manage.component.contract.dao.ContractInfoDAO;
import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.contract.service.ContractService;
import hr.manage.component.personal.dao.PersonalWorkInfoDAO;
import hr.manage.component.personal.model.PersonalWorkInfo;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ContractServiceImpl implements ContractService {
	
	private final Log logger = LogFactory.getLog(ContractServiceImpl.class);
	@Autowired
	ContractInfoDAO contractInfoDAO;
	@Autowired
	PersonalWorkInfoDAO personalWorkInfoDAO;
	
	@Override
	public List<ContractInfo> listContractInfo(ContractCondition condition){
		return contractInfoDAO.listContractInfo(condition);
	}
	
	@Override
	public Long countContractInfo(ContractCondition condition){
		return contractInfoDAO.countContractInfo(condition);
	}
	@Override
	public ContractInfo getContractInfoById(Integer contractInfoId){
		return contractInfoDAO.get(contractInfoId);
	}
	@Override
	public ContractInfo getCurContractInfoByPersonId(Integer personalId){
		return contractInfoDAO.getCurContractInfoByPersonId(personalId);
	}
	@Override
	public int deleteContractInfoById(Integer contractInfoId){
	    return 	contractInfoDAO.deleteContractInfoById(contractInfoId);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int addContractInfo(ContractInfo contractInfo){
		int result =0;
		//验证修改的合同签订次数是否递增；
		int curContractCount  = 0;
		ContractInfo lastContract  = contractInfoDAO.getCurContractInfoByPersonId(contractInfo.getPersonalInfoId());
		if(lastContract!=null){
			curContractCount = lastContract.getContractCount()+1;
		}
		else{
			curContractCount=1;
		}
		contractInfo.setContractCount(curContractCount);
		contractInfo.setStatus(1);

		String strContractCount = new DecimalFormat("00").format(contractInfo.getContractCount());
		contractInfo.setContractNumber(contractInfo.getEmployeeNumber()+strContractCount);
					
		//同步员工基本信息
		PersonalWorkInfo work = personalWorkInfoDAO.getPersonalWorkInfoById(contractInfo.getPersonalInfoId());
		if(work==null){
			return -2;
		}
		else{
			//已离职员工不能新增合同
			if(work.getLeaveStatus().equals(0)){
				return -4;
			}
			work.setContractCount(contractInfo.getContractCount());
			if(curContractCount==2){
				work.setContractRenewDate(contractInfo.getStartDate());
				work.setContractRenewEnddate(contractInfo.getEndDate());
			}
			work.setUpdateTime(new Date());
			boolean curResult = personalWorkInfoDAO.update(work);
			if(curResult){
				result = 1;
			}
			else{
				result = -3;
			}
		}
		//上一次合同修改为历史合同
		lastContract.setStatus(0);
		lastContract.setUpdateTime(new Date());
		contractInfoDAO.update(lastContract);
		//保存合同信息
		contractInfo.setPosition(work.getPosition());
		contractInfo.setIsDel(1);
		contractInfo.setCreateTime(new Date());
		result = contractInfoDAO.save(contractInfo);
		if(result<1){
			result = -1;
		}
		return result;	
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int updateContractInfo(ContractInfo contractInfo){
		int result =0;
		//保存合同信息
		boolean curResult = contractInfoDAO.update(contractInfo);
		if(!curResult){
			result = -1;
		}
		//同步员工基本信息
		PersonalWorkInfo work = personalWorkInfoDAO.getPersonalWorkInfoById(contractInfo.getPersonalInfoId());
		if(work!=null){
			if(contractInfo.getContractCount()==1){
				work.setContractStartdate(contractInfo.getStartDate());
				work.setContractEnddate(contractInfo.getEndDate());
			}
			else{
				work.setContractRenewDate(contractInfo.getStartDate());
				work.setContractRenewEnddate(contractInfo.getEndDate());
			}
			work.setContractCount(contractInfo.getContractCount());
			work.setUpdateTime(new Date());
			boolean curWorkResult = personalWorkInfoDAO.update(work);
			if(curWorkResult){
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
	@Override
	public int getMaxContractCountById(Integer personalInfoId){
		return contractInfoDAO.getMaxContractCountById(personalInfoId);
	}
	@Override
	public int countContractInfoByPersonalId(Integer personalInfoId){
		return contractInfoDAO.countContractInfoByPersonalId(personalInfoId);
	}
}
