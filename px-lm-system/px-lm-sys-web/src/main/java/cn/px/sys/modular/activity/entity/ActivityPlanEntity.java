package cn.px.sys.modular.activity.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ActivityPlan)
 *
 * @author
 * @since 2020-09-28 09:16:43
 */
@TableName("activity_plan")
public class ActivityPlanEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = -99499558354460125L;
    /**
     * 服务时限
     */
    @TableField("service_time")
    private String serviceTime;
    /**
     * 内容
     */
    @TableField("service_content")
    private String serviceContent;
    /**
     * 备注
     */
    @TableField("service_desc")
    private String serviceDesc;
    /**
     * 预留字段
     */
    @TableField("servie_reserved")
    private String servieReserved;


    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServieReserved() {
        return servieReserved;
    }

    public void setServieReserved(String servieReserved) {
        this.servieReserved = servieReserved;
    }

}