package hr.manage.component.contract.model;
import java.util.Date;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;

/**
 * contract_info
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */
@Table(value = "contract_info")
public class ContractInfo implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4947481315976953271L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 员工编号 项目+部门+自增编号 */
    @Column(value = "employee_number")
    private String employeeNumber;

    /** 姓名 */
    @Column(value = "name")
    private String name;

    /** 合同编号 */
    @Column(value = "contract_number")
    private String contractNumber;

    /** 岗位(职位) */
    @Column(value = "position")
    private String position;

    /** 合同开始日期 */
    @Column(value = "start_date")
    private Date startDate;

    /** 合同截止日期 */
    @Column(value = "end_date")
    private Date endDate;

    /** 合同签署次数(第几次) */
    @Column(value = "contract_count")
    private Integer contractCount;

    /** 备注 */
    @Column(value = "memo")
    private String memo;

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
     * 获取员工编号 项目+部门+自增编号
     * 
     * @return 员工编号 项目+部门+自增编号
     */
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    /**
     * 设置员工编号 项目+部门+自增编号
     * 
     * @param employeeNumber
     *          员工编号 项目+部门+自增编号
     */
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    /**
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置姓名
     * 
     * @param name
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取合同编号
     * 
     * @return 合同编号
     */
    public String getContractNumber() {
        return this.contractNumber;
    }

    /**
     * 设置合同编号
     * 
     * @param contractNumber
     *          合同编号
     */
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    /**
     * 获取岗位(职位)
     * 
     * @return 岗位(职位)
     */
    public String getPosition() {
        return this.position;
    }

    /**
     * 设置岗位(职位)
     * 
     * @param position
     *          岗位(职位)
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * 获取合同开始日期
     * 
     * @return 合同开始日期
     */
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * 设置合同开始日期
     * 
     * @param startDate
     *          合同开始日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取合同截止日期
     * 
     * @return 合同截止日期
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * 设置合同截止日期
     * 
     * @param endDate
     *          合同截止日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取合同签署次数(第几次)
     * 
     * @return 合同签署次数(第几次)
     */
    public Integer getContractCount() {
        return this.contractCount;
    }

    /**
     * 设置合同签署次数(第几次)
     * 
     * @param contractCount
     *          合同签署次数(第几次)
     */
    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getMemo() {
        return this.memo;
    }

    /**
     * 设置备注
     * 
     * @param memo
     *          备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
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