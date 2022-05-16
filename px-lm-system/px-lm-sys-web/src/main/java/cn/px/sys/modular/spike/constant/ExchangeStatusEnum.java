package cn.px.sys.modular.spike.constant;

import cn.px.base.consts.BaseEnum;

public enum ExchangeStatusEnum implements BaseEnum {

    EXCHANGE_STATUS_ENUM_ONE(1,"未兑换"),
    EXCHANGE_STATUS_ENUM_TWO(2,"已兑换");


    private String desc;
    private Integer value;
    private ExchangeStatusEnum(Integer value, String desc){
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
