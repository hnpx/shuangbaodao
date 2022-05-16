package cn.px.sys.modular.doubleReport.vo;

import lombok.Data;

@Data
public class UserVo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     *所属社区
     */
    private Long belongingCommunity;
    /**
     * 所属小区
     */
    private Long belongingHome;
    /**
     * 所属单位
     */
    private Long belongingUnit;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 身份类型
     */
    private Integer type;

    private Long expertise;
}
