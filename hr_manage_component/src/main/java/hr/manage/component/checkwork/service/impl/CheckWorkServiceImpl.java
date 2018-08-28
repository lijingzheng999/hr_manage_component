package hr.manage.component.checkwork.service.impl;

import hr.manage.component.checkwork.dao.CheckWorkCurrentDAO;
import hr.manage.component.checkwork.dao.CheckWorkDetailDAO;
import hr.manage.component.checkwork.model.CheckWorkCurrent;
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
	@Autowired
	CheckWorkCurrentDAO checkWorkCurrentDAO;
	
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
	
	public  CheckWorkCurrent getCheckWorkCurrentByName(String name){
		return checkWorkCurrentDAO.getCheckWorkCurrentByName(name);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int saveCheckWorkDetailListRecord( List<CheckWorkDetail> checkWorkList){
		int result = 0;
		for (CheckWorkDetail checkWork : checkWorkList) {
			
			logger.info("saveCheckWorkDetailListRecord : 员工姓名：" + checkWork.getName() );
			
		    checkWorkDetailDAO.save(checkWork);

		}
		result = 1;
		return result;
	}
}
