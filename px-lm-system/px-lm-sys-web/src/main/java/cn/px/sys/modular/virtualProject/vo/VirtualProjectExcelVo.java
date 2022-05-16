package cn.px.sys.modular.virtualProject.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class VirtualProjectExcelVo {
    @Excel(name = "项目名称",width = 20)
    private String name;
    private Long id;
    private Long companyClass;
    @Excel(name = "项目分类",width = 20)
    private String  companyClassName;
    @Excel(name = "项目地址",width = 20)
    private String address;
    private Long belongingCommunity;
    @Excel(name = "所属社区",width = 20)
    private String belongingCommunityName;
    private Long belongingUnit;
    @Excel(name = "所属单位",width = 20)
    private String belongingUnitName;
    @Excel(name = "负责人",width = 20)
    private String contactPerson;
    @Excel(name = "负责电话",width = 20)
    private String phone;
    @Excel(name = "活动年份",width = 20)
    private String time;
    private Integer status;
    @Excel(name = "状态",width = 20)
    private String statusName;

}
