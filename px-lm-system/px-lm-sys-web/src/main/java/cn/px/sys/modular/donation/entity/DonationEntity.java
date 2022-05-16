package cn.px.sys.modular.donation.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 捐赠管理(Donation)
 *
 * @author
 * @since 2020-09-02 18:02:14
 */
@TableName("donation")
public class DonationEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -28580211706442477L;
    /**
     * 捐献类型（1.捐赠物资2.捐赠现金）
     */
    @TableField("type")
    private Integer type;
    /**
     * 参与方式
     */
    @TableField("participate_way")
    private Integer participateWay;
    /**
     * 物资捐献描述
     */
    @TableField("good_description")
    private String goodDescription;
    /**
     * 现金捐赠描述
     */
    @TableField("cash_description")
    private String cashDescription;
    /**
     * 联系人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 电话
     */
    @TableField("phone")
    private String phone;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParticipateWay() {
        return participateWay;
    }

    public void setParticipateWay(Integer participateWay) {
        this.participateWay = participateWay;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public String getCashDescription() {
        return cashDescription;
    }

    public void setCashDescription(String cashDescription) {
        this.cashDescription = cashDescription;
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

}