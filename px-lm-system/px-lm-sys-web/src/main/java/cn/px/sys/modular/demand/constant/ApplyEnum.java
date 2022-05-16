package cn.px.sys.modular.demand.constant;

import cn.px.base.consts.BaseEnum;

public enum ApplyEnum implements BaseEnum {


    APPLY_STATUS_TRUE(1,"通过"),
    APPLY_STATUS_FALSE(2,"未通过"),
    APPLY_STATUS_WAIT(3,"未审核");


    private String desc;
    private Integer value;
    private ApplyEnum(Integer value, String desc){
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
