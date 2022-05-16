package cn.px.sys.modular.spike.constant;

import cn.px.base.consts.BaseEnum;

public enum TypeEnum1 implements BaseEnum {

    TYPE_ENUM_ONE(1,"普通商品"),
    TYPE_ENUM_TWO(2,"秒杀商品");


    private String desc;
    private Integer value;
    private TypeEnum1(Integer value, String desc){
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
