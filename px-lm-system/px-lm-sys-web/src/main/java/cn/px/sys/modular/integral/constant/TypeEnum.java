package cn.px.sys.modular.integral.constant;

import cn.px.base.consts.BaseEnum;

public enum TypeEnum implements BaseEnum {

    TYPE_ENUM_ONE(1,"加积分"),
    TYPE_ENUM_TWO(2,"减积分");


    private String desc;
    private Integer value;
    private TypeEnum(Integer value, String desc){
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
