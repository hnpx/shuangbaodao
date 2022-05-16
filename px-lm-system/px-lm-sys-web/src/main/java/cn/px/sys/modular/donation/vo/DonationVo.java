package cn.px.sys.modular.donation.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DonationVo {
    private Long id;
    private Long createBy;
    private Integer type;
    private Integer participateWay;
    private String goodDescription;
    private String contactPerson;
    private String phone;
    private Date createTime;
}
