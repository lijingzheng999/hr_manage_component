package hr.manage.component.recruit.service.impl;

import hr.manage.component.common.constants.DepartmentConstants;
import hr.manage.component.common.constants.UnitConstants;
import hr.manage.component.personal.model.PersonalInfo;
import hr.manage.component.personal.model.PersonalSalaryInfo;
import hr.manage.component.personal.model.PersonalWorkInfo;
import hr.manage.component.recruit.dao.RecruitInfoDAO;
import hr.manage.component.recruit.dao.ResumeInfoDAO;
import hr.manage.component.recruit.dao.ResumeInterviewDAO;
import hr.manage.component.recruit.model.RecruitInfo;
import hr.manage.component.recruit.model.ResumeCondition;
import hr.manage.component.recruit.model.ResumeInfo;
import hr.manage.component.recruit.model.ResumeInterview;
import hr.manage.component.recruit.model.ResumeInterviewCondition;
import hr.manage.component.recruit.service.ResumeService;

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
public class ResumeServiceImpl implements ResumeService {
	
	private final Log logger = LogFactory.getLog(ResumeServiceImpl.class);
	@Autowired
	RecruitInfoDAO recruitInfoDAO;
	@Autowired
	ResumeInfoDAO resumeInfoDAO;
	@Autowired
	ResumeInterviewDAO resumeInterviewDAO;
	
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
	public int updateStatusComplete(Integer recruitInfoId){
		return recruitInfoDAO.updateStatusComplete(recruitInfoId);
	}
	
	
	@Override
	public boolean updateRecruitInfo(RecruitInfo recruitInfo){
		return recruitInfoDAO.update(recruitInfo);
	}
	@Override
	public RecruitInfo getRecruitInfo(Integer recruitInfoId){
		return recruitInfoDAO.get(recruitInfoId);
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
	public ResumeInfo getResumeInfo(Integer resumeInfoId){
		return resumeInfoDAO.get(resumeInfoId);
	}
	
	@Override
	public boolean updateResumeInfo(ResumeInfo resumeInfo){
		return resumeInfoDAO.update(resumeInfo);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int updateResumeInfoPass(ResumeInfo resumeInfo){
		int result=0;
		if(resumeInfoDAO.update(resumeInfo)){
			result=1;
		}
		//自动生成沟通表数据
		if(result>0){
			ResumeInterview interview = new ResumeInterview();
			interview.setResumeId(resumeInfo.getId());
			interview.setName(resumeInfo.getName());
			interview.setSex(resumeInfo.getSex());
			interview.setInterviewTime(resumeInfo.getInterviewTime());
			interview.setPosition(resumeInfo.getPosition());
			interview.setPhone(resumeInfo.getPhone());
			interview.setEmail(resumeInfo.getEmail());
			interview.setStatus(1);//待入职
			interview.setCreateTime(new Date());
			result = resumeInterviewDAO.save(interview);
		}
		return result;
	}
	
	@Override
	public int addResumeInterview(ResumeInterview resumeInterview){
		return resumeInterviewDAO.save(resumeInterview);
	}
	
	@Override
	public List<ResumeInterview> listResumeInterview(ResumeInterviewCondition condition){
		return resumeInterviewDAO.listResumeInterview(condition);
	}
	
	@Override
	public Long countResumeInterview(ResumeInterviewCondition condition){
        return resumeInterviewDAO.countResumeInterview(condition);
    }
	
	@Override
	public int deleteResumeInterview(Integer resumeInterviewId){
		return resumeInterviewDAO.deleteResumeInterview(resumeInterviewId);
	}
	
	@Override
	public ResumeInterview getResumeInterview(Integer resumeInterviewId){
		return resumeInterviewDAO.get(resumeInterviewId);
	}
	
	@Override
	public boolean updateResumeInterview(ResumeInterview resumeInterview){
		return resumeInterviewDAO.update(resumeInterview);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public synchronized int updateResumeInterviewEntry(ResumeInterview resumeInterview){
		int result=0;
		if(resumeInterviewDAO.update(resumeInterview)){
			result=1;
		}
		//自动生成基本表数据
		if(result>0){
//			PersonalInfo person = new PersonalInfo();
//			PersonalWorkInfo work = new PersonalWorkInfo();
//			PersonalSalaryInfo salary = new PersonalSalaryInfo();
//			// 补全默认值
//			// 生成员工编号 项目+部门+自增编号
//			String unitCode = UnitConstants.seletCode(work.getExpatriateUnit());// 外派简称
//			String departCode = DepartmentConstants.seletCode(work.getDepartment());// 部门简称
//			int personNum = personalInfoDAO.countPersonalInfo();
//			String strPersonNum = new DecimalFormat("0000").format(personNum + 1);
//			String employeeNumber = unitCode + departCode + strPersonNum;
//			logger.info("savePersonalAllListRecord : 员工编号：" + employeeNumber);
//
//			person.setEmployeeNumber(employeeNumber);
//			person.setIsDel(1);
//			person.setCreateTime(new Date());
//			
//			ResumeInterview interview = new ResumeInterview();
//			interview.setResumeId(resumeInfo.getId());
//			interview.setName(resumeInfo.getName());
//			interview.setSex(resumeInfo.getSex());
//			interview.setInterviewTime(resumeInfo.getInterviewTime());
//			interview.setPosition(resumeInfo.getPosition());
//			interview.setPhone(resumeInfo.getPhone());
//			interview.setEmail(resumeInfo.getEmail());
//			interview.setStatus(1);//待入职
//			interview.setCreateTime(new Date());
//			result = resumeInterviewDAO.save(interview);
		}
		return result;
	}
	
}
