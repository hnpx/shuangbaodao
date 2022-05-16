package cn.px.sys.modular.integral.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 积分管理(IntegralRecord)
 *
 * @author
 * @since 2020-09-07 09:36:11
 */
@TableName("integral_record")
public class IntegralRecordEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 112223642036968361L;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 积分数
     */
    @TableField("integral")
    private Integer integral;
    /**
     * 积分来源
     */
    @TableField("source")
    private String source;

    @TableField("type")
    private Integer type;
    @TableField("mid")
    private Long mid;

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}