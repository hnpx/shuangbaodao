package cn.px.sys.modular.activity.constant;

import cn.px.base.consts.BaseEnum;

public enum SignIsOrNotEnum implements BaseEnum {

    IS_SIGN_ENUM_TRUE(1,"是"),
    IS_SIGN_ENUM_FALSE(2,"否");


    private String desc;
    private Integer value;
    private SignIsOrNotEnum(Integer value, String desc){
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
