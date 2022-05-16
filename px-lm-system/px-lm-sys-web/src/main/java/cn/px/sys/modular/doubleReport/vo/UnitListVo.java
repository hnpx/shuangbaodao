package cn.px.sys.modular.doubleReport.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UnitListVo {
    private Long id;

    /**
     * 单位名称
     */
    private String name;
    /**
     * 所属社区
     */
    private Long belongingCommunity;
    /**
     * 单位地址
     */
    private String address;
    /**
     * 负责人名字
     */
    private String contactPerson;
    /**
     * 负责人电话
     */
    private String phone;
    /**
     * 单位积分
     */
    private Integer integral;

    private String belongingCommunityName;
    private Long userId;
    private Integer remainingPoints;
    private Integer showIntegral;
    private Integer type;
    private Integer isUnit;

}
