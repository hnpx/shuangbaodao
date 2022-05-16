package cn.px.sys.modular.spike.entity;

import cn.px.base.core.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 限时秒杀(Spike)
 *
 * @author makejava
 * @since 2020-09-07 16:03:30
 */
@TableName("spike")
@Data
public class SpikeEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 574222897691462008L;
    /**
     * 产品id
     */
    @TableField("product_id")
    private Long productId;

    @TableField(exist = false)
    @ApiModelProperty("产品头图")
    String headFigure;

    @TableField(exist = false)
    @ApiModelProperty("产品名")
    String name;

    @ApiModelProperty("已兑换人数")
    Integer numberPeopleExchanged;

    @ApiModelProperty("产品积分")
    @TableField(exist = false)
    Integer integral;


    /**
     * 秒杀开始时间
     */
    @TableField("strat_time")
    private Date stratTime;
    /**
     * 秒杀结束时间
     */
    @TableField("end_time")
    private Date endTime;
    /**
     * 秒杀价格
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 秒杀产品数量
     */
    @TableField("number")
    private Integer number;
    /**
     * 每人限购数量
     */
    @TableField("limit_number")
    private Integer limitNumber;

    @TableField("redeemed_ampunt")
    private Integer redeemedAmpunt;

    @TableField("exchange_notice")
    private String exchangeNotice;

    public String getExchangeNotice() {
        return exchangeNotice;
    }

    public void setExchangeNotice(String exchangeNotice) {
        this.exchangeNotice = exchangeNotice;
    }

    public Integer getRedeemedAmpunt() {
        return redeemedAmpunt;
    }

    public void setRedeemedAmpunt(Integer redeemedAmpunt) {
        this.redeemedAmpunt = redeemedAmpunt;
    }

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