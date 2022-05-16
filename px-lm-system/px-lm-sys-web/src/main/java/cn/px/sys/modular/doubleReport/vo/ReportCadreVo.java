package cn.px.sys.modular.doubleReport.vo;

import lombok.Data;

@Data
public class ReportCadreVo {
    private Long id;
    private String name;
    private Long belongingCommunity;
    private Long belongingHome;
    private Long belongingUnit;
    private String phone;
    private String idNumber;
    private Integer isBind;
    private String avatar;
    private String nickname;
    private Integer integral;
    private Long userId;
    private Integer remainingPoints;
    private Integer reportStatus; //双报到积分
    private Integer showIntegral; //公益积分

}
