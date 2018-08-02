package hr.manage.component.common.model;

import java.util.Date;

/**
 * common_type
 * 
 * @author 
 * @version 1.0.0 2018-07-24
 */


import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
@Table("common_type")
public class CommonType implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5031109170041760125L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 类型,1:级别；2：所在部门；3：归属中心；4：外派单位； */
    @Column(value = "type")
    private Integer type;

    /** 内容 */
    @Column(value = "content")
    private String content;

    /** 备注 */
    @Column(value = "memo")
    private String memo;

    /** 是否删除 1未删除 0已删除 */
    @Column(value = "is_del")
    private Integer isDel;

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
     * 获取类型,1:级别；2：所在部门；3：归属中心；4：外派单位；
     * 
     * @return 类型
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置类型,1:级别；2：所在部门；3：归属中心；4：外派单位；
     * 
     * @param type
     *          类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取内容
     * 
     * @return 内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置内容
     * 
     * @param content
     *          内容
     */
    public void setContent(String content) {
        this.content = content;
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