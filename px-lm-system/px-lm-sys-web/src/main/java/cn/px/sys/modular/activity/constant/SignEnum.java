package cn.px.sys.modular.activity.constant;

import cn.px.base.consts.BaseEnum;

public enum SignEnum implements BaseEnum {

    SIGN_ENUM_ONE(0,"未参加"),
    SIGN_ENUM_TWO(1,"已签到"),
    SIGN_ENUM_THREE(2,"已签退");


    private String desc;
    private Integer value;
    private SignEnum(Integer value, String desc){
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
