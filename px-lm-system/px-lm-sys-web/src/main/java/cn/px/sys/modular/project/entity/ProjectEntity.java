package cn.px.sys.modular.project.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
//import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目管理(Project)
 *
 * @author
 * @since 2020-09-02 20:20:20
 */
@TableName("project")
public class ProjectEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 380792766903968319L;
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
     * 项目头图
     */
    @TableField("head_figure")
    private String headFigure;
    /**
     * 项目地址
     */
    @TableField("address")
    private String address;
    /**
     * 项目开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 项目结束时间
     */
    @TableField("end_time")
    private Date endTime;
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
     * 项目积分
     */
    @TableField("integral")
    private Integer integral;
    /**
     * 参与人员类型（1.不限2.党员干部3.指定单位）
     */
    @TableField("personnel_type")
    private Integer personnelType;
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

    @TableField("max_person")
    private Integer maxPerson;
    @TableField("current_person")
    private Integer currentPerson;

    @TableField("belonging_community")
    private Long belongingCommunity;
    @TableField("belonging_unit")
    private Long belongingUnit;

    @TableField("content")
    private String content;

    @TableField("service_time")
    private Integer serviceTime;
    @TableField("certification")
    private String certification;
    @TableField("units")
    private String units;

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    @TableField(exist = false)
    private Long userId;

    @TableField(exist =  false)
    private Long SignId;
    /**
     * 双报到积分
     */
    @TableField("reportPoints")
    private Integer reportPoints;
    @TableField("status")
    private Integer status;
    /**
     * 审核不通过的原因
     */
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

    public Integer getReportPoints() {
        return reportPoints;
    }

    public void setReportPoints(Integer reportPoints) {
        this.reportPoints = reportPoints;
    }

    public Long getSignId() {
        return SignId;
    }

    public void setSignId(Long signId) {
        SignId = signId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getCurrentPerson() {
        return currentPerson;
    }

    public void setCurrentPerson(Integer currentPerson) {
        this.currentPerson = currentPerson;
    }

    public Integer getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(Integer maxPerson) {
        this.maxPerson = maxPerson;
    }

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

    public String getHeadFigure() {
        return headFigure;
    }

    public void setHeadFigure(String headFigure) {
        this.headFigure = headFigure;
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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(Integer personnelType) {
        this.personnelType = personnelType;
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

}