package cn.px.sys.modular.activity.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动管理(Activity)
 *
 * @author
 * @since 2020-08-27 15:10:06
 */
@TableName("activity")
public class ActivityEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -83562950698234494L;
    /**
     * 活动名称
     */
    @TableField("name")
    private String name;
    /**
     * 活动地址
     */
    @TableField("address")
    private String address;
    /**
     * 活动开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 活动结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 活动负责人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 活动负责人电话
     */
    @TableField("phone")
    private String phone;
    /**
     * 活动简介
     */
    @TableField("introduction")
    private String introduction;
    /**
     * 活动积分
     */
    @TableField("integral")
    private Integer integral;
    /**
     * 是否需要签到（1.是2.否）
     */
    @TableField("sign_in")
    private Integer signIn;
    /**
     * 签到二维码
     */
    @TableField("sign_qr")
    private String signQr;
    /**
     * 签退二维码
     */
    @TableField("signout_qr")
    private String signoutQr;
    /**
     * 活动审核状态（1.通过2.不通过）
     */
    @TableField("status")
    private Integer status;
    /**
     * 审核原因
     */
    @TableField("reason")
    private String reason;
    /**
     * 参与人类型
     */
    @TableField("user_type")
    private Integer userType;

    @TableField("headFigure")
    private String headFigure;
    @TableField("cid")
    private Long cid;

    @TableField("max_person")
    private Integer maxPerson;

    @TableField("current_person")
    private Integer currentPerson;
    @TableField("content")
    private String content;
    @TableField("service_time")
    private Integer serviceTime;
    @TableField("certification")
    private String certification;

    /**
     * 所属社区
     *
     */
    @TableField("belonging_community")
    private Long belongingCommunity;
    /**
     * 所属单位
     *
     */
    @TableField("belonging_unit")
    private Long belongingUnit;

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

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Integer serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
    }

    public Integer getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Integer currentPerson) {
        this.currentPerson = currentPerson;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getHeadFigure() {
        return headFigure;
    }

    public void setHeadFigure(String headFigure) {
        this.headFigure = headFigure;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getSignIn() {
        return signIn;
    }

    public void setSignIn(Integer signIn) {
        this.signIn = signIn;
    }

    public String getSignQr() {
        return signQr;
    }

    public void setSignQr(String signQr) {
        this.signQr = signQr;
    }

    public String getSignoutQr() {
        return signoutQr;
    }

    public void setSignoutQr(String signoutQr) {
        this.signoutQr = signoutQr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}