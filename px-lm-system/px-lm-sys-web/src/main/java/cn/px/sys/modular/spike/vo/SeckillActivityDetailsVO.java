package cn.px.sys.modular.spike.vo;

import io.swagger.annotations.ApiModelProperty;
//import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询秒杀活动详情VO
 */
@Data
public class SeckillActivityDetailsVO {
    @ApiModelProperty("产品名")
    private String productName;

    @ApiModelProperty("产品头图")
    private String headFigure;

    @ApiModelProperty("秒杀价格")
    BigDecimal price;

    @ApiModelProperty("秒杀开始时间")
    private Date stratTime;

    @ApiModelProperty("秒杀结束时间")
    private Date endTime;

    @ApiModelProperty("已兑换数量")
    Integer redeemedAmpunt;

    @ApiModelProperty("兑换须知")
    String exchangeNotice;

    @ApiModelProperty("秒杀产品数量")
    Integer number;
}
