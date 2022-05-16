package cn.px.sys.modular.integral.vo;

import lombok.Data;

import java.util.Date;

@Data
public class IntegralRecordVo {
    private Long id;
    private Date createTime;
    private Integer integral;
    private String source;
    private Integer type;
    private String integralType;

}
