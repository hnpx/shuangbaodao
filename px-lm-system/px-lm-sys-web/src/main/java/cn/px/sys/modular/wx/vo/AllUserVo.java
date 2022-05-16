package cn.px.sys.modular.wx.vo;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AllUserVo extends BaseModel implements Serializable {
    /**
     * 详细地址
     */
    private String address;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * openid
     */
    private String openid;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户类型（1.党员干部2.单位3.普通用户）
     */
    private Integer type;
    private Date loginTime;
    private Integer integral;
    private Integer status;
    private String name;
    private String idNumber;
    private Long belongingCommunity;
    private Long belongingHome;
    private Long belongingUnit;
    private String belongingCommunityName;
    private String belongingHomeName;
    private String belongingUnitName;
    private Integer isBind;
    private Integer merchantStatus;

    private Integer pointsConsumption;
    private Integer remainingPoints;
    private Integer integralToday;
    private Integer showIntegral;
    private Long expertise;

}
