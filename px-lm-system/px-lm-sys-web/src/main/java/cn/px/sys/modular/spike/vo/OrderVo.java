package cn.px.sys.modular.spike.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderVo {
    private Long id;
    private Date createTime;
    private Long userId;
    private Long productId;
    private Integer number;
    private Integer status;
    private String nickname;
    private String productName;
    private String phone;
    private String headFigure;
}
