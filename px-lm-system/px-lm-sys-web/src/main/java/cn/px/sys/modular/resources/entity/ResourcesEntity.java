package cn.px.sys.modular.resources.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源管理(Resources)
 *
 * @author
 * @since 2020-09-04 12:32:44
 */
@TableName("resources")
public class ResourcesEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -95709947184749760L;
    /**
     * 服务分类
     */
    @TableField("resources_class")
    private Long resourcesClass;
    /**
     * 资源名称
     */
    @TableField("name")
    private String name;
    /**
     * 资源内容
     */
    @TableField("content")
    private String content;
    /**
     * 可以开始时间
     */
    @TableField("start_time")
    private Date startTime;
    /**
     * 可用结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 联系人
     */
    @TableField("contact_person")
    private String contactPerson;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 详细地址
     */
    @TableField("address")
    private String address;
    /**
     * 资源图片
     */
    @TableField("img")
    private String img;
    /**
     * 状态（1.通过2.不通过）
     */
    @TableField("status")
    private Integer status;
    /**
     * 审核原因
     */
    @TableField("reason")
    private String reason;

    @TableField("cid")
    private Long cid;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getResourcesClass() {
        return resourcesClass;
    }

    public void setResourcesClass(Long resourcesClass) {
        this.resourcesClass = resourcesClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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