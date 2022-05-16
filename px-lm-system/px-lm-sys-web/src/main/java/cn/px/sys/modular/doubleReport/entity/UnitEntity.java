package cn.px.sys.modular.doubleReport.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 单位管理(Unit)
 *
 * @author
 * @since 2020-08-28 16:16:46
 */
@TableName("unit")
public class UnitEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 668114760709079631L;
    /**
     * 单位名称
     */
    @TableField("name")
    private String name;
    /**
     * 所属社区
     */
    @TableField("belonging_community")
    private Long belongingCommunity;
    /**
     * 单位地址
     */
    @TableField("address")
    private String address;
    /**
     * 负责人名字
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 负责人电话
     */
    @TableField("phone")
    private String phone;
    /**
     * 单位积分
     */
    @TableField("integral")
    private Integer integral;
    @TableField("user_id")
    private Long userId;
    @TableField("is_unit")
    private Integer isUnit;

    public Integer getIsUnit() {
        return isUnit;
    }

    public void setIsUnit(Integer isUnit) {
        this.isUnit = isUnit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBelongingCommunity() {
        return belongingCommunity;
    }

    public void setBelongingCommunity(Long belongingCommunity) {
        this.belongingCommunity = belongingCommunity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

}