package cn.px.sys.modular.wx.constant;

import cn.px.base.consts.BaseEnum;

public enum ReportStatusEnum implements BaseEnum {

    REPORT_STATUS_ENUM_ONE(1,"未报道"),
    REPORT_STATUS_ENUM_TWO(2,"已报道");


    private String desc;
    private Integer value;
    private ReportStatusEnum(Integer value, String desc){
        this.desc=desc;
        this.value=value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
