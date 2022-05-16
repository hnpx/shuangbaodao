package cn.px.sys.modular.spike.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Api("秒殺商品拆綫呢DTO")
public class SpikeQueryDTO implements Serializable {
    private static final long serialVersionUID = 574222897691462008L;
    /**
     *
     */
    @ApiModelProperty("产品id")
    private Long productId;




    /**
     *
     */
    @ApiModelProperty("秒杀开始时间")
    private Date stratTime;
    /**
     *
     */
    @ApiModelProperty("秒杀结束时间")
    private Date endTime;
    /**
     *
     */
    @ApiModelProperty("秒杀价格")
    private BigDecimal price;
    /**
     *
     */
    @ApiModelProperty("秒杀产品数量")
    private Integer number;
    /**
     *
     */
    @ApiModelProperty("每人限购数量")
    private Integer limitNumber;




    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getStratTime() {
        return stratTime;
    }

    public void setStratTime(Date stratTime) {
        this.stratTime = stratTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }



    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }

}