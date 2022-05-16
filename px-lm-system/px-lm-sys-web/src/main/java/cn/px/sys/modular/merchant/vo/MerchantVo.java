package cn.px.sys.modular.merchant.vo;

import lombok.Data;

@Data
public class MerchantVo {
    private Long id;
    private String headFigure;
    private String name;
    private String exchangeMethod;
    private String belongingHome;
    private String introduction;
    private String contactPerson;
    private String phone;
    private String address;
    private Long userId;
    private Integer integral;

}
