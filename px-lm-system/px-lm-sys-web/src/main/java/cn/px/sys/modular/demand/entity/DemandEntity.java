package cn.px.sys.modular.demand.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 需求管理(Demand)
 *
 * @author
 * @since 2020-09-02 19:38:23
 */
@TableName("demand")
public class DemandEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 107277060494166557L;
    /**
     * 申请人
     */
    @TableField("name")
    private String name;
    /**
     * 申请电话
     */
    @TableField("phone")
    private String phone;
    /**
     * 需求分类
     */
    @TableField("demand_class")
    private Long demandClass;
    /**
     * 需求内容
     */
    @TableField("content")
    private String content;
    /**
     * 审核状态（1.通过2.未通过3.未审核）
     */
    @TableField("status")
    private Integer status;
    /**
     * 原因
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDemandClass() {
        return demandClass;
    }

    public void setDemandClass(Long demandClass) {
        this.demandClass = demandClass;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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