package cn.px.sys.modular.virtualProject.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * (VirtualProject)
 *
 * @author
 * @since 2020-12-16 09:38:46
 */
@TableName("virtual_project")
public class VirtualProjectEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 164846708555625381L;
    /**
     * 项目名称
     */
    @TableField("name")
    private String name;
    /**
     * 项目分类
     */
    @TableField("company_class")
    private Long companyClass;
    /**
     * 项目地址
     */
    @TableField("address")
    private String address;
    /**
     * 年份
     */
    @TableField("time")
    private String time;
    /**
     * 项目负责人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 项目负责人电话
     */
    @TableField("phone")
    private String phone;
    /**
     * 所属社区
     */
    @TableField("belonging_community")
    private Long belongingCommunity;
    /**
     * 所属单位
     */
    @TableField("belonging_unit")
    private Long belongingUnit;
    /**
     * 活动审核状态（1.未完成2.已完成）
     */
    @TableField("status")
    private Integer status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyClass() {
        return companyClass;
    }

    public void setCompanyClass(Long companyClass) {
        this.companyClass = companyClass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getBelongingCommunity() {
        return belongingCommunity;
    }

    public void setBelongingCommunity(Long belongingCommunity) {
        this.belongingCommunity = belongingCommunity;
    }

    public Long getBelongingUnit() {
        return belongingUnit;
    }

    public void setBelongingUnit(Long belongingUnit) {
        this.belongingUnit = belongingUnit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}