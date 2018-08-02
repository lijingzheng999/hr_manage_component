package hr.manage.component.admin.model;

import java.io.Serializable;

import net.paoding.rose.jade.plugin.sql.annotations.Column;
import net.paoding.rose.jade.plugin.sql.annotations.Table;
@Table("admin_function")
public class AdminFunction  implements Serializable{
	  /** 版本号 */
    private static final long serialVersionUID = -6600456208412548561L;

    /** 表的主键 */
    @Column(pk=true,value = "id")
    private Integer id;

    /** 权限名称 */
    @Column(value = "name")
    private String name;

    /** 描述 */
    @Column(value = "description")
    private String description;

    /** 0:不显示;1:显示 */
    @Column(value = "isMenu")
    private Byte[] isMenu;

    /** 父ID */
    @Column(value = "pid")
    private Integer pid;

    /** 是否权限组:0 false;1 true */
    @Column(value = "isGroup")
    private Byte[] isGroup;

    /** 排序 */
    @Column(value = "listOrder")
    private Integer listOrder;

    /** 是否删除 1：在用  0：已删除 */
    @Column(value = "isDel")
    private Integer isDel;

    /** 层级 */
    @Column(value = "level")
    private Integer level;

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
     * 获取权限名称
     * 
     * @return 权限名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置权限名称
     * 
     * @param name
     *          权限名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置描述
     * 
     * @param description
     *          描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取0:不显示;1:显示
     * 
     * @return 0:不显示;1:显示
     */
    public Byte[] getIsMenu() {
        return this.isMenu;
    }

    /**
     * 设置0:不显示;1:显示
     * 
     * @param isMenu
     *          0:不显示;1:显示
     */
    public void setIsMenu(Byte[] isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * 获取父ID
     * 
     * @return 父ID
     */
    public Integer getPid() {
        return this.pid;
    }

    /**
     * 设置父ID
     * 
     * @param pid
     *          父ID
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取是否权限组:0 false;1 true
     * 
     * @return 是否权限组
     */
    public Byte[] getIsGroup() {
        return this.isGroup;
    }

    /**
     * 设置是否权限组:0 false;1 true
     * 
     * @param isGroup
     *          是否权限组
     */
    public void setIsGroup(Byte[] isGroup) {
        this.isGroup = isGroup;
    }

    /**
     * 获取排序
     * 
     * @return 排序
     */
    public Integer getListOrder() {
        return this.listOrder;
    }

    /**
     * 设置排序
     * 
     * @param listOrder
     *          排序
     */
    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    /**
     * 获取是否删除 1：在用  0：已删除
     * 
     * @return 是否删除 1
     */
    public Integer getIsDel() {
        return this.isDel;
    }

    /**
     * 设置是否删除 1：在用  0：已删除
     * 
     * @param isDel
     *          是否删除 1
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取层级
     * 
     * @return 层级
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 设置层级
     * 
     * @param level
     *          层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }
}