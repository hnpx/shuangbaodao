package cn.px.sys.modular.spike.entity;

import cn.px.base.core.BaseModel;
import cn.px.sys.modular.merchant.entity.MerchantEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品管理(Product)
 *
 * @author makejava
 * @since 2020-09-07 16:27:31
 */
@TableName("product")
@Data
public class ProductEntity extends BaseModel implements Serializable {
    private static final long serialVersionUID = 242089219038575317L;
    /**
     * 产品名字
     */
    @TableField("name")
    private String name;
    /**
     * 产品头图
     */
    @TableField("head_figure")
    private String headFigure;
    /**
     * 产品积分
     */
    @TableField("integral")
    private Integer integral;
    /**
     * 产品数量/库存
     */
    @TableField("remaining_amount")
    private Integer remainingAmount;
    /**
     * 已兑换数量
     */
    @TableField("redeemed_ampunt")
    private Integer redeemedAmpunt;
    /**
     * 兑换须知
     */
    @TableField("exchange_notice")
    private String exchangeNotice;
    /**
     * 商家id
     */
    @TableField("merchant_id")
    private Long merchantId;

    @ApiModelProperty("商家")
    @TableField(exist = false)
    MerchantEntity merchantEntity;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadFigure() {
        return headFigure;
    }

    public void setHeadFigure(String headFigure) {
        this.headFigure = headFigure;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Integer getRedeemedAmpunt() {
        return redeemedAmpunt;
    }

    public void setRedeemedAmpunt(Integer redeemedAmpunt) {
        this.redeemedAmpunt = redeemedAmpunt;
    }

    public String getExchangeNotice() {
        return exchangeNotice;
    }

    public void setExchangeNotice(String exchangeNotice) {
        this.exchangeNotice = exchangeNotice;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

}