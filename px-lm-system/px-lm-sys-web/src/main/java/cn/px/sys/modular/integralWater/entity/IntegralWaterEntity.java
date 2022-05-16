package cn.px.sys.modular.integralWater.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (IntegralWater)
 *
 * @author
 * @since 2020-09-28 11:14:19
 */
@TableName("integral_water")
public class IntegralWaterEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 561767249500407621L;
    /**
     * 加积分的用户
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 积分
     */
    @TableField("Integral")
    private Double integral;
    /**
     * 积分说明
     */
    @TableField("Integral_desc")
    private String integralDesc;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getIntegral() {
        return integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getIntegralDesc() {
        return integralDesc;
    }

    public void setIntegralDesc(String integralDesc) {
        this.integralDesc = integralDesc;
    }

}