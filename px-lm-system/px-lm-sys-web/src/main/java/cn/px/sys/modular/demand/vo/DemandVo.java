package cn.px.sys.modular.demand.vo;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
@Data
public class DemandVo extends BaseModel implements Serializable {
    /**
     * 申请人
     */
    private String name;
    /**
     * 申请电话
     */
    private String phone;
    /**
     * 需求分类
     */
    private Long demandClass;
    /**
     * 需求内容
     */
    private String content;
    /**
     * 审核状态（1.通过2.未通过3.未审核）
     */
    private Integer status;
    /**
     * 原因
     */
    private String reason;
    private String demandClassName;

}
