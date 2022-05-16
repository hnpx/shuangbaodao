package cn.px.sys.modular.resources.vo;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResourcesManVo extends BaseModel implements Serializable {
    private Long resourcesClass;
    /**
     * 资源名称
     */

    private String name;
    /**
     * 资源内容
     */

    private String content;
    /**
     * 可以开始时间
     */

    private Date startTime;
    /**
     * 可用结束时间
     */

    private Date endTime;
    /**
     * 联系人
     */

    private String contactPerson;
    /**
     * 手机号
     */

    private String phone;
    /**
     * 详细地址
     */

    private String address;
    /**
     * 资源图片
     */

    private String img;
    /**
     * 状态（1.通过2.不通过）
     */

    private Integer status;
    /**
     * 审核原因
     */
    private String reason;

    private String resourcesClassName;
}
