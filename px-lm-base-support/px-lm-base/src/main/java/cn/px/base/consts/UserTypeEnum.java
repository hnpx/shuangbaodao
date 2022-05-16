package cn.px.base.consts;

public enum UserTypeEnum implements BaseEnum {


    /**
     * 供应商
     */
    SUPPLIER(1, "供应商"),
    /**
     * 用户
     */
    USER(2, "用户"),
    ADMIN(3, "管理员");
    private Integer value;
    private String desc;

    private UserTypeEnum(Integer value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }
}
