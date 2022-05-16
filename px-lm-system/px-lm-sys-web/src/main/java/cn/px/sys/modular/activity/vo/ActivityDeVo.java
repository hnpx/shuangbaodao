package cn.px.sys.modular.activity.vo;

import cn.px.base.core.BaseModel;
import cn.px.sys.modular.activity.entity.ActiveCommentEntity;
import cn.px.sys.modular.activity.entity.ActivityUserSignEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ActivityDeVo extends BaseModel implements Serializable {
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动地址
     */
    private String address;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 活动负责人
     */
    private String contactPerson;
    /**
     * 活动负责人电话
     */
    private String phone;
    /**
     * 活动简介
     */
    private String introduction;
    /**
     * 活动积分
     */
    private Integer integral;
    /**
     * 是否需要签到（1.是2.否）
     */
    private Integer signIn;
    /**
     * 签到二维码
     */
    private String signQr;
    /**
     * 签退二维码
     */
    private String signoutQr;
    /**
     * 活动审核状态（1.通过2.不通过）
     */
    private Integer status;
    /**
     * 审核原因
     */
    private String reason;
    private Integer userType;
    private String headFigure;

    private Integer timeStatus;
    private List<CommentVo> commentVoList;
    private List<UserVo> userVoList;
    /**
     * 是否报名（1.已报名2.未报名）
     */
    private Integer isSign;
    private Integer maxPerson;

    private Integer signInOrOut;

    private Integer serviceTime;
    private String certification;
}
