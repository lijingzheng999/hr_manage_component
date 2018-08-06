package hr.manage.component.contract.service.impl;

import hr.manage.component.contract.dao.ContractInfoDAO;
import hr.manage.component.contract.model.ContractCondition;
import hr.manage.component.contract.model.ContractInfo;
import hr.manage.component.contract.service.ContractService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ContractServiceImpl implements ContractService {
	
	private final Log logger = LogFactory.getLog(ContractServiceImpl.class);
	@Autowired
	ContractInfoDAO contractInfoDAO;
	
	@Override
	public List<ContractInfo> listContractInfo(ContractCondition condition){
		return contractInfoDAO.listContractInfo(condition);
	}
	
	@Override
	public Long countContractInfo(ContractCondition condition){
		return contractInfoDAO.countContractInfo(condition);
	}
	
	public ContractInfo getContractInfoById(Integer contractInfoId){
		return contractInfoDAO.get(contractInfoId);
	}
	
	public int deleteContractInfoById(Integer contractInfoId){
	    return 	contractInfoDAO.deleteContractInfoById(contractInfoId);
	}
	
}
