package cn.px.sys.modular.activity.constant;

import cn.px.base.consts.BaseEnum;

public enum TimeStatusEnum implements BaseEnum {

    TIME_STATUS_TRUE(1,"未开始"),
    TIME_STATUS_FALSE(2,"进行中"),
    TIME_STATUS_END(3,"已结束"),
    TIME_STATUS_ENUM(4,"未认领");



    private String desc;
    private Integer value;
    private TimeStatusEnum(Integer value, String desc){
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
