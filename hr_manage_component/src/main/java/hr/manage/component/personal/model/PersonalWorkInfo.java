package hr.manage.component.personal.model;

import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * personal_work_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */

@Table(value = "personal_work_info")
public class PersonalWorkInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1622281879458457592L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 员工信息表ID */
    @Column(value = "personal_info_id")
    private Integer personalInfoId;

    /** 工作地点  -员工可修改*/
    @Column(value = "working_place")
    private String workingPlace;

    /** 岗位类别  -员工可修改*/
    @Column(value = "post_type")
    private String postType;

    /** 职位 */
    @Column(value = "position")
    private String position;

    /** 级别 */
    @Column(value = "level")
    private String level;

    /** 部门  -员工可修改*/
    @Column(value = "department")
    private String department;

    /** 中心  -员工可修改*/
    @Column(value = "center")
    private String center;

    /** 项目  -员工可修改*/
    @Column(value = "project")
    private String project;

    /** 全通负责人  -员工可修改*/
    @Column(value = "expatriate_manager")
    private String expatriateManager;

    /** 外派单位 */
    @Column(value = "expatriate_unit")
    private String expatriateUnit;

    /** 招聘渠道 */
    @Column(value = "recruit_channel")
    private String recruitChannel;

    /** 工作地址  -员工可修改*/
    @Column(value = "working_address")
    private String workingAddress;

    /** 合同签署次数 */
    @Column(value = "contract_number")
    private Integer contractNumber;

    /** 合同生效日期 ;签订日期*/
    @Column(value = "contract_startdate")
    private Date contractStartdate;

    /** 合同失效日期 */
    @Column(value = "contract_enddate")
    private Date contractEnddate;

    /** 续签合同日期 */
    @Column(value = "contract_renew_date")
    private Date contractRenewDate;

    /** 续签合同失效日期 */
    @Column(value = "contract_renew_enddate")
    private Date contractRenewEnddate;

    /** 是否撤离  */
    @Column(value = "is_leave")
    private String isLeave;

    /** 是否离职  */
    @Column(value = "leave_working")
    private String leaveWorking;

    /** 离职日期  */
    @Column(value = "leave_working_time")
    private Date leaveWorkingTime;

    /** 是否删除 1未删除 0已删除 */
    @Column(value = "is_del")
    private Integer isDel;

    /** 编辑时间 */
    @Column(value = "update_time")
    private Date updateTime;

    /** 创建时间 */
    @Column(value = "create_time")
    private Date createTime;

    /**
     * 获取表的主键
     * 
     * @return 表的主键
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置表的主键
     * 
     * @param id
     *          表的主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取员工信息表ID
     * 
     * @return 员工信息表ID
     */
    public Integer getPersonalInfoId() {
        return this.personalInfoId;
    }

    /**
     * 设置员工信息表ID
     * 
     * @param personalInfoId
     *          员工信息表ID
     */
    public void setPersonalInfoId(Integer personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    /**
     * 获取工作地点
     * 
     * @return 工作地点
     */
    public String getWorkingPlace() {
        return this.workingPlace;
    }

    /**
     * 设置工作地点
     * 
     * @param workingPlace
     *          工作地点
     */
    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }

    /**
     * 获取岗位类别
     * 
     * @return 岗位类别
     */
    public String getPostType() {
        return this.postType;
    }

    /**
     * 设置岗位类别
     * 
     * @param postType
     *          岗位类别
     */
    public void setPostType(String postType) {
        this.postType = postType;
    }

    /**
     * 获取职位
     * 
     * @return 职位
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * 设置职位
     * 
     * @param position
     *          职位
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取级别
     * 
     * @return 级别
     */
    public String getLevel() {
        return this.level;
    }

    /**
     * 设置级别
     * 
     * @param level
     *          级别
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取部门
     * 
     * @return 部门
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * 设置部门
     * 
     * @param department
     *          部门
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * 获取中心
     * 
     * @return 中心
     */
    public String getCenter() {
        return this.center;
    }

    /**
     * 设置中心
     * 
     * @param center
     *          中心
     */
    public void setCenter(String center) {
        this.center = center;
    }

    /**
     * 获取项目
     * 
     * @return 项目
     */
    public String getProject() {
        return this.project;
    }

    /**
     * 设置项目
     * 
     * @param project
     *          项目
     */
    public void setProject(String project) {
        this.project = project;
    }

    /**
     * 获取全通负责人
     * 
     * @return 全通负责人
     */
    public String getExpatriateManager() {
        return this.expatriateManager;
    }

    /**
     * 设置全通负责人
     * 
     * @param expatriateManager
     *          全通负责人
     */
    public void setExpatriateManager(String expatriateManager) {
        this.expatriateManager = expatriateManager;
    }

    /**
     * 获取外派单位
     * 
     * @return 外派单位
     */
    public String getExpatriateUnit() {
        return this.expatriateUnit;
    }

    /**
     * 设置外派单位
     * 
     * @param expatriateUnit
     *          外派单位
     */
    public void setExpatriateUnit(String expatriateUnit) {
        this.expatriateUnit = expatriateUnit;
    }

    /**
     * 获取招聘渠道
     * 
     * @return 招聘渠道
     */
    public String getRecruitChannel() {
        return this.recruitChannel;
    }

    /**
     * 设置招聘渠道
     * 
     * @param recruitChannel
     *          招聘渠道
     */
    public void setRecruitChannel(String recruitChannel) {
        this.recruitChannel = recruitChannel;
    }

    /**
     * 获取工作地址
     * 
     * @return 工作地址
     */
    public String getWorkingAddress() {
        return this.workingAddress;
    }

    /**
     * 设置工作地址
     * 
     * @param workingAddress
     *          工作地址
     */
    public void setWorkingAddress(String workingAddress) {
        this.workingAddress = workingAddress;
    }

    /**
     * 获取合同签署次数
     * 
     * @return 合同签署次数
     */
    public Integer getContractNumber() {
        return this.contractNumber;
    }

    /**
     * 设置合同签署次数
     * 
     * @param contractNumber
     *          合同签署次数
     */
    public void setContractNumber(Integer contractNumber) {
        this.contractNumber = contractNumber;
    }

    

    /**
     * 获取合同生效日期
     * 
     * @return 合同生效日期
     */
    public Date getContractStartdate() {
        return this.contractStartdate;
    }

    /**
     * 设置合同生效日期
     * 
     * @param contractStartdate
     *          合同生效日期
     */
    public void setContractStartdate(Date contractStartdate) {
        this.contractStartdate = contractStartdate;
    }

    /**
     * 获取合同失效日期
     * 
     * @return 合同失效日期
     */
    public Date getContractEnddate() {
        return this.contractEnddate;
    }

    /**
     * 设置合同失效日期
     * 
     * @param contractEnddate
     *          合同失效日期
     */
    public void setContractEnddate(Date contractEnddate) {
        this.contractEnddate = contractEnddate;
    }

    /**
     * 获取续签合同日期
     * 
     * @return 续签合同日期
     */
    public Date getContractRenewDate() {
        return this.contractRenewDate;
    }

    /**
     * 设置续签合同日期
     * 
     * @param contractRenewDate
     *          续签合同日期
     */
    public void setContractRenewDate(Date contractRenewDate) {
        this.contractRenewDate = contractRenewDate;
    }

    /**
     * 获取续签合同失效日期
     * 
     * @return 续签合同失效日期
     */
    public Date getContractRenewEnddate() {
        return this.contractRenewEnddate;
    }

    /**
     * 设置续签合同失效日期
     * 
     * @param contractRenewEnddate
     *          续签合同失效日期
     */
    public void setContractRenewEnddate(Date contractRenewEnddate) {
        this.contractRenewEnddate = contractRenewEnddate;
    }

    /**
     * 获取是否撤离
     * 
     * @return 是否撤离
     */
    public String getIsLeave() {
        return this.isLeave;
    }

    /**
     * 设置是否撤离
     * 
     * @param leave
     *          是否撤离
     */
    public void setIsLeave(String isLeave) {
        this.isLeave = isLeave;
    }

    /**
     * 获取是否离职
     * 
     * @return 是否离职
     */
    public String getLeaveWorking() {
        return this.leaveWorking;
    }

    /**
     * 设置是否离职
     * 
     * @param leaveWorking
     *          是否离职
     */
    public void setLeaveWorking(String leaveWorking) {
        this.leaveWorking = leaveWorking;
    }

    /**
     * 获取离职原因
     * 
     * @return 离职原因
     */
    public Date getLeaveWorkingTime() {
        return this.leaveWorkingTime;
    }

    /**
     * 设置离职原因
     * 
     * @param leaveWorkingReasons
     *          离职原因
     */
    public void setLeaveWorkingTime(Date leaveWorkingTime) {
        this.leaveWorkingTime = leaveWorkingTime;
    }

    /**
     * 获取是否删除 1未删除 0已删除
     * 
     * @return 是否删除 1未删除 0已删除
     */
    public Integer getIsDel() {
        return this.isDel;
    }

    /**
     * 设置是否删除 1未删除 0已删除
     * 
     * @param isDel
     *          是否删除 1未删除 0已删除
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取编辑时间
     * 
     * @return 编辑时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置编辑时间
     * 
     * @param updateTime
     *          编辑时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}