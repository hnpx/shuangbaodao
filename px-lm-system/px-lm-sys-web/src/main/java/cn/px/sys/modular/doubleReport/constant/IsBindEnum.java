package cn.px.sys.modular.doubleReport.constant;

import cn.px.base.consts.BaseEnum;

public enum IsBindEnum implements BaseEnum {


    IS_BIND_TRUE(1,"是"),
    IS_BIND_FALSE(2,"否");


    private String desc;
    private Integer value;
    private IsBindEnum(Integer value, String desc){
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
