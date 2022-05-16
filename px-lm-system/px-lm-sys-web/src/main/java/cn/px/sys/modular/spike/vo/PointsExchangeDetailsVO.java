package cn.px.sys.modular.spike.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询积分兑换详情VO
 */
@Data
public class PointsExchangeDetailsVO {
    private static final long serialVersionUID = 242089219038575317L;

    @ApiModelProperty("产品名字")
    private String productName;

    @ApiModelProperty("产品头图")
    private String headFigure;

    @ApiModelProperty("产品剩余数量")
    private Integer remainingAmount;

    @ApiModelProperty("产品积分")
    private Integer integral;

    @ApiModelProperty("已兑换数量")
    Integer redeemedAmpunt;

    @ApiModelProperty("兑换须知")
    String exchangeNotice;
}
