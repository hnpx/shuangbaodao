package cn.px.sys.modular.merchant.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 商家管理(Merchant)
 *
 * @author
 * @since 2020-09-02 14:37:54
 */
@TableName("merchant")
public class MerchantEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 302684267707269048L;
    /**
     * 商家名称
     */
    @TableField("name")
    private String name;
    /**
     * 商家地址
     */
    @TableField("address")
    private String address;
    /**
     * 商家联系人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 商家联系电话
     */
    @TableField("phone")
    private String phone;
    /**
     * 商家介绍
     */
    @TableField("introduction")
    private String introduction;
    /**
     * 商家头图
     */
    @TableField("head_figure")
    private String headFigure;
    /**
     * 商家分类id
     */
    @TableField("classification_id")
    private Long classificationId;
    /**
     * 所属社区
     */
    @TableField("belonging_community")
    private Long belongingCommunity;
    /**
     * 所属小区
     */
    @TableField("belonging_home")
    private Long belongingHome;

    @TableField("exchange_method")
    private String exchangeMethod;

    @TableField("user_id")
    private Long userId;
    @TableField("status")
    private Integer status;
    @TableField("reason")
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExchangeMethod() {
        return exchangeMethod;
    }

    public void setExchangeMethod(String exchangeMethod) {
        this.exchangeMethod = exchangeMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getHeadFigure() {
        return headFigure;
    }

    public void setHeadFigure(String headFigure) {
        this.headFigure = headFigure;
    }

    public Long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Long classificationId) {
        this.classificationId = classificationId;
    }

    public Long getBelongingCommunity() {
        return belongingCommunity;
    }

    public void setBelongingCommunity(Long belongingCommunity) {
        this.belongingCommunity = belongingCommunity;
    }

    public Long getBelongingHome() {
        return belongingHome;
    }

    public void setBelongingHome(Long belongingHome) {
        this.belongingHome = belongingHome;
    }

}