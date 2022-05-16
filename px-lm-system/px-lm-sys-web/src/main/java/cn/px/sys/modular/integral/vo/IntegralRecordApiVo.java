package cn.px.sys.modular.integral.vo;

import lombok.Data;

import java.util.Date;

@Data
public class IntegralRecordApiVo {

    private Long id;
    private Date createTime;
    private Integer integral;

    private Integer type;
    private String integralType;
    private String source;
    private String nickName;


}
