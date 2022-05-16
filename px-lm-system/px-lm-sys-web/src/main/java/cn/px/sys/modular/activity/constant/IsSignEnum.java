package cn.px.sys.modular.activity.constant;

import cn.px.base.consts.BaseEnum;

public enum IsSignEnum implements BaseEnum {

    IS_SIGN_ENUM_TRUE(1,"已报名"),
    IS_SIGN_ENUM_FALSE(2,"未报名");


    private String desc;
    private Integer value;
    private IsSignEnum(Integer value, String desc){
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
