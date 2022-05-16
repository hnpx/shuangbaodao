package cn.px.sys.modular.activity.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (ActivityUserSign)
 *
 * @author
 * @since 2020-08-31 11:08:19
 */
@TableName("activity_user_sign")
public class ActivityUserSignEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 618799757226429837L;
    /**
     * 活动id
     */
    @TableField("activity_id")
    private Long activityId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 参与状态（1.未参加2.已参加）
     */
    @TableField("status")
    private Integer status;


    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}