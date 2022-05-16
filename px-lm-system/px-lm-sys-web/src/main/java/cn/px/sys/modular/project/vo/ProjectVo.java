package cn.px.sys.modular.project.vo;

import cn.px.base.core.BaseModel;
import cn.px.sys.modular.activity.vo.CommentVo;
import cn.px.sys.modular.activity.vo.UserVo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProjectVo extends BaseModel implements Serializable {
    /**
     * 项目名称
     */

    private String name;
    /**
     * 项目分类
     */

    private Long companyClass;
    /**
     * 项目头图
     */

    private String headFigure;
    /**
     * 项目地址
     */

    private String address;
    /**
     * 项目开始时间
     */

    private Date startTime;
    /**
     * 项目结束时间
     */

    private Date endTime;
    /**
     * 项目负责人
     */

    private String contactPerson;
    /**
     * 项目负责人电话
     */

    private String phone;
    /**
     * 项目积分
     */

    private Integer integral;
    /**
     * 参与人员类型（1.不限2.党员干部3.指定单位）
     */

    private Integer personnelType;
    /**
     * 是否需要签到（1.是2.否）
     */

    private Integer signIn;

    private String companyClassName;

    private Integer maxPerson;
    /**
     * 项目时间状态（1.未开始2.进行中4.已认领）
     */
    private Integer timeStatus;

    /**
     * 是否报名（1.已报名2.未报名）
     */
    private Integer isSign;

    private List<UserVo> userVoList;
    private List<CommentVo> commentVoList;
    private Integer signInOrOut;

    private Long belongingUnit;
    private String content;
    private Integer serviceTime;
    private String unitName;
    private String certification;
    private Integer status;
    /**
     * 是否可以认证(1.可以认证2.不可以认证)
     */
    private Integer isCertification;

}
