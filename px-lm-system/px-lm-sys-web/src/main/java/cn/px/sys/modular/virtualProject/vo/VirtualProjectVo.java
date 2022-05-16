package cn.px.sys.modular.virtualProject.vo;

import lombok.Data;

@Data
public class VirtualProjectVo {
    private Long id;
    private Long companyClass;
    private String  companyClassName;
    private Long belongingCommunity;
    private String belongingCommunityName;
    private Long belongingUnit;
    private String belongingUnitName;
    private String name;
    private Integer status;
    private String address;
    private String contactPerson;
    private String phone;
    private String time;

}
