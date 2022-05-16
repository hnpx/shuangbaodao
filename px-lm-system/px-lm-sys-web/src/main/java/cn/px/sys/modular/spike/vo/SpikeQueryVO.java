package cn.px.sys.modular.spike.vo;

import com.sun.jna.platform.win32.OaIdl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Api("查询限时秒杀VO")
public class SpikeQueryVO implements Serializable {
    private static final long serialVersionUID = 574222897691462008L;

    @ApiModelProperty("产品名")
    private String productName;

    @ApiModelProperty("秒杀开始时间")
    private Date stratTime;

    @ApiModelProperty("秒杀结束时间")
    private Date endTime;

    @ApiModelProperty("秒杀价格")
    private BigDecimal price;

    @ApiModelProperty("产品头图")
    private String headFigure;

    @ApiModelProperty("已兑换人数")
    Integer numberPeopleExchanged;

    @ApiModelProperty("产品id")
    Long productId;

    @ApiModelProperty("秒杀产品数量")
    private Integer number;

    @ApiModelProperty("每人限购数量")
    Integer limitNumber;

    @ApiModelProperty("id")
    Long id;
    @ApiModelProperty("timeStatus")
    Integer timeStatus;

}