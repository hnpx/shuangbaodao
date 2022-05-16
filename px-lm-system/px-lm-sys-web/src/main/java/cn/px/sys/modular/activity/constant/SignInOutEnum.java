package cn.px.sys.modular.activity.constant;

import cn.px.base.consts.BaseEnum;

public enum SignInOutEnum implements BaseEnum {

    SIGN_ENUM_ONE(1,"是"),
    SIGN_ENUM_TWO(2,"否");


    private String desc;
    private Integer value;
    private SignInOutEnum(Integer value, String desc){
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
