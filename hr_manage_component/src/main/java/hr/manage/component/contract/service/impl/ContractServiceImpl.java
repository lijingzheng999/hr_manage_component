package hr.manage.component.contract.service.impl;

import hr.manage.component.contract.dao.ContractInfoDAO;
import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.contract.service.ContractService;
import hr.manage.component.personal.dao.PersonalWorkInfoDAO;
import hr.manage.component.personal.model.PersonalWorkInfo;

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
	public int deleteContractInfoById(Integer contractInfoId){
	    return 	contractInfoDAO.deleteContractInfoById(contractInfoId);
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
	public int addContractInfo(ContractInfo contractInfo){
		int result =0;
		//保存合同信息
		result = contractInfoDAO.save(contractInfo);
		if(result<1){
			result = -1;
		}
		//同步员工基本信息
		PersonalWorkInfo work = personalWorkInfoDAO.getPersonalWorkInfoById(contractInfo.getPersonalInfoId());
		if(work!=null){
			work.setContractCount(contractInfo.getContractCount());
			work.setContractRenewDate(contractInfo.getStartDate());
			work.setContractRenewEnddate(contractInfo.getEndDate());
			work.setUpdateTime(new Date());
			boolean curResult = personalWorkInfoDAO.update(work);
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
