package cn.px.sys.modular.integral.constant;

import cn.px.base.consts.BaseEnum;

public enum StatusOrderEnum implements BaseEnum {

    STATUS_ORDER_ENUM_ONE(1,"未兑换"),
    STATUS_ORDER_ENUM_TWO(2,"已兑换");


    private String desc;
    private Integer value;
    private StatusOrderEnum(Integer value, String desc){
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
