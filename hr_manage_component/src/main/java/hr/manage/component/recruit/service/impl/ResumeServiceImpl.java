package hr.manage.component.recruit.service.impl;

import hr.manage.component.recruit.dao.RecruitInfoDAO;
import hr.manage.component.recruit.dao.ResumeInfoDAO;
import hr.manage.component.recruit.model.RecruitInfo;
import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;
import hr.manage.component.recruit.service.ResumeService;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ResumeServiceImpl implements ResumeService {
	
	private final Log logger = LogFactory.getLog(ResumeServiceImpl.class);
	@Autowired
	RecruitInfoDAO recruitInfoDAO;
	@Autowired
	ResumeInfoDAO resumeInfoDAO;
	
	@Override
	public int addRecruitInfo(RecruitInfo recruitInfo){
		return recruitInfoDAO.save(recruitInfo);
	}
	
	@Override
	public List<RecruitInfo> listRecruitInfo(){
		return recruitInfoDAO.listRecruitInfo(null);
	}
	
	@Override
	public int deleteRecruitInfo(Integer recruitInfoId){
		return recruitInfoDAO.deleteRecruitInfo(recruitInfoId);
	}
	
	@Override
	public int updateRecruitInfo(RecruitInfo recruitInfo){
		return recruitInfoDAO.save(recruitInfo);
	}
	
	@Override
	public int addResumeInfo(ResumeInfo resumeInfo){
		return resumeInfoDAO.save(resumeInfo);
	}
	
	@Override
	public List<ResumeInfo> listResumeInfo(ResumeCondition condition){
		return resumeInfoDAO.listResumeInfo(condition);
	}
	
	@Override
	public Long countResumeInfo(ResumeCondition condition){
        return resumeInfoDAO.countResumeInfo(condition);
    }
	
	@Override
	public int deleteResumeInfo(Integer resumeInfoId){
		return resumeInfoDAO.deleteResumeInfo(resumeInfoId);
	}
	
	@Override
	public int updateResumeInfo(ResumeInfo resumeInfo){
		return resumeInfoDAO.save(resumeInfo);
	}
	
}
