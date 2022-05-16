package cn.px.sys.modular.project.constant;

import cn.px.base.consts.BaseEnum;

public enum PersonnelTypeEnum implements BaseEnum {

    PERSONNEL_TYPE_ENUM_ONE(1,"所有人"),
    SIGN_ENUM_TWO(2,"党员干部"),
    SIGN_ENUM_THREE(3,"单位");


    private String desc;
    private Integer value;
    private PersonnelTypeEnum(Integer value, String desc){
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
